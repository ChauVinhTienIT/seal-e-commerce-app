package com.seal.ecommerce.service;

import com.seal.ecommerce.dto.PageResponse;
import com.seal.ecommerce.dto.request.ProductCreationRequest;
import com.seal.ecommerce.dto.request.ProductUpdateRequest;
import com.seal.ecommerce.dto.response.ProductResponse;
import com.seal.ecommerce.entity.Product;
import com.seal.ecommerce.entity.ProductImage;
import com.seal.ecommerce.exception.AppException;
import com.seal.ecommerce.exception.ErrorCode;
import com.seal.ecommerce.mapper.ProductMapper;
import com.seal.ecommerce.repository.ProductImageRepository;
import com.seal.ecommerce.repository.ProductRepository;
import lombok.*;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
        Optional<Product> product = productRepository.findById(productId);
        return productRepository.findById(productId)
                .map(productMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public ProductResponse uploadProductMainImage(MultipartFile file, Integer productId) {
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
    public ProductResponse updateProductMainImage(MultipartFile file, Integer productId) {
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
    public PageResponse<ProductResponse> getAllProductsToPage(long page, long size, String sortBy, boolean asc) {
        Sort sort = Sort.by(sortBy);
        if (!asc) {
            sort = sort.descending();
        }

        System.out.println("Get Page: " + page);

        Pageable pageable = PageRequest.of((int) (page - 1), (int) size, sort);

        Page<Product> productPage = productRepository.findAll(pageable);
        return PageResponse.<ProductResponse>builder()
                .currentPage(page)
                .totalElement(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .data(productPage.stream().map(productMapper::toResponse).collect(Collectors.toList()))
                .build();
    }


    @Override
    public Resource getMainImage(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        String fileName = product.getMainImage();
        String fileUri = String.format("%s/%d/%s", imageDir, product.getId(), fileName);
        return filesStorageService.loadAsResource(fileUri);
    }

    @Override
    @Transactional
    public ProductResponse uploadProductImages(List<MultipartFile> files, Integer productId) {
        String productDir = imageDir + "/" + productId;
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        Set<ProductImage> productImages = files.stream()
                .filter(this::isImage)
                .map(file -> {
                    String fileName = filesStorageService.store(file, productDir);
                    return ProductImage.builder()
                            .product(product)
                            .name(fileName)
                            .build();
                })
                .collect(Collectors.toSet());
        productImageRepository.saveAll(productImages);
        return productMapper.toResponse(product);
    }

    @Override
    @Transactional
    public List<String> deleteProductImages(Integer productId, List<Integer> imageIds) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        List<ProductImage> productImages = productImageRepository.findAllById(imageIds);

        List<String> fileNames = productImages.stream()
                .map(ProductImage::getName)
                .collect(Collectors.toList());

        fileNames.forEach(fileName -> {
            String fileUri = String.format("%s/%d/%s", imageDir, product.getId(), fileName);
            filesStorageService.delete(fileUri);
        });

        productImageRepository.deleteAll(productImages);
        return fileNames;
    }

    @Override
    public Resource getProductImage(Integer productId, Integer imageId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        ProductImage productImage = productImageRepository.findById(imageId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_IMAGE_NOT_FOUND));
        String fileName = productImage.getName();
        String fileUri = String.format("%s/%d/%s", imageDir, product.getId(), fileName);
        return filesStorageService.loadAsResource(fileUri);
    }

    @Override
    public List<ProductResponse> getAllProductsByCategory(Integer categoryId) {
        return productRepository.findAllBySubCategoryId(categoryId)
                .stream()
                .map(productMapper::toResponse)
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

    private boolean isImage(MultipartFile file) {
        return allowedTypes.contains(file.getContentType());
    }
}
