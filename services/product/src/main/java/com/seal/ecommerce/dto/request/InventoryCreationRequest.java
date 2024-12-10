package com.seal.ecommerce.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InventoryCreationRequest {
    @NotNull
    Integer productId;
    @NotNull
    Integer colorId;
    @NotNull
    Integer availableQuantity;
    @NotNull
    Double listPrice;
    Double discount;
    Double cost;
    Integer enabled;
}
