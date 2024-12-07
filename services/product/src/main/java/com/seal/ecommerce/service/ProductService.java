package com.seal.ecommerce.service;


import com.seal.ecommerce.dto.request.ProductCreationRequest;
import com.seal.ecommerce.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductCreationRequest productCreationRequest);
    ProductResponse updateProduct(ProductCreationRequest productCreationRequest);
    ProductResponse deleteProduct(ProductCreationRequest productCreationRequest);
    ProductResponse getProduct(Integer productId);
    List<ProductResponse> getAllProducts();
}
