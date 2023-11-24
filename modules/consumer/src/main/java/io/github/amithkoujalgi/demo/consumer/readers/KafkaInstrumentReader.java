package io.github.amithkoujalgi.demo.consumer.readers;

import io.github.amithkoujalgi.demo.models.http.Instrument;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Component
public class KafkaInstrumentReader implements ItemReader<Instrument> {
    private static final Logger log = LoggerFactory.getLogger(KafkaInstrumentReader.class);

    @Value("${infrastructure.topics.price-changes}")
    private String topicPriceUpdates;

    @Autowired
    KafkaConsumer<String, Object> kafkaConsumer;

    @Override
    public Instrument read() {
        TopicPartition partition = new TopicPartition(topicPriceUpdates, 0);
        List<TopicPartition> partitions = new ArrayList<>();
        partitions.add(partition);
        kafkaConsumer.assign(partitions);
//        kafkaConsumer.seekToBeginning(Collections.singleton(partition));
        ConsumerRecords<String, Object> records = kafkaConsumer.poll(Duration.ofMillis(1000));
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
