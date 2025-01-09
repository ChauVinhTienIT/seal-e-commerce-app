package com.seal.ecommerce.service;

import com.seal.ecommerce.dto.request.LoginRequest;
import com.seal.ecommerce.dto.request.RegisterRequest;
import com.seal.ecommerce.dto.response.LoginResponse;
import com.seal.ecommerce.email.EmailTemplateName;
import com.seal.ecommerce.entity.Role;
import com.seal.ecommerce.entity.User;
import com.seal.ecommerce.exception.AppException;
import com.seal.ecommerce.exception.ErrorCode;
import com.seal.ecommerce.repository.RoleRepository;
import com.seal.ecommerce.repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthenticationServiceImpl implements AuthenticationService{
    UserService userService;
    PasswordEncoder passwordEncoder;
    RoleService roleService;
    TokenService tokenService;
    AuthenticationManager authenticationManager;
    JwtService jwtService;
    EmailService emailService;
    UserRepository userRepository;
    @NonFinal
    @Value("{application.mailing.frontend.activation-url}")
    String activationUrl;
    @Override
    public void register(RegisterRequest request) throws MessagingException {
        Role userRole = roleService.getByTitle("USER");
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .enabled(false)
                .isVerified(false)
                .roles(Collections.singletonList(userRole))
                .build();
        userService.create(user);
        sendValidationEmail(user);
    }
    private void sendValidationEmail(User user) throws MessagingException {
        var activateToken = tokenService.generateAndSaveActivationToken(user);

        emailService.sendEmail(
                user.getEmail(),
                user.getName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                activateToken,
                "Activation account"
        );
    }
    @Override
    public LoginResponse authenticate(LoginRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = ((User)auth.getPrincipal());
        var claims = new HashMap<String, Object>();
        claims.put("fullName", user.getName());
        var token = jwtService.generateToken(claims, user);
        return LoginResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public void activate(String token) throws MessagingException {
        var authToken = tokenService.getByToken(token);

        var user = userService.getById(authToken.getUser().getId());
        if(LocalDateTime.now().isAfter(authToken.getExpiresAt())) {
            sendValidationEmail(user);
            throw new RuntimeException("Token has been expired. The new token has been sent");
        }
        user.setEnabled(true);
        userService.save(user);
        authToken.setValidatedAt(LocalDateTime.now());
        tokenService.save(authToken);
    }
}
