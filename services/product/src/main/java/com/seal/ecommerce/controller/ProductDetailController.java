package com.seal.ecommerce.controller;

import com.seal.ecommerce.dto.ApiResponse;
import com.seal.ecommerce.dto.request.ProductDetailCreationRequest;
import com.seal.ecommerce.dto.response.ProductDetailResponse;
import com.seal.ecommerce.dto.response.SubCategoryResponse;
import com.seal.ecommerce.service.ProductDetailService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/product-detail")
public class ProductDetailController {
    private final ProductDetailService productDetailService;

    @GetMapping
    public ApiResponse<List<ProductDetailResponse>> getProductDetails() {
        return ApiResponse.<List<ProductDetailResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Product details retrieved successfully")
                .result(productDetailService.getProductDetails())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductDetailResponse> getProductDetail(
            @PathVariable Integer id
    ) {
        return ApiResponse.<ProductDetailResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Product detail retrieved successfully")
                .result(productDetailService.getProductDetail(id))
                .build();
    }

    @GetMapping("/product/{product-id}")
    public ApiResponse<List<ProductDetailResponse>> getProductDetailsByProductId(
            @PathVariable("product-id") Integer productId
    ) {
        return ApiResponse.<List<ProductDetailResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Product details retrieved successfully")
                .result(productDetailService.getProductDetailsByProductId(productId))
                .build();
    }

    @PostMapping
    public ApiResponse<ProductDetailResponse> createProductDetail(
            @RequestBody ProductDetailCreationRequest request
    ) {
        return ApiResponse.<ProductDetailResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Product detail created successfully")
                .result(productDetailService.createProductDetail(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<ProductDetailResponse> updateProductDetail(
            @PathVariable Integer id,
            @RequestBody ProductDetailCreationRequest request
    ) {
        return ApiResponse.<ProductDetailResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Product detail updated successfully")
                .result(productDetailService.updateProductDetail(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<ProductDetailResponse> deleteProductDetail(
            @PathVariable Integer id
    ) {
        return ApiResponse.<ProductDetailResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Product detail deleted successfully")
                .result(productDetailService.deleteProductDetail(id))
                .build();
    }

    @PostMapping("/product/{product-id}")
    public ApiResponse<List<ProductDetailResponse>> addProductDetailsToProduct(
            @RequestBody List<ProductDetailCreationRequest> requests,
            @PathVariable("product-id") Integer productId
    ) {
        return ApiResponse.<List<ProductDetailResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Product details added successfully")
                .result(productDetailService.addProductDetailsToProduct(requests, productId))
                .build();
    }

}
