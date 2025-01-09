package com.seal.ecommerce.mapper;

import com.seal.ecommerce.dto.request.CategoryCreationRequest;
import com.seal.ecommerce.dto.response.CategoryResponse;
import com.seal.ecommerce.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category creationRequestToEntity(CategoryCreationRequest request);
    CategoryResponse toResponse(Category entity);
}
