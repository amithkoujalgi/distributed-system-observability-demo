package io.github.amithkoujalgi.demo;

import io.github.amithkoujalgi.demo.producer.listeners.JobCompletionNotificationListener;
import io.github.amithkoujalgi.demo.producer.processors.FileItemProcessor;
import io.github.amithkoujalgi.demo.producer.readers.LocalItemReader;
import io.github.amithkoujalgi.demo.producer.writers.KafkaItemWriter;
import io.github.amithkoujalgi.demo.producer.writers.PrintingWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
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
import java.io.File;
import java.util.Arrays;
import java.util.stream.StreamSupport;

@Configuration
public class BatchConfiguration {
    @Value("${producer.reader-impl:local}")
    private String readerImpl;

    @Value("${producer.writer-impl:printer}")
    private String writerImpl;

    @Bean(value = "datasource-batch")
    @ConfigurationProperties(prefix = "spring.datasource-batch")
    public DataSource dataSource() {
        return new DriverManagerDataSource();
    }

    private static final Logger log = LoggerFactory.getLogger(BatchConfiguration.class);

    @Bean
    public ItemReader<File> reader() {
        // available types: sftp/local
        if (readerImpl.equalsIgnoreCase("sftp")) {
            log.info("Using reader implementation: sftp");
            return new LocalItemReader();
        } else if (readerImpl.equalsIgnoreCase("local")) {
            log.info("Using reader implementation: local");
            return new LocalItemReader();
        } else {
            log.info("Invalid reader implementation {}. Defaulting to local impl.", readerImpl);
            return new LocalItemReader();
        }
    }

    @Bean
    public FileItemProcessor processor() {
        return new FileItemProcessor();
    }

    @Bean
    public ItemWriter<File> writer() {
        // available types: kafka/printer
        if (writerImpl.equalsIgnoreCase("kafka")) {
            log.info("Using writer implementation: kafka");
            return new KafkaItemWriter();
        } else if (writerImpl.equalsIgnoreCase("printer")) {
            log.info("Using writer implementation: printer");
            return new PrintingWriter();
        } else {
            log.info("Invalid writer implementation {}. Defaulting to printer impl.", writerImpl);
            return new PrintingWriter();
        }
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
    public Job importFilesJob(JobRepository jobRepository, Step step1, JobCompletionNotificationListener listener) {
        return new JobBuilder("importFilesJob", jobRepository).listener(listener).start(step1).build();
    }

    @Bean
    public Step step(JobRepository jobRepository, DataSourceTransactionManager transactionManager, ItemReader<File> reader, FileItemProcessor processor, ItemWriter<File> writer) {
        return new StepBuilder("step1", jobRepository).<File, File>chunk(3, transactionManager).reader(reader).processor(processor).writer(writer).build();
    }
}


