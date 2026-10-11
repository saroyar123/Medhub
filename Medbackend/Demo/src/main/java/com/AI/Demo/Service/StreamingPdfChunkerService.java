package com.AI.Demo.Service;

import com.AI.Demo.dto.DocumentChunk;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

@Service
public class StreamingPdfChunkerService {

    private static final Logger log = LoggerFactory.getLogger(StreamingPdfChunkerService.class);
    private static final int CHUNK_SIZE = 600; // Target word length
    private static final int OVERLAP = 80;      // Overlapping word length

    private final PdfPageFilter pageFilter;

    public StreamingPdfChunkerService(PdfPageFilter pageFilter) {
        this.pageFilter = pageFilter;
    }

    public void processFile(File pdfFile, Consumer<DocumentChunk> chunkConsumer) throws IOException {
        log.info("Starting processing: {} ({} MB)", pdfFile.getName(), pdfFile.length() / (1024 * 1024));

        // Use disk temp files to prevent heap ballooning on multi-hundred MB inputs
        MemoryUsageSetting memorySetting = MemoryUsageSetting.setupTempFileOnly();

        try (PDDocument document = Loader.loadPDF(pdfFile, String.valueOf(memorySetting))) {
            PDFTextStripper stripper = new PDFTextStripper();
            int totalPages = document.getNumberOfPages();

//            totalPages=3;

            List<String> window = new ArrayList<>(CHUNK_SIZE + 50);
            int startPage = 1;
            int chunkIndex = 0;

            for (int page = 1; page <= totalPages; page++) {
                stripper.setStartPage(page);
                stripper.setEndPage(page);

                String rawText = stripper.getText(document);

                if (pageFilter.shouldSkipPage(page, totalPages, rawText)) {
                    continue;
                }

                String clean = cleanText(rawText);
                String[] words = clean.split(" ");

                for (String word : words) {
                    if (word.isEmpty()) continue;

                    if (window.isEmpty()) {
                        startPage = page;
                    }

                    window.add(word);

                    if (window.size() >= CHUNK_SIZE) {
                        emitChunk(chunkIndex++, startPage, page, pdfFile.getName(), window, chunkConsumer);

                        // Extract trailing overlap words
                        int overlapStart = Math.max(0, window.size() - OVERLAP);
                        List<String> retained = new ArrayList<>(window.subList(overlapStart, window.size()));
                        window.clear();
                        window.addAll(retained);
                        startPage = page;
                    }
                }
            }

            // Flush leftover tokens
            if (!window.isEmpty()) {
                emitChunk(chunkIndex, startPage, totalPages, pdfFile.getName(), window, chunkConsumer);
            }
        }
        log.info("Completed parsing and chunking for {}", pdfFile.getName());
    }

    private void emitChunk(int index, int startPage, int endPage, String fileName,
                           List<String> words, Consumer<DocumentChunk> consumer) {
        String content = String.join(" ", words);
        Map<String, Object> metadata = Map.of(
                "fileName", fileName,
                "startPage", startPage,
                "endPage", endPage,
                "chunkIndex", index
        );
        consumer.accept(new DocumentChunk(index, startPage, endPage, content, metadata));
    }

    private String cleanText(String text) {
        return text
                .replaceAll("-\\s*\\n", "")          // Merge words broken by line endings
                .replaceAll("[\\r\\n\\t]+", " ")     // Normalize line feeds and tabs to single spaces
                .replaceAll("\\s{2,}", " ")          // Strip multi-spaces
                .replaceAll("(\\.\\s*){4,}", " ")    // Strip lingering dotted separators
                .trim();
    }
}
