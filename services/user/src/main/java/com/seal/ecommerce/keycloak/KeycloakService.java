package com.seal.ecommerce.keycloak;

import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KeycloakService {
    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.server-url}")
    private String keycloakUrl;

    private final Keycloak keycloak;
    public Response registerUser(KeycloakUser keycloakUser){
        RealmResource realmResource = keycloak.realm(realm);

        UserRepresentation user = new UserRepresentation();
        user.setUsername(keycloakUser.getUsername());
        user.setEmail(keycloakUser.getEmail());
        user.setFirstName(keycloakUser.getFirstName());
        user.setLastName(keycloakUser.getLastName());
        user.setEnabled(true);

        // Set user credentials
        CredentialRepresentation credentials = new CredentialRepresentation();
        credentials.setType(CredentialRepresentation.PASSWORD);
        credentials.setValue(keycloakUser.getPassword());
        credentials.setTemporary(false);
        user.setCredentials(Collections.singletonList(credentials));

        Response response = realmResource.users().create(user);

        // get new user
        String userId = CreatedResponseUtil.getCreatedId(response);
        UserResource userResource = realmResource.users().get(userId);



        RoleRepresentation realmRole = realmResource.roles().get("USER").toRepresentation();
        userResource.roles().realmLevel().add(Collections.singletonList(realmRole));
        // Register the user
        return response;
    }
    // Đăng nhập và lấy Access Token
    public AccessTokenResponse login(String username, String password) {
        Keycloak keycloakAuth = KeycloakBuilder.builder()
                .serverUrl(keycloakUrl)
                .realm(realm)
                .clientId("ecommerce-application")
                .clientSecret("0792GxiBR7d0tyxdaEyl9u7yl1mENnSc")
                .username(username)
                .password(password)
                .grantType(OAuth2Constants.PASSWORD)
                .build();
        return keycloakAuth.tokenManager().getAccessToken();
    }

    // Lấy danh sách tất cả người dùng
    public List<UserRepresentation> getAllUsers() {
        return keycloak.realm(realm).users().list();
    }

    // Tìm người dùng theo email
    public UserRepresentation getUserByEmail(String email) {
        UsersResource usersResource = keycloak.realm(realm).users();
        List<UserRepresentation> users = usersResource.search(email, true);
        return users.isEmpty() ? null : users.get(0);
    }
}
