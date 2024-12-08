package com.seal.ecommerce.mapper;

import com.seal.ecommerce.dto.request.InventoryCreationRequest;
import com.seal.ecommerce.dto.request.ProductCreationRequest;
import com.seal.ecommerce.dto.response.InventoryCreationResponse;
import com.seal.ecommerce.dto.response.InventoryResponse;
import com.seal.ecommerce.dto.response.ProductResponse;
import com.seal.ecommerce.entity.Inventory;
import com.seal.ecommerce.entity.Product;
import com.seal.ecommerce.entity.SubCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    InventoryCreationResponse toInventoryCreationResponse(Inventory inventory);
    InventoryResponse toInventoryResponse(Inventory inventory);
    Inventory toInventoryFromCreationRequest(InventoryCreationRequest request);
}