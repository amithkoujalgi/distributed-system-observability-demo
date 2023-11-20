package io.gitub.amithkoujalgi.demo.config;

import io.gitub.amithkoujalgi.demo.services.PortfolioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.stereotype.Component;

@Component
@Configuration
@EnableRedisRepositories
public class ServicesConfig {

    @Bean
    public PortfolioService portfolioService() {
        return new PortfolioService();
    }

}