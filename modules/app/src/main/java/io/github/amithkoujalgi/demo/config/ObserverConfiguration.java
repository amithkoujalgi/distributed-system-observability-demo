package io.github.amithkoujalgi.demo.config;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationPredicate;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.ObservationView;
import io.micrometer.observation.aop.ObservedAspect;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.observation.ServerRequestObservationContext;
import org.springframework.web.client.RestTemplate;

@Configuration(proxyBeanMethods = false)
class ObserverConfiguration {
    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
        return new ObservedAspect(observationRegistry);
    }

    private Observation.Context getRootObservationContext(Observation.Context current) {
        ObservationView parent = current.getParentObservation();
        if (parent == null) {
            return current;
        } else {
            return getRootObservationContext((Observation.Context) parent.getContextView());
        }
    }

    @Bean
    ObservationPredicate patternBasedRouteObservationDisablerPredicate() {
        return (name, context) -> {
            Observation.Context root = getRootObservationContext(context);
            if (root instanceof ServerRequestObservationContext serverContext) {
                String r = serverContext.getCarrier().getRequestURI();
                return !r.startsWith("/swagger-ui") && !r.startsWith("/v3/api-docs");
            }
            return true;
        };
    }
}