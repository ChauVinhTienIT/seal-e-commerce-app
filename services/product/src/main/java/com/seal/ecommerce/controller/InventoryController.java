package com.seal.ecommerce.controller;

import com.seal.ecommerce.dto.ApiResponse;
import com.seal.ecommerce.dto.PageResponse;
import com.seal.ecommerce.dto.request.InventoryCreationRequest;
import com.seal.ecommerce.dto.request.InventoryUpdateRequest;
import com.seal.ecommerce.dto.response.InventoryCreationResponse;
import com.seal.ecommerce.dto.response.InventoryResponse;
import com.seal.ecommerce.dto.response.InventoryUpdateResponse;
import com.seal.ecommerce.service.InventoryService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryController {
    InventoryService inventoryService;

    @PostMapping
    ApiResponse<InventoryCreationResponse> createInventory(
            @RequestBody  @Valid InventoryCreationRequest request
            ){
        return ApiResponse.<InventoryCreationResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Inventory create successfully")
                .result(inventoryService.createInventory(request))
                .build();
    }
    @PutMapping("/{id}")
    ApiResponse<InventoryUpdateResponse> updateInventory(
            @PathVariable("id") Integer inventoryId,
            @RequestBody InventoryUpdateRequest request
            )
    {
        return ApiResponse.<InventoryUpdateResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Inventory update successfully")
                .result(inventoryService.updateInventory(inventoryId, request))
                .build();
    }
    @GetMapping("/{id}")
    ApiResponse<InventoryResponse> getInventory(
            @PathVariable("id") Integer inventoryId
    )
    {
        return ApiResponse.<InventoryResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Inventory get successfully")
                .result(inventoryService.getInventory(inventoryId))
                .build();
    }
    @GetMapping("/product/{id}")
    ApiResponse<List<InventoryResponse>> getAllByProduct(
            @PathVariable("id") Integer productId
    )
    {
        return ApiResponse.<List<InventoryResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Inventory get all by product successfully")
                .result(inventoryService.getAllByProduct(productId))
                .build();
    }
    @GetMapping
    ApiResponse<PageResponse<InventoryResponse>> getAll(
            @RequestParam(value = "page", required = false, defaultValue = "1") long page,
            @RequestParam(value = "size", required = false, defaultValue = "10") long size
    ){
        return ApiResponse.<PageResponse<InventoryResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Inventory get all successfully")
                .result(inventoryService.getAllToPage(page, size))
                .build();
    }
}
