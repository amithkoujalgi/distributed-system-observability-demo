package io.github.amithkoujalgi.demo.util;

import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Applications;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

public class AuthTokenValidator {
    private final RestTemplate restTemplate;
    private final EurekaClient discoveryClient;
    private final String authServiceAppName;

    public AuthTokenValidator(RestTemplate restTemplate, EurekaClient discoveryClient, String authServiceAppName) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
        this.authServiceAppName = authServiceAppName;
    }

    public boolean validateAuthToken(String token) {
        Applications applications = discoveryClient.getApplications(authServiceAppName);
        String url = applications.getRegisteredApplications().get(0).getInstances().get(0).getHomePageUrl();
        ResponseEntity<TokenValidatorResponse> response = restTemplate.exchange(url + "/api/auth/is-logged-in/" + token, HttpMethod.GET, null, TokenValidatorResponse.class);
        return Objects.requireNonNull(response.getBody()).getStatus();
    }
}

