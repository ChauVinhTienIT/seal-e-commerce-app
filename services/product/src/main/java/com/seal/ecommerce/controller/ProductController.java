package com.seal.ecommerce.controller;

import com.seal.ecommerce.dto.ApiResponse;
import com.seal.ecommerce.dto.request.ProductCreationRequest;
import com.seal.ecommerce.dto.request.ProductUpdateRequest;
import com.seal.ecommerce.dto.response.ProductResponse;
import com.seal.ecommerce.service.ProductService;
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
