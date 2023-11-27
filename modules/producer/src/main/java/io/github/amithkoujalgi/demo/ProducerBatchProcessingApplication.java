package io.github.amithkoujalgi.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
@SpringBootApplication(scanBasePackages = {"io.github.amithkoujalgi.demo.config", "io.github.amithkoujalgi.demo.controllers", "io.github.amithkoujalgi.demo.repositories", "io.github.amithkoujalgi.demo.initializers"})
@EnableDiscoveryClient
public class ProducerBatchProcessingApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ProducerBatchProcessingApplication.class);
        System.exit(SpringApplication.exit(application.run(args)));
    }
}
