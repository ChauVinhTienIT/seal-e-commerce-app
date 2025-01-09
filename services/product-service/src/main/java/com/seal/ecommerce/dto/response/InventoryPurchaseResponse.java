package com.seal.ecommerce.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InventoryPurchaseResponse {
    Integer inventoryId;
    Integer quantity;
    boolean isSuccess;
    String message;
}
