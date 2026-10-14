package com.AI.Demo.Service;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PdfPageFilter {

    private static final Pattern DOT_LEADER_PATTERN =
            Pattern.compile("(\\.\\s*){4,}|(\\s\\d+\\s*$)");
    private static final Pattern COPYRIGHT_PATTERN =
            Pattern.compile("(?i)(all rights reserved|©|copyright|isbn|trademarks|gsa adp schedule)");

    public boolean shouldSkipPage(int pageNumber, int totalPages, String rawPageText) {
        String trimmed = rawPageText.trim();
        if (trimmed.isEmpty()) {
            return true;
        }

        // Drop legal/notices concentrated at front or back of document
        if (isBoundaryPage(pageNumber, totalPages) && isLegalOrNotice(trimmed)) {
            return true;
        }

        // Drop Table of Contents
        return isTableOfContents(trimmed);
    }

    private boolean isBoundaryPage(int pageNumber, int totalPages) {
        return pageNumber <= 15 || pageNumber > Math.max(1, totalPages - 10);
    }

    private boolean isLegalOrNotice(String text) {
        String lower = text.toLowerCase();
        if (lower.startsWith("notices") || lower.contains("trademarks") || lower.contains("terms of use")) {
            return true;
        }
        return COPYRIGHT_PATTERN.matcher(text).find() &&
                (lower.contains("license") || lower.contains("warranty") || lower.contains("liability"));
    }

    private boolean isTableOfContents(String text) {
        String lower = text.toLowerCase();
        if (lower.startsWith("contents") || lower.startsWith("table of contents")) {
            return true;
        }

        String[] lines = text.split("\r?\n");
        int matchedLines = 0;
        int nonEmptyLines = 0;

        for (String line : lines) {
            String trimmedLine = line.trim();
            if (!trimmedLine.isEmpty()) {
                nonEmptyLines++;
                if (DOT_LEADER_PATTERN.matcher(trimmedLine).find()) {
                    matchedLines++;
                }
            }
        }

        return nonEmptyLines > 5 && ((double) matchedLines / nonEmptyLines) > 0.25;
    }
}
