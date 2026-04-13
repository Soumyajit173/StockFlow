package com.guvi.projects.StockFlow.dto;

import java.util.List;

public record OrderResponse(
        Long id,
        String status,
        String createdAt,
        List<OrderItemResponse> items
) {}