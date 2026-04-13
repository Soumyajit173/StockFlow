package com.guvi.projects.StockFlow.repository;

import com.guvi.projects.StockFlow.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Filter by category (JOIN)
    Page<Product> findByCategories_Name(String category, Pageable pageable);

    // Search by name
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // Low stock
    Page<Product> findByStockLessThan(int threshold, Pageable pageable);
}