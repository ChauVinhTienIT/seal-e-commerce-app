package com.seal.ecommerce.product;

import com.seal.ecommerce.category.Category;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest(
         Integer id,
         @NotNull(message = "Product name is required")
         String name,
         @NotNull(message = "Product description is required")
         String description,

         @Positive(message = "Product available quantity must be greater than 0")
         Double availableQuantity,

         @Positive(message = "Product price must be greater than 0")
         BigDecimal price,

         @NotNull(message = "Product category is required")
         Integer categoryId
) {
}
