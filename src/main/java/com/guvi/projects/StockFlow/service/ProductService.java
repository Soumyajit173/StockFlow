package com.guvi.projects.StockFlow.service;

import com.guvi.projects.StockFlow.dto.ProductRequest;
import com.guvi.projects.StockFlow.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    ProductResponse create(ProductRequest request);

    ProductResponse update(Long id, ProductRequest request);

     Page<ProductResponse> getProducts(
            String category,
            String search,
            Integer lowStock,
            Pageable pageable
    );

    void delete(Long id);
}