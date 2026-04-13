package com.guvi.projects.StockFlow.dto;

import java.util.List;

public record OrderRequest(
        List<OrderItemRequest> items
) {}