package io.github.amithkoujalgi.demo.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import java.io.File;

public class KafkaItemWriter implements ItemWriter<File> {
    private static final Logger log = LoggerFactory.getLogger(KafkaItemWriter.class);

    @Override
    public void write(Chunk<? extends File> chunk) throws Exception {
        log.info("Writing file names to Kafka");
        for (File file : chunk.getItems()) {
            String fileName = file.getName();
        }
    }

}
