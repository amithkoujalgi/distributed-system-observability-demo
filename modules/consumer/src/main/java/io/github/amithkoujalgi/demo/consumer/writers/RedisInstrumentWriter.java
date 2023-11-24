package io.github.amithkoujalgi.demo.consumer.writers;

import io.github.amithkoujalgi.demo.models.http.Instrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RedisInstrumentWriter implements ItemWriter<Instrument> {
    private static final Logger log = LoggerFactory.getLogger(RedisInstrumentWriter.class);


    @Value("${infrastructure.topics.price-changes}")
    private String topicPriceUpdates;

    @Override
    public void write(Chunk<? extends Instrument> chunk) {
        log.info("Writing instruments to Redis...");
        for (Instrument instrument : chunk.getItems()) {

        }
    }
}