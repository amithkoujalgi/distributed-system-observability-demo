package io.github.amithkoujalgi.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;

@SpringBootApplication(scanBasePackages = "io.github.amithkoujalgi.demo")

public class BatchProcessingApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(BatchProcessingApplication.class);
        System.exit(SpringApplication.exit(application.run(args)));
    }
}
