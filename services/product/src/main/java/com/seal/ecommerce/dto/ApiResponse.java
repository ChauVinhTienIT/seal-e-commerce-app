package com.seal.ecommerce.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiResponse<T> {
    @Builder.Default
    private int code = 100;
    private String message;
    private T result;
}
