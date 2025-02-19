package com.seal.ecommerce.config;

import com.seal.ecommerce.keycloak.KeycloakJwtAuthenticationConverter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
//    @Value("{server.servlet.context-path}")
//    @NonFinal
//    public String contextPath;
    @NonFinal
    public final String[] PUBLIC_ENDPOINTS = {"/auth/register", "/auth/login", "/swagger-ui/**", "/v3/api-docs", "/auth/activate-account"};
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
                .oauth2ResourceServer(auth ->
                        auth.jwt(token -> token.jwtAuthenticationConverter(new KeycloakJwtAuthenticationConverter())));
        return http.build();
    }
    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withIssuerLocation("http://localhost:9090/realms/seal").build();
    }
}
