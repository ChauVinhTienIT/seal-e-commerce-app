package com.seal.ecommerce.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubCategoryCreationRequest {
    @NotNull(message = "Sub category name is required")
    private String name;
    @NotNull(message = "Category id is required")
    private Integer categoryId;
}
