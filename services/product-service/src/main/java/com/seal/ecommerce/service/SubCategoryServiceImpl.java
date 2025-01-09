package com.seal.ecommerce.service;

import com.seal.ecommerce.dto.request.SubCategoryCreationRequest;
import com.seal.ecommerce.dto.response.SubCategoryResponse;
import com.seal.ecommerce.entity.Product;
import com.seal.ecommerce.entity.SubCategory;
import com.seal.ecommerce.exception.AppException;
import com.seal.ecommerce.exception.ErrorCode;
import com.seal.ecommerce.mapper.SubCategoryMapper;
import com.seal.ecommerce.repository.SubCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class SubCategoryServiceImpl implements SubcategoryService{
    private final SubCategoryRepository subCategoryRepository;
    private final SubCategoryMapper subCategoryMapper;

    @Override
    public SubCategoryResponse createSubcategory(SubCategoryCreationRequest request) {
        SubCategory subCategory = subCategoryMapper.creationRequestToEntity(request);
        try {
            subCategory = subCategoryRepository.save(subCategory);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.SUB_CATEGORY_ALREADY_EXISTS);
        }
        return subCategoryMapper.toResponse(subCategory);
    }

    @Override
    public SubCategoryResponse getSubcategory(Integer id) {
        return subCategoryRepository.findById(id)
                .map(subCategoryMapper::toResponse)
                .orElseThrow(() -> new AppException(ErrorCode.SUB_CATEGORY_NOT_FOUND));
    }

    @Override
    public List<SubCategoryResponse> getSubcategories() {
        return subCategoryRepository.findAll().stream()
                .map(subCategoryMapper::toResponse)
                .toList();
    }

    @Override
    public SubCategoryResponse updateSubcategory(Integer id, SubCategoryCreationRequest subCategoryCreationRequest) {
        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SUB_CATEGORY_NOT_FOUND));
        SubCategory request = subCategoryMapper.creationRequestToEntity(subCategoryCreationRequest);
        mergeSubCategory(subCategory, request);
        subCategory = subCategoryRepository.save(subCategory);
        return subCategoryMapper.toResponse(subCategory);
    }

    @Override
    public SubCategoryResponse deleteSubcategory(Integer id) {
        return subCategoryRepository.findById(id)
                .map(subCategory -> {
                    subCategoryRepository.delete(subCategory);
                    return subCategoryMapper.toResponse(subCategory);
                })
                .orElseThrow(() -> new AppException(ErrorCode.SUB_CATEGORY_NOT_FOUND));
    }

    @Override
    public List<SubCategoryResponse> getSubcategoriesByCategory(Integer categoryId) {
        return subCategoryRepository.findAllByCategoryId(categoryId).stream()
                .map(subCategoryMapper::toResponse)
                .toList();
    }

    private void mergeSubCategory(SubCategory subCategory, SubCategory newSubCategory) {
        if(!newSubCategory.getName().isEmpty()){
            subCategory.setName(newSubCategory.getName());
        }
        if(newSubCategory.getCategory() != null){
            subCategory.setCategory(newSubCategory.getCategory());
        }
    }
}
