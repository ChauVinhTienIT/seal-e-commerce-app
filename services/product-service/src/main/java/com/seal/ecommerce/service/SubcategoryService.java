package com.seal.ecommerce.service;

import com.seal.ecommerce.dto.request.SubCategoryCreationRequest;
import com.seal.ecommerce.dto.response.SubCategoryResponse;

import java.util.List;

public interface SubcategoryService {
    public SubCategoryResponse createSubcategory(SubCategoryCreationRequest request);
    public SubCategoryResponse getSubcategory(Integer id);
    public List<SubCategoryResponse> getSubcategories();
    public SubCategoryResponse updateSubcategory(Integer id, SubCategoryCreationRequest request);
    public SubCategoryResponse deleteSubcategory(Integer id);

    List<SubCategoryResponse> getSubcategoriesByCategory(Integer categoryId);
}
