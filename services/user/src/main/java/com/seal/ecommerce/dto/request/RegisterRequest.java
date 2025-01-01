package com.seal.ecommerce.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstName;
    private String lastName;

    @NotNull
    private String email;

    @Size(min = 8, message = "The length of password must be more than 8 characters")
    @NotNull
    private String password;

    private String phoneNumber;
}
