package com.seal.ecommerce.customer;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequest {
    private String id;

    @NotNull(message = "Customer firstname is required")
    private String firstname;

    @NotNull(message = "Customer lastname is required")
    private String lastname;

    @NotNull(message = "Customer email is required")
    @Email(message = "Customer email address is invalid")
    private String email;

    @NotNull(message = "Customer phone number is required")
    private String phone;

    private Address address;
}
