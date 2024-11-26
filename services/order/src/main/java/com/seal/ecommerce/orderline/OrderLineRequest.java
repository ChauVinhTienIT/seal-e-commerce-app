package com.seal.ecommerce.orderline;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderLineRequest(
        Integer id,
        Integer orderId,

        @NotNull(message = "Product ID should be provided")
        Integer productId,

        @Positive(message = "Quantity should be positive")
        double quantity
) {
}
