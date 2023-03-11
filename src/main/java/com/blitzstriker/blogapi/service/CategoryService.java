package com.blitzstriker.blogapi.service;

import com.blitzstriker.blogapi.payload.CategoryRequest;
import com.blitzstriker.blogapi.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);

    CategoryResponse updateCategory(Long id, CategoryRequest request);

    CategoryResponse getCategory(Long id);

    List<CategoryResponse> getAllCategories();

    void deleteCategory(Long id);
}
