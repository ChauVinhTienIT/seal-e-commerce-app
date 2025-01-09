package com.seal.ecommerce.service;

import com.seal.ecommerce.dto.request.ProductDetailCreationRequest;
import com.seal.ecommerce.dto.response.ProductDetailResponse;

import java.util.List;

public interface ProductDetailService {
    List<ProductDetailResponse> getProductDetails();
    ProductDetailResponse createProductDetail(ProductDetailCreationRequest request);
    List<ProductDetailResponse> addProductDetailsToProduct(List<ProductDetailCreationRequest> requests, Integer productId);
    ProductDetailResponse getProductDetail(Integer id);
    ProductDetailResponse updateProductDetail(Integer id, ProductDetailCreationRequest request);

    ProductDetailResponse deleteProductDetail(Integer id);

    List<ProductDetailResponse> getProductDetailsByProductId(Integer productId);
}
