package com.seal.ecommerce.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ProductUpdateRequest {
    @NotNull(message = "Product name is required")
    private String name;

    @NotNull(message = "Product short description is required")
    private String shortDescription;

    @NotNull(message = "Product full description is required")
    private String fullDescription;

    @NotNull(message = "Product main image is required")
    private String mainImage;

    @NotNull(message = "Product brand is required")
    private String brand;

    @NotNull(message = "Product sub category is required")
    private Integer subCategory;

    @NotNull(message = "Product enabled is required")
    private Integer enabled;

    @NotNull(message = "Product is available is required")
    private boolean isAvailable;

    @NotNull(message = "Product is active is required")
    private boolean isActive;
}
