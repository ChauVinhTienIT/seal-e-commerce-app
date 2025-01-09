package com.seal.ecommerce.mapper;

import com.seal.ecommerce.dto.request.ProductCreationRequest;
import com.seal.ecommerce.dto.request.ProductUpdateRequest;
import com.seal.ecommerce.dto.response.ProductResponse;
import com.seal.ecommerce.entity.Product;
import com.seal.ecommerce.entity.ProductImage;
import com.seal.ecommerce.entity.SubCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${server.address}")
    private String host;

    @Value("${server.port}")
    private String port;


    @Mapping(target = "subCategory", source = "subCategory", qualifiedByName = "intToSubCategory")
    public abstract Product creationRequestToEntity(ProductCreationRequest request);

    @Mapping(target = "subCategory", source = "subCategory", qualifiedByName = "intToSubCategory")
    public abstract Product updateRequestToEntity(ProductUpdateRequest request);

    @Mapping(target = "subCategory", source = "subCategory.id")
    @Mapping(target = "mainImage", source = "id", qualifiedByName = "uirToUrl")
    @Mapping(target = "imageIds", source = "images", qualifiedByName = "productImagesToImageIds")
    @Mapping(target = "images", source = "images", qualifiedByName = "productImagesToUrls")
    public abstract ProductResponse toResponse(Product entity);

    @Named("intToSubCategory")
    public SubCategory intToSubCategory(Integer subCategoryId) {
        SubCategory subCategory = new SubCategory();
        subCategory.setId(subCategoryId);
        return subCategory;
    }

    @Named("uirToUrl")
    public String uirToUrl(Integer productId) {
        return "http://" + host + ":" + port + contextPath + "/products/main-image/" + productId;
    }

    @Named("productImagesToImageIds")
    public List<Integer> productImagesToImageIds(Set<ProductImage> productImages){
        return productImages.stream().map(ProductImage::getId).collect(Collectors.toList());
    }

    @Named("productImagesToUrls")
    public List<String> productImagesToUrls(Set<ProductImage> productImages){
        return productImages.stream().map(productImage -> "http://" + host + ":" + port + contextPath + "/products/images/" + productImage.getProduct().getId() + "/" + productImage.getId()).collect(Collectors.toList());
    }
}