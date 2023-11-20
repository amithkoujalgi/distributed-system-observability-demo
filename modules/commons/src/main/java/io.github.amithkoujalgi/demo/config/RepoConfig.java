package io.github.amithkoujalgi.demo.config;

import io.github.amithkoujalgi.demo.repositories.InstrumentRepository;
import io.github.amithkoujalgi.demo.repositories.TradeRepository;
import io.github.amithkoujalgi.demo.repositories.impl.InstrumentRepositoryImpl;
import io.github.amithkoujalgi.demo.repositories.impl.TradeRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

//TODO: Find a way to remove this and let Spring auto-find the repo impls
@Component
@Configuration
public class RepoConfig {
    @Bean
    public InstrumentRepository instrumentRepository() {
        return new InstrumentRepositoryImpl();
    }

    @Bean
    public TradeRepository tradeRepository() {
        return new TradeRepositoryImpl();
    }
}