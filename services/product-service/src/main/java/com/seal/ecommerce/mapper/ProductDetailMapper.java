package com.seal.ecommerce.mapper;

import com.seal.ecommerce.dto.request.ProductDetailCreationRequest;
import com.seal.ecommerce.dto.response.ProductDetailResponse;
import com.seal.ecommerce.entity.Product;
import com.seal.ecommerce.entity.ProductDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProductDetailMapper {
    @Mapping(target = "product", source = "productId", qualifiedByName = "productIdToProduct")
    ProductDetail toEntity(ProductDetailCreationRequest request);

    @Mapping(target = "productId", source = "product.id")
    ProductDetailResponse toResponse(ProductDetail entity);

    @Named("productIdToProduct")
    default Product productIdToProduct(Integer productId) {
        Product product = new Product();
        product.setId(productId);
        return product;
    }
}
