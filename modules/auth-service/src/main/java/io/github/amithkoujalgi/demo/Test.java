package io.github.amithkoujalgi.demo;

import io.github.amithkoujalgi.demo.services.KeycloakService;
import org.keycloak.representations.idm.UserRepresentation;

public class Test {
    public static void main(String[] args) {
        String keycloakServerUrl = "http://localhost:9000";
        String masterRealmName = "master";
        String adminUsername = "admin";
        String adminPassword = "admin";
        String adminClientId = "admin-cli";

        KeycloakService keycloakService = new KeycloakService(keycloakServerUrl, masterRealmName, adminUsername, adminPassword, adminClientId);
//        UserRepresentation user = keycloakService.getUserByUsername("myapp", "test");
//        System.out.println(user.getEmail());

        String jwt = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJFTWpfa3FEak1GNnVDTnZILVdpX2lLTTlJTEhYRWpDajEzTFZWMUxscnhBIn0.eyJleHAiOjE3MDE4ODAwNzUsImlhdCI6MTcwMTg4MDAxNSwiYXV0aF90aW1lIjoxNzAxODgwMDE0LCJqdGkiOiJmOTllN2YxNC01ZTNiLTQzNDktODg5OS1kODdhZWU1NDg4MzkiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjkwMDAvcmVhbG1zL215YXBwIiwic3ViIjoiNTk3YjMzMDktYWNhZC00OWRjLTgwZDUtNGI4N2MxMjJjZDU1IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoibXlhcHAtY2xpZW50Iiwibm9uY2UiOiI2MWJmZmExNS03MTM5LTRmMGYtODVhYS1hYzhhMjBiZjBmNTgiLCJzZXNzaW9uX3N0YXRlIjoiNWU3MzQwZDEtY2ZkMi00MTFmLWFlYjgtOWU5ZTI0ZWYzNmZkIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0Ojk5MTAiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtbWFzdGVyIiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwic2NvcGUiOiJvcGVuaWQgZW1haWwgcHJvZmlsZSIsInNpZCI6IjVlNzM0MGQxLWNmZDItNDExZi1hZWI4LTllOWUyNGVmMzZmZCIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJuYW1lIjoiVGVzdCBVc2VyIiwicHJlZmVycmVkX3VzZXJuYW1lIjoidGVzdCIsImdpdmVuX25hbWUiOiJUZXN0IiwiZmFtaWx5X25hbWUiOiJVc2VyIiwiZW1haWwiOiJ0ZXN0QGdtYWlsLmNvbSJ9.XeWPNhkG-IeoSupIortHRP13uIGKkxsm-vpbxAZuX_mI0qb00Zgjhw9vjWSPD8F67RphfXiouxS4lBps6QCJbCB2nv5gHBU4Ngwazj06jeL6iFYaRzRGV2sUqYCnMVEdbt38bx5jjLFBjk0zHLmos0_8YhNv3ihZRMhfnETtnPxyW_1DIjY53ugm5lN7lGEPktpTQc9gpHi1Q0JbuSkT63Z3uoZOUiQgfgPSChQLODBjeKopb0phDdQFpC6iTOrpwbQ0S1O30PKCyUWKceTXn8Po1ruyoue5_PmJoRYPw4aF353fUEBOYq7pJ7CYgI7DLzpx6jrKMU4ZFZN2hckDBA";

        UserRepresentation user = keycloakService.getUserFromJWT("myapp", jwt);
        System.out.println(user.getEmail());
    }
}
