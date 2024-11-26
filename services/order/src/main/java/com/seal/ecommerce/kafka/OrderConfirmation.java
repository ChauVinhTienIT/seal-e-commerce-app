package com.seal.ecommerce.kafka;

import com.seal.ecommerce.customer.CustomerResponse;
import com.seal.ecommerce.order.PaymentMethod;
import com.seal.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
