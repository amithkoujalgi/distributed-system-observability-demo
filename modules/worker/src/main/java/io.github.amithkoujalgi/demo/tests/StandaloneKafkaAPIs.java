package io.github.amithkoujalgi.demo.tests;

import io.github.amithkoujalgi.demo.models.http.Order;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.RecordDeserializationException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.time.Duration;
import java.util.*;

public class StandaloneKafkaAPIs {
    public static void main(String[] args) {
        KafkaConsumer<String, Object> kafkaConsumer = kafka();
        TopicPartition partition = new TopicPartition("stock-transactions", 0);
        List<TopicPartition> partitions = new ArrayList<>();
        partitions.add(partition);
        kafkaConsumer.assign(partitions);
        kafkaConsumer.seekToBeginning(Collections.singleton(partition));
        List<Order> ordersPlaced = new ArrayList<>();
        try {
            ConsumerRecords<String, Object> records = kafkaConsumer.poll(Duration.ofMillis(3000));
            for (ConsumerRecord<String, Object> record : records) {
                try {
                    String key = record.key();
                    Order value = (Order) record.value();
                    ordersPlaced.add(value);
                } catch (RecordDeserializationException e) {
                    System.err.println("Error deserializing record: " + e.getMessage());
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static KafkaConsumer<String, Object> kafka() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "ConsumerGroup1");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "io.github.amithkoujalgi.demo.apimodels");
        return new KafkaConsumer<>(props);
    }
}
