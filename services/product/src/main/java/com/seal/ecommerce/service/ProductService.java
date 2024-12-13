package com.seal.ecommerce.service;


import com.seal.ecommerce.dto.request.ProductCreationRequest;
import com.seal.ecommerce.dto.request.ProductUpdateRequest;
import com.seal.ecommerce.dto.response.ProductResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductCreationRequest productCreationRequest);
    ProductResponse updateProduct(ProductUpdateRequest productCreationRequest, Integer productId);
    ProductResponse disableProduct(Integer productId);
    ProductResponse enableProduct(Integer productId);

    ProductResponse getProduct(Integer productId);
    List<ProductResponse> getAllProducts();

    ProductResponse uploadProductMainImage(MultipartFile file, Integer productId);
    ProductResponse updateProductMainImage(MultipartFile file, Integer productId);
    Resource getMainImage(Integer productId);

    ProductResponse uploadProductImages(List<MultipartFile> files, Integer productId);
    List<String> deleteProductImages (Integer productId, List<Integer> imageIds);

    Resource getProductImage(Integer productId, Integer imageId);
}
