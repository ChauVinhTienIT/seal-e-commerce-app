package com.seal.ecommerce.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InventoryUpdateRequest {
    @NotNull
    Integer inventoryId;
    Integer colorId;
    Integer availableQuantity;
    Double listPrice;
    Double discountPercent;
    Double cost;
    Integer enabled;
}
