package com.seal.ecommerce.service;

import com.seal.ecommerce.dto.PageResponse;
import com.seal.ecommerce.dto.request.InventoryCreationRequest;
import com.seal.ecommerce.dto.request.InventoryPurchaseRequest;
import com.seal.ecommerce.dto.request.InventoryUpdateRequest;
import com.seal.ecommerce.dto.response.InventoryCreationResponse;
import com.seal.ecommerce.dto.response.InventoryPurchaseResponse;
import com.seal.ecommerce.dto.response.InventoryResponse;
import com.seal.ecommerce.dto.response.InventoryUpdateResponse;

import java.util.List;

public interface InventoryService {
    InventoryCreationResponse createInventory(InventoryCreationRequest request);
    InventoryUpdateResponse updateInventory(Integer inventoryId, InventoryUpdateRequest request);

    InventoryResponse getInventory(Integer inventoryId);

    List<InventoryResponse> getAllByProduct(Integer productId);

    PageResponse<InventoryResponse> getAllToPage(long page, long size);

    List<InventoryPurchaseResponse> purchase(List<InventoryPurchaseRequest> request);
}
