package com.seal.ecommerce.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InventoryCreationResponse {
    Integer productId;
    Integer colorId;
    Integer availableQuantity;
    Double listPrice;
    Double discount;
    Double cost;
    Integer enabled;
}
