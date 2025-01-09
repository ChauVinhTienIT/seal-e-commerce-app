package com.seal.ecommerce.service;

import com.seal.ecommerce.dto.request.CategoryCreationRequest;
import com.seal.ecommerce.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    public CategoryResponse createCategory(CategoryCreationRequest request);
    public CategoryResponse getCategory(Integer id);
    public List<CategoryResponse> getCategories();
    public CategoryResponse updateCategory(Integer id, CategoryCreationRequest request);
    public CategoryResponse deleteCategory(Integer id);
}
