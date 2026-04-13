package com.guvi.projects.StockFlow.repository;

import com.guvi.projects.StockFlow.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}