package io.github.amithkoujalgi.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileNamePrintingItemWriter implements ItemWriter<File> {
    private static final Logger log = LoggerFactory.getLogger(FileNamePrintingItemWriter.class);
    @Value("${producer.target_directory}")
    private String targetDirectory;

    @Override
    public void write(Chunk<? extends File> chunk) throws Exception {
        Path directoryPath = Path.of(targetDirectory);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }
        Path targetFile = Path.of(targetDirectory, "batch-results.txt");
        if (!Files.exists(targetFile)) {
            Files.createFile(targetFile);
        }
        log.info("Writing file names to: {}", targetFile.toAbsolutePath());
        try {
            for (File file : chunk.getItems()) {
                String fileName = file.getName();
                Files.write(targetFile, fileName.getBytes(), StandardOpenOption.APPEND);
                Files.write(targetFile, System.lineSeparator().getBytes(), StandardOpenOption.APPEND);
            }
        } catch (Exception e) {
            log.error("Error writing file names to results file", e);
            throw e;
        }
    }

}
