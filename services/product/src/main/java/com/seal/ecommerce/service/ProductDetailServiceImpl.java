package com.seal.ecommerce.service;

import com.seal.ecommerce.dto.request.ProductDetailCreationRequest;
import com.seal.ecommerce.dto.response.ProductDetailResponse;
import com.seal.ecommerce.entity.Product;
import com.seal.ecommerce.entity.ProductDetail;
import com.seal.ecommerce.entity.SubCategory;
import com.seal.ecommerce.exception.AppException;
import com.seal.ecommerce.exception.ErrorCode;
import com.seal.ecommerce.mapper.ProductDetailMapper;
import com.seal.ecommerce.repository.ProductDetailRepository;
import com.seal.ecommerce.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ProductDetailServiceImpl implements ProductDetailService {
    private final ProductDetailRepository productDetailRepository;
    private final ProductRepository productRepository;
    private final ProductDetailMapper productDetailMapper;

    @Override
    public List<ProductDetailResponse> getProductDetails() {
        return productDetailRepository.findAll().stream()
                .map(productDetailMapper::toResponse)
                .toList();
    }

    @Override
    public ProductDetailResponse createProductDetail(ProductDetailCreationRequest request) {
        ProductDetail productDetail = productDetailMapper.toEntity(request);
        try {
            productDetail = productDetailRepository.save(productDetail);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.PRODUCT_DETAIL_ALREADY_EXISTS);
        }
        return productDetailMapper.toResponse(productDetail);
    }

    @Override
    public List<ProductDetailResponse> addProductDetailsToProduct(List<ProductDetailCreationRequest> requests, Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        List<ProductDetail> productDetails = requests.stream()
                .map(request -> {
                    ProductDetail productDetail = productDetailMapper.toEntity(request);
                    productDetail.setProduct(product);
                    return productDetail;
                })
                .toList();

        productDetailRepository.saveAll(productDetails);
        return productDetails.stream()
                .map(productDetailMapper::toResponse)
                .toList();
    }

    @Override
    public ProductDetailResponse getProductDetail(Integer id) {
        return productDetailRepository.findById(id)
                .map(productDetailMapper::toResponse)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_DETAIL_NOT_FOUND));
    }

    @Override
    public ProductDetailResponse updateProductDetail(Integer id, ProductDetailCreationRequest request) {
        ProductDetail productDetail = productDetailRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_DETAIL_NOT_FOUND));
        ProductDetail productDetailRequest = productDetailMapper.toEntity(request);
        mergeProductDetail(productDetail, productDetailRequest);
        productDetailRepository.save(productDetail);
        return productDetailMapper.toResponse(productDetail);
    }



    @Override
    public ProductDetailResponse deleteProductDetail(Integer id) {
        return productDetailRepository.findById(id)
                .map(productDetail -> {
                    productDetailRepository.delete(productDetail);
                    return productDetailMapper.toResponse(productDetail);
                })
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_DETAIL_NOT_FOUND));
    }

    @Override
    public List<ProductDetailResponse> getProductDetailsByProductId(Integer productId) {
        return productDetailRepository.findAllByProductId(productId).stream()
                .map(productDetailMapper::toResponse)
                .toList();
    }

    private void mergeProductDetail(ProductDetail productDetail, ProductDetail productDetailRequest) {
       if (!productDetailRequest.getName().isBlank()){
           productDetail.setName(productDetailRequest.getName());
       }
       if (!productDetailRequest.getValue().isBlank()){
           productDetail.setValue(productDetailRequest.getValue());
       }
       if (productDetailRequest.getProduct() != null){
           productDetail.setProduct(productDetailRequest.getProduct());
       }
    }
}
