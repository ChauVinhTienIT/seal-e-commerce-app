package com.seal.ecommerce.service;

import com.seal.ecommerce.dto.request.LoginRequest;
import com.seal.ecommerce.dto.request.RegisterRequest;
import com.seal.ecommerce.dto.response.LoginResponse;
import jakarta.mail.MessagingException;
import org.keycloak.representations.AccessTokenResponse;

public interface AuthenticationService {
    void register(RegisterRequest request) throws MessagingException;

    AccessTokenResponse authenticate(LoginRequest request);
}
