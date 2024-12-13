package com.seal.ecommerce.controller;

import com.seal.ecommerce.dto.ApiResponse;
import com.seal.ecommerce.dto.PageResponse;
import com.seal.ecommerce.dto.request.ProductCreationRequest;
import com.seal.ecommerce.dto.request.ProductUpdateRequest;
import com.seal.ecommerce.dto.response.ProductResponse;
import com.seal.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Get all products")
    @GetMapping
    public ApiResponse<List<ProductResponse>> getAllProducts() {
        return ApiResponse.<List<ProductResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Products retrieved successfully")
                .result(productService.getAllProducts())
                .build();
    }

    @GetMapping("/page")
    @Operation(summary = "Get all products to page")
    public ApiResponse<PageResponse<ProductResponse>> getAllProductsToPage(
            @RequestParam(value = "page", required = false, defaultValue = "1") long page,
            @RequestParam(value = "size", required = false, defaultValue = "10") long size,
            @RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(value = "asc", required = false, defaultValue = "true") boolean asc
    ) {
        return ApiResponse.<PageResponse<ProductResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Products retrieved successfully")
                .result(productService.getAllProductsToPage(page, size, sortBy, asc))
                .build();
    }

    @GetMapping("/category/{category-id}")
    @Operation(summary = "Get all products by category")
    public ApiResponse<List<ProductResponse>> getAllProductsByCategory(
            @PathVariable("category-id") Integer categoryId
    ) {
        return ApiResponse.<List<ProductResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Products retrieved successfully with category ID: " + categoryId)
                .result(productService.getAllProductsByCategory(categoryId))
                .build();
    }

    @Operation(summary = "Get a product by ID")
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

    @Operation(summary = "Create a product")
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

    @Operation(summary = "Update a product")
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

    @Operation(summary = "Enable a product")
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

    @Operation(summary = "Disable a product")
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

    @Operation(summary = "Get the main image of a product")
    @GetMapping("/main-image/{product-id}")
    @ResponseBody
    public ResponseEntity<Resource> getProductMainImage(
            @PathVariable("product-id") Integer productId
    ) throws IOException {
        Resource file = productService.getMainImage(productId);
        // Decode the received file URI
        String contentType = Files.probeContentType(file.getFile().toPath());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(file);
    }

    @Operation(summary = "Upload the main image of a product")
    @PostMapping(path = "main-image/{product-id}", consumes = "multipart/form-data")
    public ApiResponse<ProductResponse> uploadProductMainImage(
            @RequestParam("file") MultipartFile file,
            @PathVariable("product-id") Integer productId
    ) {
        return ApiResponse.<ProductResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Product image uploaded successfully")
                .result(productService.uploadProductMainImage(file, productId))
                .build();
    }

    @Operation(summary = "Update the main image of a product")
    @PutMapping(path = "main-image/{product-id}", consumes = "multipart/form-data")
    public ApiResponse<ProductResponse> updateProductMainImage(
            @RequestParam("file") MultipartFile file,
            @PathVariable("product-id") Integer productId
    ) {
        return ApiResponse.<ProductResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Product image uploaded successfully")
                .result(productService.updateProductMainImage(file, productId))
                .build();
    }

    @Operation(summary = "Upload images of a product")
    @PostMapping(path = "images/{product-id}", consumes = "multipart/form-data")
    public ApiResponse<ProductResponse> uploadProductImages(
            @RequestParam("files") List<MultipartFile> files,
            @PathVariable("product-id") Integer productId
    ) {
        return ApiResponse.<ProductResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Product images uploaded successfully")
                .result(productService.uploadProductImages(files, productId))
                .build();
    }

    @Operation(summary = "Get an image of a product")
    @GetMapping(path = "images/{product-id}/{image-id}")
    public ResponseEntity<Resource> getProductImage(
            @PathVariable("product-id") Integer productId,
            @PathVariable("image-id") Integer imageId
    ) throws IOException {
        Resource file = productService.getProductImage(productId, imageId);
        // Decode the received file URI
        String contentType = Files.probeContentType(file.getFile().toPath());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(file);
    }

    @Operation(summary = "Delete images of a product")
    @DeleteMapping(path = "images/{product-id}/{image-id}")
    public ApiResponse<List<String>> deleteProductImages(
            @PathVariable("product-id") Integer productId,
            @PathVariable("image-id") List<Integer> imageIds
    ) {
        return ApiResponse.<List<String>>builder()
                .code(HttpStatus.OK.value())
                .message("Product images deleted successfully")
                .result(productService.deleteProductImages(productId, imageIds))
                .build();
    }

}
