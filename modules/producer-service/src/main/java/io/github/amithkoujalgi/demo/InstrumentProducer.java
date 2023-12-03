package io.github.amithkoujalgi.demo;

import io.github.amithkoujalgi.demo.models.http.Instrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@Component
public class InstrumentProducer {
    private static final Logger log = LoggerFactory.getLogger(InstrumentProducer.class);
    private final KafkaTemplate<String, Object> kafkaProducer;

    @Value("${producer.stock-instruments}")
    private String instrumentNames;

    @Value("${infrastructure.topics.price-changes}")
    private String topicPriceUpdates;

    public InstrumentProducer(KafkaTemplate<String, Object> kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    public void start() {
        new Thread(() -> {
            while (true) {
                Instrument instrument = Instrument.random(Arrays.asList(instrumentNames.split(",")));
                log.info("Producing instrument: {}", instrument.getName());
                CompletableFuture<SendResult<String, Object>> msg = kafkaProducer.send(topicPriceUpdates, instrument.getName(), instrument);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
