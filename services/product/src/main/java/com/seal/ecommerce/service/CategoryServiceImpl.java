package com.seal.ecommerce.service;

import com.seal.ecommerce.dto.request.CategoryCreationRequest;
import com.seal.ecommerce.dto.response.CategoryResponse;
import com.seal.ecommerce.entity.Category;
import com.seal.ecommerce.entity.Product;
import com.seal.ecommerce.exception.AppException;
import com.seal.ecommerce.exception.ErrorCode;
import com.seal.ecommerce.mapper.CategoryMapper;
import com.seal.ecommerce.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse createCategory(CategoryCreationRequest request) {
        Category category = categoryMapper.creationRequestToEntity(request);
        try {
            category = categoryRepository.save(category);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.PRODUCT_ALREADY_EXISTS);
        }
        return categoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponse getCategory(Integer id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toResponse)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
    }

    @Override
    public List<CategoryResponse> getCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    @Override
    public CategoryResponse updateCategory(Integer id, CategoryCreationRequest categoryCreationRequest) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        Category request = categoryMapper.creationRequestToEntity(categoryCreationRequest);
        mergeCategory(category, request);
        category = categoryRepository.save(category);
        return categoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponse deleteCategory(Integer id) {
        return categoryRepository.findById(id)
                .map(category -> {
                    categoryRepository.delete(category);
                    return categoryMapper.toResponse(category);
                })
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
    }

    private void mergeCategory(Category category, Category request) {
        if(!request.getName().isBlank()) {
            category.setName(request.getName());
        }
    }
}
