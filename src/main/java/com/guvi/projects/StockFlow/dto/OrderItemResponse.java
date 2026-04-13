package com.guvi.projects.StockFlow.dto;

public record OrderItemResponse(
        Long productId,
        String productName,
        Integer quantity,
        Double price
) {}
