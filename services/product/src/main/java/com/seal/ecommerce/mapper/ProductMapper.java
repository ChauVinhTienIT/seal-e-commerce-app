package com.seal.ecommerce.mapper;

import com.seal.ecommerce.dto.request.ProductCreationRequest;
import com.seal.ecommerce.dto.request.ProductUpdateRequest;
import com.seal.ecommerce.dto.response.ProductResponse;
import com.seal.ecommerce.entity.Product;
import com.seal.ecommerce.entity.SubCategory;
import lombok.Value;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "subCategory", source = "subCategory", qualifiedByName = "intToSubCategory")
    Product creationRequestToEntity(ProductCreationRequest request);

    @Mapping(target = "subCategory", source = "subCategory", qualifiedByName = "intToSubCategory")
    Product updateRequestToEntity(ProductUpdateRequest request);

    @Mapping(target = "subCategory", source = "subCategory.id")
    ProductResponse toResponse(Product entity);

    @Named("intToSubCategory")
    default SubCategory intToSubCategory(Integer subCategoryId) {
        SubCategory subCategory = new SubCategory();
        subCategory.setId(subCategoryId);
        return subCategory;
    }
}