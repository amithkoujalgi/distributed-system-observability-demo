package io.github.amithkoujalgi.demo.producer.writers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.concurrent.CompletableFuture;

@Component
public class KafkaItemWriter implements ItemWriter<File> {
    private static final Logger log = LoggerFactory.getLogger(KafkaItemWriter.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaProducer;

    @Value("${infrastructure.topic}")
    private String topic;

    @Override
    public void write(Chunk<? extends File> chunk) {
        log.info("Writing file names to Kafka...");
        for (File file : chunk.getItems()) {
            String parent = file.getParent();
            CompletableFuture<SendResult<String, Object>> msg = kafkaProducer.send(topic, parent, new KafkaFileRecord(file.getAbsolutePath()));
        }
    }
}

class KafkaFileRecord {
    private String filePath;

    public KafkaFileRecord(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}