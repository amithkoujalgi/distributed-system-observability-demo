package io.github.amithkoujalgi.demo.config;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.okhttp.LogbookInterceptor;

@Configuration
public class LogBookConfig {
    @Bean
    public OkHttpClient.Builder okHttpClientBuilder(Logbook logbook) {
        return new OkHttpClient.Builder()
            .addNetworkInterceptor(new LogbookInterceptor(logbook));
    }
}
