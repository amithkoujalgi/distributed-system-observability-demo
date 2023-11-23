package io.github.amithkoujalgi.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "io.github.amithkoujalgi.demo")
public class ProducerBatchProcessingApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ProducerBatchProcessingApplication.class);
        System.exit(SpringApplication.exit(application.run(args)));
    }
}
