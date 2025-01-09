package com.seal.ecommerce.dto.request;

import com.seal.ecommerce.entity.Product;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ProductDetailCreationRequest {
    @NotNull(message = "Product detail name is required")
    private String name;

    @NotNull(message = "Product detail value is required")
    private String value;

    @NotNull(message = "Product id is required")
    private Integer productId;

}
