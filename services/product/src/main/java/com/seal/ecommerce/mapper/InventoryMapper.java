package com.seal.ecommerce.mapper;

import com.seal.ecommerce.dto.request.InventoryCreationRequest;
import com.seal.ecommerce.dto.request.InventoryUpdateRequest;
import com.seal.ecommerce.dto.response.InventoryResponse;
import com.seal.ecommerce.entity.Inventory;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    InventoryResponse toInventoryResponse(Inventory inventory);
    Inventory toInventoryFromCreationRequest(InventoryCreationRequest request);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateInventoryFromRequest(InventoryUpdateRequest request, @MappingTarget Inventory inventory);
}