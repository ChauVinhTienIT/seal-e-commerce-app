package com.seal.ecommerce.service;

import com.seal.ecommerce.dto.PageResponse;
import com.seal.ecommerce.dto.request.InventoryCreationRequest;
import com.seal.ecommerce.dto.request.InventoryPurchaseRequest;
import com.seal.ecommerce.dto.request.InventoryUpdateRequest;
import com.seal.ecommerce.dto.response.InventoryPurchaseResponse;
import com.seal.ecommerce.dto.response.InventoryResponse;
import com.seal.ecommerce.entity.Color;
import com.seal.ecommerce.entity.Inventory;
import com.seal.ecommerce.entity.Product;
import com.seal.ecommerce.exception.AppException;
import com.seal.ecommerce.exception.ErrorCode;
import com.seal.ecommerce.mapper.InventoryMapper;
import com.seal.ecommerce.repository.ColorRepository;
import com.seal.ecommerce.repository.InventoryRepository;
import com.seal.ecommerce.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryServiceImpl implements InventoryService {
    ProductRepository productRepository;
    ColorRepository colorRepository;
    InventoryMapper inventoryMapper;
    InventoryRepository inventoryRepository;
    @Override
    public InventoryResponse createInventory(InventoryCreationRequest request) {
        var productId = request.getProductId();
        var colorId = request.getColorId();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        Color color = colorRepository.findById(colorId)
                .orElseThrow(() -> new AppException(ErrorCode.COLOR_NOT_FOUND));
        Inventory inventory = inventoryMapper.toInventoryFromCreationRequest(request);
        inventory.setProduct(product);
        inventory.setColor(color);
        product.setAvailable(true);
        productRepository.save(product);
        return inventoryMapper.toInventoryResponse(inventoryRepository.save(inventory));
    }

    @Override
    public InventoryResponse updateInventory(InventoryUpdateRequest request) {
        Inventory inventory = inventoryRepository.findById(request.getInventoryId())
                .orElseThrow(() -> new AppException(ErrorCode.INVENTORY_NOT_FOUND));
        if(request.getColorId() != null){
            Color color = colorRepository.findById(request.getColorId())
                    .orElseThrow(() -> new AppException(ErrorCode.COLOR_NOT_FOUND));
            inventory.setColor(color);
        }
        inventoryMapper.updateInventoryFromRequest(request, inventory);
        return inventoryMapper.toInventoryResponse(inventoryRepository.save(inventory));
    }

    @Override
    public InventoryResponse getInventory(Integer inventoryId) {
        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new AppException(ErrorCode.INVENTORY_NOT_FOUND));
        return inventoryMapper.toInventoryResponse(inventory);
    }

    @Override
    public List<InventoryResponse> getAllByProduct(Integer productId) {
        // Fetch all inventory entities associated with the product
        var inventories = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND_IN_INVENTORY));

        return inventories.stream()
                .map(inventoryMapper::toInventoryResponse)
                .collect(Collectors.toList());
    }
    @Override
    public PageResponse<InventoryResponse> getAllToPage(long page, long size) {
        Sort sort = Sort.by("id").ascending();
        Pageable pageable = PageRequest.of((int) (page - 1), (int) size, sort);
        var pageData = inventoryRepository.findAll(pageable);
        return PageResponse.<InventoryResponse>builder()
                .currentPage(page)
                .totalElement(pageData.getTotalElements())
                .totalPages(pageData.getTotalPages())
                .data(pageData.getContent().stream().map(inventoryMapper::toInventoryResponse).toList())
                .build();
    }

    @Override
    public List<InventoryPurchaseResponse> purchase(List<InventoryPurchaseRequest> request) {
        List<InventoryPurchaseResponse> responses = new ArrayList<>();
        List<Integer> inventoryIds = request.stream()
                .map(InventoryPurchaseRequest::getInventoryId)
                .toList();
        List<Inventory> inventories = inventoryRepository.findAllById(inventoryIds);
        Map<Integer, Inventory> inventoryMap = inventories.stream()
                .collect(Collectors.toMap(Inventory::getId, inventory -> inventory));

        for (InventoryPurchaseRequest purchaseRequest : request) {
            try{
                var purchaseSuccessResponse = purchaseSingleInventory(purchaseRequest, inventoryMap);
                responses.add(purchaseSuccessResponse);
            }
            catch (IllegalArgumentException exception){
                responses.add(InventoryPurchaseResponse.builder()
                        .inventoryId(purchaseRequest.getInventoryId())
                        .isSuccess(false)
                        .quantity(0)
                        .message("Ordering failed: " + exception.getMessage())
                        .build()
                );
            }

        }
        return responses;
    }

    @Transactional
    protected InventoryPurchaseResponse purchaseSingleInventory(InventoryPurchaseRequest purchaseRequest, Map<Integer, Inventory> inventoryMap){
        Inventory inventory = inventoryMap.get(purchaseRequest.getInventoryId());
        if (inventory == null) {
            throw new IllegalArgumentException("Inventory not found for ID: " + purchaseRequest.getInventoryId());
        }
        if (inventory.getAvailableQuantity() < purchaseRequest.getAvailableQuantity()) {
            throw new IllegalArgumentException("Insufficient stock for Inventory ID: " + purchaseRequest.getInventoryId());
        }
        inventory.setAvailableQuantity(inventory.getAvailableQuantity() - purchaseRequest.getAvailableQuantity());
        inventoryRepository.save(inventory);
        return InventoryPurchaseResponse.builder()
                .inventoryId(inventory.getId())
                .isSuccess(true)
                .quantity(purchaseRequest.getAvailableQuantity())
                .message(inventory.getProduct().getName() + " "
                        + inventory.getColor().getName() + " "
                        + "is ordered successfully!")
                .build();
    }
}
