package com.seal.ecommerce.controller;

import com.seal.ecommerce.dto.ApiResponse;
import com.seal.ecommerce.dto.request.SubCategoryCreationRequest;
import com.seal.ecommerce.dto.response.SubCategoryResponse;
import com.seal.ecommerce.service.SubcategoryService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/subcategories")
public class SubCategoryController {
    private final SubcategoryService subCategoryService;

    @GetMapping
    public ApiResponse<List<SubCategoryResponse>> getSubCategories() {
        return ApiResponse.<List<SubCategoryResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Subcategories retrieved successfully")
                .result(subCategoryService.getSubcategories())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<SubCategoryResponse> getSubCategory(
            @PathVariable Integer id
    ) {
        return ApiResponse.<SubCategoryResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Subcategory retrieved successfully")
                .result(subCategoryService.getSubcategory(id))
                .build();
    }

    @GetMapping("/category/{category-id}")
    public ApiResponse<List<SubCategoryResponse>> getSubCategoriesByCategory(
            @PathVariable("category-id") Integer categoryId
    ) {
        return ApiResponse.<List<SubCategoryResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Subcategories retrieved successfully")
                .result(subCategoryService.getSubcategoriesByCategory(categoryId))
                .build();
    }

    @PostMapping
    public ApiResponse<SubCategoryResponse> createSubCategory(
            @RequestBody @Valid SubCategoryCreationRequest request
    ) {
        return ApiResponse.<SubCategoryResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Subcategory created successfully")
                .result(subCategoryService.createSubcategory(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<SubCategoryResponse> updateSubCategory(
            @RequestBody @Valid SubCategoryCreationRequest request,
            @PathVariable Integer id
    ) {
        return ApiResponse.<SubCategoryResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Subcategory updated successfully")
                .result(subCategoryService.updateSubcategory(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<SubCategoryResponse> deleteSubCategory(
            @PathVariable Integer id
    ) {
        subCategoryService.deleteSubcategory(id);
        return ApiResponse.<SubCategoryResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Subcategory deleted successfully")
                .build();
    }
}
