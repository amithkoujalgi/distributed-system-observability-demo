package io.github.amithkoujalgi.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class FileProcessingBatchConfiguration {

    @Value("${producer.source_directory}")
    private String sourceDirectory;



    @Bean
    public ResourcelessTransactionManager dataSourceTransactionManager() {
        return new ResourcelessTransactionManager();
    }

    @Bean
    public ItemReader<File> reader() throws IOException {
        List<File> files = Files.walk(Paths.get(sourceDirectory))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());
        return new IteratorItemReader<>(files);
    }

    @Bean
    public FileItemProcessor processor() {
        return new FileItemProcessor();
    }

    @Bean
    public ItemWriter<File> writer() {
        return new FileNamePrintingItemWriter();
    }

    @Bean
    public Job importFilesJob(JobRepository jobRepository, Step step1, JobCompletionNotificationListener listener) {
        return new JobBuilder("importFilesJob", jobRepository)
                .listener(listener)
                .start(step1)
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, ResourcelessTransactionManager transactionManager,
                      ItemReader<File> reader, FileItemProcessor processor, ItemWriter<File> writer) {
        return new StepBuilder("step1", jobRepository)
                .<File, File>chunk(3, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}


