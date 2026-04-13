package com.guvi.projects.StockFlow.service;

import com.guvi.projects.StockFlow.entity.Category;

import java.util.List;

public interface CategoryService {

    Category create(Category category);

    List<Category> getAll();
}