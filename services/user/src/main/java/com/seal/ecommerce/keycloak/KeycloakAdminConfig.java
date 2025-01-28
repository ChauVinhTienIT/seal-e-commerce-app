package com.seal.ecommerce.keycloak;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakAdminConfig {
    @Value("${keycloak.server-url}")
    private String keycloakUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.username}")
    private String keycloakUsername;
    @Value("${keycloak.username}")
    private String keycloakPassword;
    @Bean
    public Keycloak keycloakAdminClient() {
        return KeycloakBuilder.builder()
                .serverUrl(keycloakUrl)
                .realm(realm) // Realm admin
                .clientId(clientId)
//                .clientSecret("qrlyH4KhOQS0aS0w5yOAtkVWdSOXoUca")
//                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .username(keycloakUsername) // Keycloak admin username
                .password(keycloakPassword) // Keycloak admin password
                .build();
    }
}