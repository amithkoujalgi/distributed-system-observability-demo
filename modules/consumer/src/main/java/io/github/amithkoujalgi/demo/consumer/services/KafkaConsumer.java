package io.github.amithkoujalgi.demo.consumer.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    @KafkaListener(topics = "your-topic-name", groupId = "your-consumer-group")
    public void listen(String message) {
        System.out.println("Received message: " + message);
        // Add your business logic here
    }
}