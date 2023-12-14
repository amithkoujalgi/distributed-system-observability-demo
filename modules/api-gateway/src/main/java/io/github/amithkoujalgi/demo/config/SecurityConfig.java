package io.github.amithkoujalgi.demo.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    //    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
//        return serverHttpSecurity.csrf(ServerHttpSecurity.CsrfSpec::disable)
//                .authorizeExchange(exchange -> exchange.pathMatchers("/eureka/**", "/actuator/**", "/")
//                        .permitAll()
//                        .anyExchange().authenticated()
//                ).oauth2ResourceServer((oauth) -> oauth
//                        .jwt(Customizer.withDefaults()))
//                .build();
//    }
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
        return serverHttpSecurity.csrf(ServerHttpSecurity.CsrfSpec::disable).authorizeExchange(exchange -> exchange.pathMatchers("/api/**").authenticated().pathMatchers("/resources/**").permitAll().anyExchange().permitAll()).oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults())).addFilterAfter(new PostAuthActionFilter(), SecurityWebFiltersOrder.AUTHENTICATION).build();
    }
}

class PostAuthActionFilter implements WebFilter {
    @NotNull
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if (exchange.getRequest().getPath().toString().startsWith("/api/")) {
            String authToken = exchange.getRequest().getHeaders().get("authorization").get(0);
            return chain.filter(exchange.mutate().request(exchange.getRequest().mutate().header("access-token", UUID.randomUUID().toString()).build()).build());
        } else {
            return chain.filter(exchange.mutate().request(exchange.getRequest().mutate().build()).build());
        }
    }
}