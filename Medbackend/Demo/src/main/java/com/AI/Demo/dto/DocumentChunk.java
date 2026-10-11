package com.AI.Demo.dto;

import java.util.Map;

public record DocumentChunk(
        int chunkIndex,
        int startPage,
        int endPage,
        String content,
        Map<String, Object> metadata
) {}
