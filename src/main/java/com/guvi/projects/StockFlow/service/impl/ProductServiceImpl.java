package com.guvi.projects.StockFlow.service.impl;

import com.guvi.projects.StockFlow.dto.ProductRequest;
import com.guvi.projects.StockFlow.dto.ProductResponse;
import com.guvi.projects.StockFlow.entity.Category;
import com.guvi.projects.StockFlow.entity.Product;
import com.guvi.projects.StockFlow.enums.ProductStatus;
import com.guvi.projects.StockFlow.exception.ResourceNotFoundException;
import com.guvi.projects.StockFlow.repository.CategoryRepository;
import com.guvi.projects.StockFlow.repository.ProductRepository;
import com.guvi.projects.StockFlow.service.ProductService;
import com.guvi.projects.StockFlow.util.MapperUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductResponse create(ProductRequest request) {

        List<Category> categories = categoryRepository.findAllById(request.categoryIds());

        Product product = new Product();
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStock(request.stock());
        product.setStatus(ProductStatus.valueOf(request.status()));
        product.setCategories(categories);

        Product saved = productRepository.save(product);

        return mapToResponse(saved);
    }

    @Override
    public ProductResponse update(Long id, ProductRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStock(request.stock());
        product.setStatus(ProductStatus.valueOf(request.status()));

        Product updated = productRepository.save(product);

        return mapToResponse(updated);
    }

    @Override
    public Page<ProductResponse> getProducts(
            String category,
            String search,
            Integer lowStock,
            Pageable pageable
    ) {

        Page<Product> products;

        if (category != null) {
            products = productRepository.findByCategories_Name(category, pageable);
        } else if (search != null) {
            products = productRepository.findByNameContainingIgnoreCase(search, pageable);
        } else if (lowStock != null) {
            products = productRepository.findByStockLessThan(lowStock, pageable);
        } else {
            products = productRepository.findAll(pageable);
        }

        return products.map(MapperUtil::toProductResponse);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    private ProductResponse mapToResponse(Product product) {
        List<String> categoryNames = product.getCategories()
                .stream()
                .map(Category::getName)
                .collect(Collectors.toList());

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
}