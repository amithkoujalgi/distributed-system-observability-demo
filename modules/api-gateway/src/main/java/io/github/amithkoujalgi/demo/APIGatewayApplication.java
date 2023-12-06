package io.github.amithkoujalgi.demo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.nio.file.Paths;

@SpringBootApplication(scanBasePackages = {"io.github.amithkoujalgi.demo"})
@OpenAPIDefinition(info = @Info(title = "API Gateway"))
@EnableDiscoveryClient
public class APIGatewayApplication {
    @Bean
    public RouterFunction<ServerResponse> homeRouter() {
//        return RouterFunctions.resources("/**", new ClassPathResource("static/"));
        return RouterFunctions.resources("/**", new FileSystemResource(Paths.get("/Users/amithkoujalgi/Documents/GitHub/distributed-system-observability-demo/modules/api-gateway/src/main/resources/static")));
    }

    public static void main(String[] args) {
        SpringApplication.run(APIGatewayApplication.class, args);
    }
}