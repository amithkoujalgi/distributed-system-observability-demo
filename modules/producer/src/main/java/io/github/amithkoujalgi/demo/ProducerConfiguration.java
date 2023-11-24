package io.github.amithkoujalgi.demo;

import io.github.amithkoujalgi.demo.models.http.Instrument;
import io.github.amithkoujalgi.demo.producer.listeners.JobCompletionNotificationListener;
import io.github.amithkoujalgi.demo.producer.processors.InstrumentProcessor;
import io.github.amithkoujalgi.demo.producer.readers.DummyInstrumentReader;
import io.github.amithkoujalgi.demo.producer.writers.KafkaInstrumentWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.stream.StreamSupport;

@Configuration
public class ProducerConfiguration {
    @Bean(value = "datasource-batch")
    @ConfigurationProperties(prefix = "spring.datasource-batch")
    public DataSource dataSource() {
        return new DriverManagerDataSource();
    }

    private static final Logger log = LoggerFactory.getLogger(ProducerConfiguration.class);

    @Bean
    public ItemReader<Instrument> reader() {
        return new DummyInstrumentReader();
    }

    @Bean
    public InstrumentProcessor processor() {
        return new InstrumentProcessor();
    }

    @Bean
    public ItemWriter<Instrument> writer() {
        return new KafkaInstrumentWriter();
    }

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        final Environment env = event.getApplicationContext().getEnvironment();
        log.info("====== Environment and configuration ======");
        log.info("Active profiles: {}", Arrays.toString(env.getActiveProfiles()));
        final MutablePropertySources sources = ((AbstractEnvironment) env).getPropertySources();
        StreamSupport.stream(sources.spliterator(), false).filter(ps -> ps instanceof EnumerablePropertySource).map(ps -> ((EnumerablePropertySource<?>) ps).getPropertyNames()).flatMap(Arrays::stream).distinct().filter(prop -> !(prop.contains("credentials") || prop.contains("password"))).forEach(prop -> log.info("{}: {}", prop, env.getProperty(prop)));
        log.info("===========================================");
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public Job generateInstrumentsJob(JobRepository jobRepository, Step step1, JobCompletionNotificationListener listener) {
        return new JobBuilder("generateInstrumentsJob", jobRepository).listener(listener).start(step1).build();
    }

    @Bean
    public Step generateInstrumentsStep(JobRepository jobRepository, DataSourceTransactionManager transactionManager, ItemReader<Instrument> reader, InstrumentProcessor processor, ItemWriter<Instrument> writer) {
        return new StepBuilder("step1", jobRepository).<Instrument, Instrument>chunk(3, transactionManager).reader(reader).processor(processor).writer(writer).build();
    }
}


