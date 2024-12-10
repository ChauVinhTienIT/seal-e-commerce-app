package com.seal.ecommerce.service;

import com.seal.ecommerce.dto.request.InventoryCreationRequest;
import com.seal.ecommerce.dto.request.InventoryUpdateRequest;
import com.seal.ecommerce.dto.response.InventoryCreationResponse;
import com.seal.ecommerce.dto.response.InventoryResponse;
import com.seal.ecommerce.dto.response.InventoryUpdateResponse;
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
import org.springframework.stereotype.Service;

import java.util.List;
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
    public InventoryCreationResponse createInventory(InventoryCreationRequest request) {
        var productId = request.getProductId();
        var colorId = request.getColorId();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Color color = colorRepository.findById(colorId)
                .orElseThrow(() -> new RuntimeException("Color not found"));
        Inventory inventory = inventoryMapper.toInventoryFromCreationRequest(request);
        inventory.setProduct(product);
        inventory.setColor(color);
        product.setAvailable(true);
        productRepository.save(product);
        return inventoryMapper.toInventoryCreationResponse(inventoryRepository.save(inventory));
    }

    @Override
    public InventoryUpdateResponse updateInventory(Integer inventoryId, InventoryUpdateRequest request) {
        // Fetch the inventory entity by ID
        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new AppException(ErrorCode.INVENTORY_NOT_FOUND));

        // Update the inventory fields with the provided request data
        inventory.setAvailableQuantity(request.getAvailableQuantity());
        inventory.setListPrice(request.getListPrice());
        inventory.setDiscountPercent(request.getDiscount());
        inventory.setCost(request.getCost());
        inventory.setEnabled(request.getEnabled());

        // Save the updated inventory entity
        inventoryRepository.save(inventory);

        // Map the updated entity to the response object
        return InventoryUpdateResponse.builder()
                .availableQuantity(inventory.getAvailableQuantity())
                .listPrice(inventory.getListPrice())
                .discount(inventory.getDiscountPercent())
                .cost(inventory.getCost())
                .enabled(inventory.getEnabled())
                .build();
    }

    @Override
    public InventoryResponse getInventory(Integer inventoryId) {
        // Fetch the inventory entity by ID
        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new AppException(ErrorCode.INVENTORY_NOT_FOUND));

        // Map the entity to the response object
        return InventoryResponse.builder()
                .id(inventory.getId())
                .product(inventory.getProduct())
                .color(inventory.getColor())
                .availableQuantity(inventory.getAvailableQuantity())
                .listPrice(inventory.getListPrice())
                .discount(inventory.getDiscountPercent())
                .cost(inventory.getCost())
                .enabled(inventory.getEnabled())
                .build();
    }

    @Override
    public List<InventoryResponse> getAllByProduct(Integer productId) {
        // Fetch the product entity by ID
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        // Fetch all inventory entities associated with the product
        List<Inventory> inventories = inventoryRepository.findByProduct(product);

        // Map the list of inventory entities to a list of response objects
        return inventories.stream()
                .map(inventory -> InventoryResponse.builder()
                        .id(inventory.getId())
                        .product(inventory.getProduct())
                        .color(inventory.getColor())
                        .availableQuantity(inventory.getAvailableQuantity())
                        .listPrice(inventory.getListPrice())
                        .discount(inventory.getDiscountPercent())
                        .cost(inventory.getCost())
                        .enabled(inventory.getEnabled())
                        .build())
                .collect(Collectors.toList());
    }

}
