package com.seal.ecommerce.product;

import java.math.BigDecimal;

public record ProductPurchaseResponse(
        Integer productId,
        String productName,
        String productDescription,
        double quantity,
        BigDecimal price
) {
}
