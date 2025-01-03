package com.seal.ecommerce.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ProductCreationRequest {
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
}
