package com.seal.ecommerce.controller;

import com.seal.ecommerce.dto.ApiResponse;
import com.seal.ecommerce.dto.request.LoginRequest;
import com.seal.ecommerce.dto.request.RegisterRequest;
import com.seal.ecommerce.dto.response.LoginResponse;
import com.seal.ecommerce.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;
    @PostMapping("/register")
    public ApiResponse<?> register(@RequestBody @Valid RegisterRequest request)
            throws MessagingException {
        authenticationService.register(request);
        return ApiResponse.<String>builder()
                .message("Register successful")
                .build();
    }

    @PostMapping("/authenticate")
    public ApiResponse<LoginResponse> authenticate(
            @RequestBody @Valid LoginRequest request
    )
    {
        return ApiResponse.<LoginResponse>builder()
                .result(authenticationService.authenticate(request))
                .build();

    }
    @GetMapping("/activate-account")
    public void activate(
            @RequestParam("token") String token
    ) throws MessagingException {
        authenticationService.activate(token);
    }
}