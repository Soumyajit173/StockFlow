package com.guvi.projects.StockFlow.service.impl;

import com.guvi.projects.StockFlow.entity.Category;
import com.guvi.projects.StockFlow.repository.CategoryRepository;
import com.guvi.projects.StockFlow.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
}