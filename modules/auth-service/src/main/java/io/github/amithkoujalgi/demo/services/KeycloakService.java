package io.github.amithkoujalgi.demo.services;

import com.auth0.jwt.JWT;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public class KeycloakService {
    private final Keycloak keycloakClient;

    public KeycloakService(String keycloakServerUrl, String masterRealmName, String adminUsername, String adminPassword, String adminClientId) {
        this.keycloakClient = Keycloak.getInstance(keycloakServerUrl, masterRealmName, adminUsername, adminPassword, adminClientId);
    }

    public List<UserRepresentation> listUsers(String realmName) {
        return this.keycloakClient.realm(realmName).users().list();
    }

    public UserRepresentation getUserByUsername(String realmName, String username) {
        return this.keycloakClient.realm(realmName).users().searchByUsername(username, true).get(0);
    }

    public UserRepresentation getUserByUserID(String realmName, String userID) {
        RealmResource realmResource = keycloakClient.realm(realmName);
        UsersResource usersResource = realmResource.users();
        String searchKey = "id:" + userID;
        List<UserRepresentation> users = usersResource.search(searchKey, 0, 1);
        if (!users.isEmpty()) {
            return users.get(0);
        } else {
            return null;
        }
    }

    private String getUserIDFromJWT(String jwt) {
        return JWT.decode(jwt).getClaims().get("sub").asString();
    }

    public UserRepresentation getUserFromJWT(String realmName, String jwt){
        return getUserByUserID(realmName, getUserIDFromJWT(jwt));
    }
}
