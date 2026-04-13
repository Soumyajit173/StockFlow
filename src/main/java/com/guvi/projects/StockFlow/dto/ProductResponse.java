package com.guvi.projects.StockFlow.dto;

import java.util.List;

public record ProductResponse(
        Long id,
        String name,
        String description,
        Double price,
        Integer stock,
        String status,
        List<String> categories
) {}