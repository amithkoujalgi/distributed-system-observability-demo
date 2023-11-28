package io.github.amithkoujalgi.demo.util;

import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class AuthTokenValidator {
    private final RestTemplate restTemplate;
    private final EurekaClient discoveryClient;
    private final String authServiceAppName;

    public AuthTokenValidator(RestTemplate restTemplate, EurekaClient discoveryClient, String authServiceAppName) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
        this.authServiceAppName = authServiceAppName;
    }

    public boolean validateAuthToken(String token) throws Exception {
        List<Application> x = discoveryClient.getApplications().getRegisteredApplications();
        Optional<Application> authApp = x.stream()
                .filter(app -> authServiceAppName.equals(app.getName()))
                .findFirst();
        if (authApp.isPresent()) {
            Application app = authApp.get();
            String url = app.getInstances().get(0).getHomePageUrl();
            System.out.println("+++URL: "+url);
            ResponseEntity<TokenValidatorResponse> response = restTemplate.exchange(url + "/api/auth/is-logged-in/" + token, HttpMethod.GET, null, TokenValidatorResponse.class);
            return Objects.requireNonNull(response.getBody()).getStatus();
        } else {
            throw new Exception("Auth Service not found!");
        }
    }
}

