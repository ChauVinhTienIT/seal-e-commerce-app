package com.seal.ecommerce.dto.response;

import com.seal.ecommerce.entity.Color;
import com.seal.ecommerce.entity.Product;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InventoryResponse {
    private Integer id;
    private Product product;
    private Color color;
    private Integer availableQuantity;
    private Double listPrice;
    private Double discount;
    private Double cost;
    private Integer enabled;
}
