package com.guvi.projects.StockFlow.dto;

import java.util.List;

public record ProductRequest(
        String name,
        String description,
        Double price,
        Integer stock,
        String status,
        List<Long> categoryIds
) {}