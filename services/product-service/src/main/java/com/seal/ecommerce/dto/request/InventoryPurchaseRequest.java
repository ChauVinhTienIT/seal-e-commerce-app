package com.seal.ecommerce.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InventoryPurchaseRequest {
    Integer inventoryId;
    Integer availableQuantity;
}
