package com.AI.Demo.Service;

import com.AI.Demo.dto.DocumentChunk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PdfIngestionService {

    private static final Logger log = LoggerFactory.getLogger(PdfIngestionService.class);
    private static final int BATCH_SIZE = 50;

    private final StreamingPdfChunkerService chunkerService;
    private final VectorStore vectorStore;

    public PdfIngestionService(StreamingPdfChunkerService chunkerService, VectorStore vectorStore) {
        this.chunkerService = chunkerService;
        this.vectorStore = vectorStore;
    }

    public void ingestPdf(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("Target file does not exist: " + filePath);
        }

        List<Document> batch = new ArrayList<>(BATCH_SIZE);
        final int[] totalIndexed = {0};

        chunkerService.processFile(file, (DocumentChunk chunk) -> {
            // Convert domain chunk into Spring AI Document
            Document doc = new Document(chunk.content(), chunk.metadata());
            batch.add(doc);

            // Flush batch once threshold is hit
            if (batch.size() >= BATCH_SIZE) {
                persistBatch(batch);
                totalIndexed[0] += batch.size();
                log.info("Indexed {} cumulative chunks...", totalIndexed[0]);
                batch.clear(); // Releases references immediately for garbage collection
            }
        });

        // Write remaining elements
        if (!batch.isEmpty()) {
            persistBatch(batch);
            totalIndexed[0] += batch.size();
            batch.clear();
        }

        log.info("Ingestion complete. Total chunks persisted: {}", totalIndexed[0]);
    }

    private void persistBatch(List<Document> batch) {
        try {
            // Generates embeddings via configured EmbeddingModel and writes to Vector DB
            log.info("Batch:"+batch.toString());
            vectorStore.add(batch);
        } catch (Exception e) {
            log.error("Failed to persist vector batch: {}", e.getMessage(), e);
            throw new RuntimeException("Vector ingestion failed during write", e);
        }
    }
}
