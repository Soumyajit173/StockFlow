package com.guvi.projects.StockFlow.dto;

public record OrderItemRequest(
        Long productId,
        Integer quantity
) {}