package com.seal.ecommerce.service;

import com.seal.ecommerce.dto.request.LoginRequest;
import com.seal.ecommerce.dto.request.RegisterRequest;
import com.seal.ecommerce.dto.response.LoginResponse;
import com.seal.ecommerce.keycloak.KeycloakService;
import com.seal.ecommerce.keycloak.KeycloakUser;
import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthenticationServiceImpl
        implements AuthenticationService{
    KeycloakService keycloakService;
    @NonFinal
    @Value("{application.mailing.frontend.activation-url}")
    String activationUrl;
    @Override
    public void register(RegisterRequest request) throws MessagingException {
        keycloakService.registerUser(KeycloakUser.builder()
                        .username(request.getEmail())
                        .email(request.getEmail())
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .password(request.getPassword())
                .build());
    }

    @Override
    public AccessTokenResponse authenticate(LoginRequest request) {
        return keycloakService.login(request.getEmail(), request.getPassword());
    }

}
