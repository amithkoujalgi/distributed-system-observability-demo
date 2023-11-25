package io.github.amithkoujalgi.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "io.github.amithkoujalgi.demo.repositories")
public class JpaConfig {
}