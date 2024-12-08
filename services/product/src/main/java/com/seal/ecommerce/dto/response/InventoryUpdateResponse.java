package com.seal.ecommerce.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InventoryUpdateResponse {
    Integer availableQuantity;
    Double listPrice;
    Double discount;
    Double cost;
    Integer enabled;
}
