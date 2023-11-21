package io.github.amithkoujalgi.demo.producer.readers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;

public class LocalItemReader implements ItemReader<File> {
    private static final Logger log = LoggerFactory.getLogger(LocalItemReader.class);
    @Value("${producer.source-directory}")
    private String sourceDirectory;
    private File[] files;
    private int currentIndex = 0;

    private File[] initializeFiles() {
        File directory = new File(sourceDirectory);
        return directory.listFiles();
    }

    @Override
    public File read() {
        if (files == null) {
            files = initializeFiles();
        }
        if (currentIndex < files.length) {
            File currentFile = files[currentIndex];
            currentIndex++;
            log.info("Found file: {}", currentFile.getAbsolutePath());
            return currentFile;
        } else {
            return null;
        }
    }
}
