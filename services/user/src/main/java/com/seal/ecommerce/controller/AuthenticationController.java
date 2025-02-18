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
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;

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

    @PostMapping("/login")
    public ApiResponse<AccessTokenResponse> authenticate(
            @RequestBody @Valid LoginRequest request
    )
    {
        return ApiResponse.<AccessTokenResponse>builder()
                .result(authenticationService.authenticate(request))
                .build();

    }
    @GetMapping("/activate-account")
    public void activate(
            @RequestParam("token") String token
    ) throws MessagingException {
    }
    @GetMapping("/test")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ApiResponse<String> test(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        return ApiResponse.<String>builder()
                .code(100)
                .result("Test Success")
                .build();
    }
    @GetMapping("/test1")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<String> test1(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        return ApiResponse.<String>builder()
                .code(100)
                .result("Test 1 Success")
                .build();
    }
}