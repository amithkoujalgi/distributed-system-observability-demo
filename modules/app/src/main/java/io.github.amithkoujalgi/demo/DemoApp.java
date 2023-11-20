package io.github.amithkoujalgi.demo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"io.github.amithkoujalgi.demo.config", "io.github.amithkoujalgi.demo.controllers", "io.github.amithkoujalgi.demo.repositories", "io.github.amithkoujalgi.demo.initializers"})
@OpenAPIDefinition(info = @Info(title = "Demo Server"))
public class DemoApp {

    public static void main(String[] args) {
        SpringApplication.run(io.github.amithkoujalgi.demo.DemoApp.class, args);
    }

}