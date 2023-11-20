package io.github.amithkoujalgi.demo.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.io.File;

public class FileItemProcessor implements ItemProcessor<File, File> {

    private static final Logger log = LoggerFactory.getLogger(FileItemProcessor.class);

    @Override
    public File process(final File fl) {
        log.info("Processing file: {}", fl.getAbsolutePath());
        return fl;
    }
}
