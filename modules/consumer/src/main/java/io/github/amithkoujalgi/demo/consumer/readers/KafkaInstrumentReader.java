package io.github.amithkoujalgi.demo.consumer.readers;

import io.github.amithkoujalgi.demo.models.http.Instrument;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Iterator;

@Component
public class KafkaInstrumentReader implements ItemReader<Instrument> {
    private static final Logger log = LoggerFactory.getLogger(KafkaInstrumentReader.class);

    @Autowired
    KafkaConsumer<String, Object> kafkaConsumer;

    @Override
    public Instrument read() {
        ConsumerRecords<String, Object> records = kafkaConsumer.poll(Duration.ofMillis(100)); // adjust the poll duration as needed
        if (records.isEmpty()) {
            log.info("No records received from Kafka.");
            return null;
        }
        Iterator<ConsumerRecord<String, Object>> recordIterator = records.iterator();
        if (recordIterator.hasNext()) {
            ConsumerRecord<String, Object> record = recordIterator.next();
            log.info("Received message: " + record.value());
            return (Instrument) record.value();
        }
        return null;
    }
}
