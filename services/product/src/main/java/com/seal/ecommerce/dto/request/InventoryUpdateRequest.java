package com.seal.ecommerce.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InventoryUpdateRequest {
    Integer inventoryId;
    Integer availableQuantity;
    Double listPrice;
    Double discount;
    Double cost;
    Integer enabled;
}
