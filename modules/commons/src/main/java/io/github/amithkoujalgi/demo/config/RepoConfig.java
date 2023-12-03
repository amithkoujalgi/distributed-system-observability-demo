package io.github.amithkoujalgi.demo.config;

import io.github.amithkoujalgi.demo.services.InstrumentService;
import io.github.amithkoujalgi.demo.services.TradeService;
import io.github.amithkoujalgi.demo.services.impl.InstrumentServiceImpl;
import io.github.amithkoujalgi.demo.services.impl.TradeServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

//TODO: Find a way to remove this and let Spring auto-find the repo impls
@Component
@Configuration
public class RepoConfig {
    @Bean
    public InstrumentService instrumentsAPI() {
        return new InstrumentServiceImpl();
    }

    @Bean
    public TradeService tradeRepository() {
        return new TradeServiceImpl();
    }
}