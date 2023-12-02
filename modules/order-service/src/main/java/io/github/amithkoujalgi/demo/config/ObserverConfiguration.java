package io.github.amithkoujalgi.demo.config;

import io.micrometer.common.KeyValue;
import io.micrometer.observation.*;
import io.micrometer.observation.aop.ObservedAspect;
import net.ttddyy.observation.tracing.DataSourceBaseContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
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
                return !r.startsWith("/swagger-ui") && !r.startsWith("/v3/api-docs") && !r.startsWith("/eureka") && !r.startsWith("/actuator");
            }
            return true;
        };
    }

    @Bean
    ObservationFilter tempoErrorFilter() {
        // TODO: remove this once Tempo is fixed: https://github.com/grafana/tempo/issues/1916
        return context -> {
            if (context.getError() != null) {
                context.addHighCardinalityKeyValue(KeyValue.of("error", "true"));
                context.addHighCardinalityKeyValue(KeyValue.of("errorMessage", context.getError().getMessage()));
            }
            return context;
        };
    }
    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(DataSourceBaseContext.class)
    static class DataSourceActuatorConfig {
        @Bean
        ObservationFilter tempoServiceGraphFilter() {
            // TODO: remove this once Tempo is fixed: https://github.com/grafana/tempo/issues/2212
            return context -> {
                if (context instanceof DataSourceBaseContext dataSourceContext) {
                    context.addHighCardinalityKeyValue(KeyValue.of("db.name", dataSourceContext.getRemoteServiceName()));
                }
                return context;
            };
        }
    }
}