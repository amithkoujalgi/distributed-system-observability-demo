package io.github.amithkoujalgi.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "io.github.amithkoujalgi.demo")
public class BatchProcessingApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(BatchProcessingApplication.class);
        System.exit(SpringApplication.exit(application.run(args)));
    }
}
