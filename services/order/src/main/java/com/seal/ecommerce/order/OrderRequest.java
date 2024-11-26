package com.seal.ecommerce.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import com.seal.ecommerce.product.PurchaseRequest;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        Integer id,
        String reference,

        @Positive(message = "Amount should be positive")
        BigDecimal amount,

        @NotNull(message = "Payment method should be precised")
        PaymentMethod paymentMethod,

        @NotNull(message = "Customer ID should be provided")
        @NotEmpty(message = "Customer ID should not be empty")
        @NotBlank(message = "Customer ID should not be blank")
        String customerId,

        @NotEmpty(message = "At least one product should be provided")
        List<PurchaseRequest> products
) {
}
