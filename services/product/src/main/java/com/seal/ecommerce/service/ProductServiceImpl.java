package com.seal.ecommerce.service;

import com.seal.ecommerce.dto.request.ProductCreationRequest;
import com.seal.ecommerce.dto.request.ProductUpdateRequest;
import com.seal.ecommerce.dto.response.ProductResponse;
import com.seal.ecommerce.entity.Product;
import com.seal.ecommerce.exception.AppException;
import com.seal.ecommerce.exception.ErrorCode;
import com.seal.ecommerce.mapper.ProductMapper;
import com.seal.ecommerce.repository.ProductImageRepository;
import com.seal.ecommerce.repository.ProductRepository;
import lombok.*;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final FilesStorageService filesStorageService;
    private final ProductMapper productMapper;
    private final List<String> allowedTypes = Arrays.asList("image/png", "image/jpeg", "image/jpg");
    private final String imageDir = "product";

    @Override
    public ProductResponse createProduct(ProductCreationRequest productCreationRequest) {
        Product product = productMapper.creationRequestToEntity(productCreationRequest);
        try {
            product = productRepository.save(product);
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
    public ProductResponse uploadProductImage(MultipartFile file, Integer productId) {
        if(!isImage(file)){
            throw new AppException(ErrorCode.INVALID_FILE_TYPE);
        }

        String productDir = imageDir + "/" + productId;

        return productRepository.findById(productId)
                .map(product -> {
                    String fileName = filesStorageService.store(file, productDir);
                    product.setMainImage(fileName);
                    productRepository.save(product);
                    return productMapper.toResponse(product);
                })
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    @Override
    public ProductResponse updateProductImage(MultipartFile file, Integer productId) {
        if(!isImage(file)){
            throw new AppException(ErrorCode.INVALID_FILE_TYPE);
        }

        String productDir = imageDir + "/" + productId;

        return productRepository.findById(productId)
                .map(product -> {
                    String oldFileName = product.getMainImage();
                    String fileName = filesStorageService.store(file, productDir);
                    product.setMainImage(fileName);

                    productRepository.save(product);

                    String fileUri = String.format("%s/%d/%s", imageDir, product.getId(), oldFileName);
                    filesStorageService.delete(fileUri);

                    return productMapper.toResponse(product);
                })
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository
                .findAll()
                .stream().map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Resource getMainImage(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        String fileName = product.getMainImage();
        String fileUri = String.format("%s/%d/%s", imageDir, product.getId(), fileName);
        return filesStorageService.loadAsResource(fileUri);
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

    private boolean isImage(MultipartFile file) {
        return allowedTypes.contains(file.getContentType());
    }
}
