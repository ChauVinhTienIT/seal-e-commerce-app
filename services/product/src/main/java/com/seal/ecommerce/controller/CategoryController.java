package com.seal.ecommerce.controller;

import com.seal.ecommerce.dto.ApiResponse;
import com.seal.ecommerce.dto.request.CategoryCreationRequest;
import com.seal.ecommerce.dto.response.CategoryResponse;
import com.seal.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public ApiResponse<CategoryResponse> getCategory(
            @PathVariable Integer id
    ) {
        return ApiResponse.<CategoryResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Category retrieved successfully")
                .result(categoryService.getCategory(id))
                .build();
    }

    @GetMapping
    public ApiResponse<List<CategoryResponse>> getCategories() {
        return ApiResponse.<List<CategoryResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Categories retrieved successfully")
                .result(categoryService.getCategories())
                .build();
    }

    @PostMapping
    public ApiResponse<CategoryResponse> createCategory(
            @RequestBody @Valid CategoryCreationRequest request
    ) {
        return ApiResponse.<CategoryResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Category created successfully")
                .result(categoryService.createCategory(request))
                .build();
    }

    @PutMapping
    public ApiResponse<CategoryResponse> updateCategory(
            @RequestParam Integer id,
            @RequestBody @Valid CategoryCreationRequest request
    ) {
        return ApiResponse.<CategoryResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Category updated successfully")
                .result(categoryService.updateCategory(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<CategoryResponse> deleteCategory(
            @PathVariable Integer id
    ) {

        return ApiResponse.<CategoryResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Category deleted successfully")
                .result(categoryService.deleteCategory(id))
                .build();
    }
}
