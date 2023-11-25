package io.github.amithkoujalgi.demo.config;

import io.github.amithkoujalgi.demo.repositories.InstrumentsAPI;
import io.github.amithkoujalgi.demo.repositories.TradeAPI;
import io.github.amithkoujalgi.demo.repositories.impl.InstrumentsAPIImpl;
import io.github.amithkoujalgi.demo.repositories.impl.TradeAPIImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

//TODO: Find a way to remove this and let Spring auto-find the repo impls
@Component
@Configuration
public class RepoConfig {
    @Bean
    public InstrumentsAPI instrumentsAPI() {
        return new InstrumentsAPIImpl();
    }

    @Bean
    public TradeAPI tradeRepository() {
        return new TradeAPIImpl();
    }
}