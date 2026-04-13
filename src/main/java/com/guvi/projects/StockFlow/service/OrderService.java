package com.guvi.projects.StockFlow.service;

import com.guvi.projects.StockFlow.dto.OrderRequest;
import com.guvi.projects.StockFlow.dto.OrderResponse;
import com.guvi.projects.StockFlow.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface OrderService {

    Order placeOrder(OrderRequest request);

    Order cancelOrder(Long orderId);

    Page<OrderResponse> getOrders(int page, int size);
}