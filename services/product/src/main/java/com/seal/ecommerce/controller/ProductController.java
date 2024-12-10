package com.seal.ecommerce.controller;

import com.seal.ecommerce.dto.ApiResponse;
import com.seal.ecommerce.dto.request.ProductCreationRequest;
import com.seal.ecommerce.dto.request.ProductUpdateRequest;
import com.seal.ecommerce.dto.response.ProductResponse;
import com.seal.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ApiResponse<List<ProductResponse>> getAllProducts() {
        return ApiResponse.<List<ProductResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Products retrieved successfully")
                .result(productService.getAllProducts())
                .build();
    }

    @GetMapping("/{product-id}")
    public ApiResponse<ProductResponse> getProduct(
            @PathVariable("product-id") Integer productId
    ) {
        return ApiResponse.<ProductResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Product retrieved successfully")
                .result(productService.getProduct(productId))
                .build();
    }

    @PostMapping
    public ApiResponse<ProductResponse> createProduct(
            @RequestBody @Valid ProductCreationRequest request
    ) {
        return ApiResponse.<ProductResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Product created successfully")
                .result(productService.createProduct(request))
                .build();
    }

    @PutMapping("/{product-id}")
    public ApiResponse<ProductResponse> updateProduct(
            @RequestBody @Valid ProductUpdateRequest request,
            @PathVariable("product-id") Integer productId
    ) {
        return ApiResponse.<ProductResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Product updated successfully")
                .result(productService.updateProduct(request, productId))
                .build();
    }

    @PutMapping("/{product-id}/enable")
    public ApiResponse<ProductResponse> enableProduct(
            @PathVariable("product-id") Integer productId
    ) {
        return ApiResponse.<ProductResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Product enabled successfully")
                .result(productService.enableProduct(productId))
                .build();
    }

    @PutMapping("/{product-id}/disable")
    public ApiResponse<ProductResponse> disableProduct(
            @PathVariable("product-id") Integer productId
    ) {
        return ApiResponse.<ProductResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Product disabled successfully")
                .result(productService.disableProduct(productId))
                .build();
    }

    @PostMapping(path = "/{product-id}/image", consumes = "multipart/form-data")
    public ApiResponse<ProductResponse> uploadProductImage(
            @RequestParam("file") MultipartFile file,
            @PathVariable("product-id") Integer productId
    ) {
        return ApiResponse.<ProductResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Product image uploaded successfully")
                .result(productService.uploadProductImage(file, productId))
                .build();
    }
}
