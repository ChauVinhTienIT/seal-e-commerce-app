package com.seal.ecommerce.service;

import com.seal.ecommerce.dto.request.LoginRequest;
import com.seal.ecommerce.dto.request.RegisterRequest;
import com.seal.ecommerce.dto.response.LoginResponse;
import jakarta.mail.MessagingException;

public interface AuthenticationService {
    void register(RegisterRequest request) throws MessagingException;

    LoginResponse authenticate(LoginRequest request);

    void activate(String token) throws MessagingException;
}
