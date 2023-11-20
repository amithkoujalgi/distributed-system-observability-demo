package io.github.amithkoujalgi.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import java.io.File;

public class FileNamePrintingItemWriter implements ItemWriter<File> {
    private static final Logger log = LoggerFactory.getLogger(FileNamePrintingItemWriter.class);

    @Override
    public void write(Chunk<? extends File> chunk) {
        for (File file : chunk.getItems()) {
            log.info("Writing file: {}", file.getName());
        }
    }
}
