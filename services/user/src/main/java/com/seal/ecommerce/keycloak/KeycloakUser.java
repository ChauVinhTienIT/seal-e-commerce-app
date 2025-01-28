package com.seal.ecommerce.keycloak;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KeycloakUser {
    String email;
    String username;
    String password;
    String firstName;
    String lastName;
}
