package com.seal.ecommerce.service;

import com.seal.ecommerce.dto.request.LoginRequest;
import com.seal.ecommerce.dto.request.RegisterRequest;
import com.seal.ecommerce.dto.response.LoginResponse;

public interface AuthenticationService {
    void register(RegisterRequest request);

    LoginResponse authenticate(LoginRequest request);

    void activate(String token);
}
