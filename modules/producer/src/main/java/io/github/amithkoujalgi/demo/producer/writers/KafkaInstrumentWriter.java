package io.github.amithkoujalgi.demo.producer.writers;

import io.github.amithkoujalgi.demo.models.http.Instrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class KafkaInstrumentWriter implements ItemWriter<Instrument> {
    private static final Logger log = LoggerFactory.getLogger(KafkaItemWriter.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaProducer;

    @Value("${infrastructure.topic}")
    private String topic;

    @Override
    public void write(Chunk<? extends Instrument> chunk) {
        log.info("Writing instruments to Kafka...");
        for (Instrument instrument : chunk.getItems()) {
            CompletableFuture<SendResult<String, Object>> msg = kafkaProducer.send(topic, instrument.getName(), instrument);
        }
    }
}