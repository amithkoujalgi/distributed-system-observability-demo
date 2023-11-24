package io.github.amithkoujalgi.demo.config;

import com.netflix.discovery.EurekaClient;
import io.github.amithkoujalgi.demo.util.AuthTokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
class TokenValidatorConfiguration {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private EurekaClient discoveryClient;

    @Value("${infrastructure.services.auth.host-name}")
    private String authServiceAppName;

    @Bean
    AuthTokenValidator authTokenValidator() {
        return new AuthTokenValidator(restTemplate, discoveryClient, authServiceAppName);
    }
}