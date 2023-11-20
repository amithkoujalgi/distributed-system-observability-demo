package io.github.amithkoujalgi.demo.producer;

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
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
public class KafkaItemWriter implements ItemWriter<File> {
    private static final Logger log = LoggerFactory.getLogger(KafkaItemWriter.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaProducer;

    @Value("${infrastructure.topic}")
    private String topic;

    @Override
    public void write(Chunk<? extends File> chunk) throws Exception {
        log.info("Writing file names to Kafka...");
        for (File file : chunk.getItems()) {
            String fileName = file.getName();
            CompletableFuture<SendResult<String, Object>> msg = kafkaProducer.send(topic, UUID.randomUUID().toString(), new KafkaFileRecord(file.getAbsolutePath()));
            // if we need to wait for ack
            //  while (!msg.isDone()) {
            //       System.out.println("waiting for ack");
            //  }
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