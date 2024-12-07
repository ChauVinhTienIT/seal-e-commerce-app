package com.seal.ecommerce.service;

import com.seal.ecommerce.dto.request.ProductCreationRequest;
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
        Product product = productMapper.toEntity(productCreationRequest);
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
    public ProductResponse updateProduct(ProductCreationRequest productCreationRequest) {
        return null;
    }

    @Override
    public ProductResponse deleteProduct(ProductCreationRequest productCreationRequest) {
        return null;
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
}
