package com.guvi.projects.StockFlow.util;

import com.guvi.projects.StockFlow.dto.*;
import com.guvi.projects.StockFlow.entity.*;

import java.util.List;

public class MapperUtil {

    public static ProductResponse toProductResponse(Product product) {
        List<String> categoryNames = product.getCategories()
                .stream()
                .map(Category::getName)
                .toList();

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getStatus().name(),
                categoryNames
        );
    }

    public static OrderResponse toOrderResponse(Order order) {

        List<OrderItemResponse> items = order.getItems().stream()
                .map(item -> new OrderItemResponse(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getPrice()
                ))
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getStatus().name(),
                order.getCreatedAt().toString(),
                items
        );
    }
}