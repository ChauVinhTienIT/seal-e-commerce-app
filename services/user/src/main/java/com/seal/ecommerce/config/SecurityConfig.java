package com.seal.ecommerce.config;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    @Value("{server.servlet.context-path}")
    @NonFinal
    public String contextPath;
    @NonFinal
    public final String[] PUBLIC_ENDPOINTS = {"/auth/register", "/auth/login", "/swagger-ui/**", "/v3/api-docs"};
    AuthenticationProvider authenticationProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(
                        Customizer.withDefaults()
                )
                .csrf(
                        AbstractHttpConfigurer::disable
                )
                .authorizeHttpRequests(request ->
                        request.requestMatchers(PUBLIC_ENDPOINTS)
                                    .permitAll()
                                .anyRequest()
                                    .authenticated()
                )
                .sessionManagement(
                        session -> session.sessionCreationPolicy((STATELESS))
                )
                .authenticationProvider(authenticationProvider);
        return http.build();
    }
}
