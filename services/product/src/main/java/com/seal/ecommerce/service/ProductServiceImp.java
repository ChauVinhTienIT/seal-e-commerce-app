package com.seal.ecommerce.service;

import com.seal.ecommerce.dto.request.ProductCreationRequest;
import com.seal.ecommerce.dto.request.ProductUpdateRequest;
import com.seal.ecommerce.dto.response.ProductResponse;
import com.seal.ecommerce.entity.Inventory;
import com.seal.ecommerce.entity.Product;
import com.seal.ecommerce.exception.AppException;
import com.seal.ecommerce.exception.ErrorCode;
import com.seal.ecommerce.mapper.ProductMapper;
import com.seal.ecommerce.repository.InventoryRepository;
import com.seal.ecommerce.repository.ProductRepository;
import lombok.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService{
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponse createProduct(ProductCreationRequest productCreationRequest) {
        Product product = productMapper.creationRequestToEntity(productCreationRequest);
        try {
            product = productRepository.save(product);
            Inventory inventory = new Inventory();
            inventory.setProduct(product);
            inventoryRepository.save(inventory);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.PRODUCT_ALREADY_EXISTS);
        }
        return productMapper.toResponse(product);
    }

    @Override
    public ProductResponse updateProduct(ProductUpdateRequest productUpdateRequest, Integer productId) {
        Product request = productMapper.updateRequestToEntity(productUpdateRequest);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        // Update product fields if they are not null or empty
        mergeProduct(product, request);

        productRepository.save(product);

        return productMapper.toResponse(product);
    }

    @Override
    public ProductResponse disableProduct(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        product.setActive(false);
        productRepository.save(product);
        return productMapper.toResponse(product);
    }

    @Override
    public ProductResponse enableProduct(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        product.setActive(true);
        productRepository.save(product);
        return productMapper.toResponse(product);
    }

    @Override
    public ProductResponse getProduct(Integer productId) {
        return productRepository.findById(productId)
                .map(productMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository
                .findAll()
                .stream().map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    private void mergeProduct(Product product, Product request) {
        if (!request.getName().isBlank()) {
            product.setName(request.getName());
        }
        if (!request.getShortDescription().isBlank()) {
            product.setShortDescription(request.getShortDescription());
        }
        if (!request.getFullDescription().isBlank()) {
            product.setFullDescription(request.getFullDescription());
        }
        if (!request.getMainImage().isBlank()) {
            product.setMainImage(request.getMainImage());
        }
        if (!request.getBrand().isBlank()) {
            product.setBrand(request.getBrand());
        }
        if (request.getSubCategory() != null) {
            product.setSubCategory(request.getSubCategory());
        }
    }
}
