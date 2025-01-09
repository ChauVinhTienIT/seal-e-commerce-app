package com.seal.ecommerce.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CategoryCreationRequest {
    @NotNull(message = "Category name is required")
    private String name;
    private String image;
}
