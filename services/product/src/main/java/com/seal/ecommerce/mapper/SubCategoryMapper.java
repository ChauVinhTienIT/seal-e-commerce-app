package com.seal.ecommerce.mapper;

import com.seal.ecommerce.dto.request.SubCategoryCreationRequest;
import com.seal.ecommerce.dto.response.SubCategoryResponse;
import com.seal.ecommerce.entity.Category;
import com.seal.ecommerce.entity.SubCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface SubCategoryMapper {

    @Mapping(target = "category", source = "categoryId", qualifiedByName = "categoryIdToCategory")
    SubCategory creationRequestToEntity(SubCategoryCreationRequest request);

    @Mapping(target = "categoryId", source = "category.id")
    SubCategoryResponse toResponse(SubCategory entity);

    @Named("categoryIdToCategory")
    default Category categoryIdToCategory(Integer categoryId) {
        Category category = new Category();
        category.setId(categoryId);
        return category;
    }
}
