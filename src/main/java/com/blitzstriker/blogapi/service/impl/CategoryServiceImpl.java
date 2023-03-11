package com.blitzstriker.blogapi.service.impl;

import com.blitzstriker.blogapi.entity.Category;
import com.blitzstriker.blogapi.exception.ResourceNotFoundException;
import com.blitzstriker.blogapi.payload.CategoryRequest;
import com.blitzstriker.blogapi.payload.CategoryResponse;
import com.blitzstriker.blogapi.repository.CategoryRepository;
import com.blitzstriker.blogapi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());

        Category newCategory = categoryRepository.save(category);
        return modelMapper.map(newCategory, CategoryResponse.class);
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = getCategoryById(id);

        category.setName(request.getName() != null ? request.getName() : category.getName());
        category.setDescription(request.getDescription() != null ? request.getDescription() : category.getDescription());
        Category updatedCategory = categoryRepository.save(category);
        return modelMapper.map(updatedCategory, CategoryResponse.class);
    }

    @Override
    public CategoryResponse getCategory(Long id) {
        Category category = getCategoryById(id);
        return modelMapper.map(category, CategoryResponse.class);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category -> modelMapper.map(category, CategoryResponse.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = getCategoryById(id);
        categoryRepository.delete(category);
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category", "ID", String.valueOf(id))
        );
    }
}
