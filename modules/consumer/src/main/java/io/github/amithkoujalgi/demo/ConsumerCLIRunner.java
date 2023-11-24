package io.github.amithkoujalgi.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.amithkoujalgi.demo.models.http.Instrument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;

@SuppressWarnings("rawtypes")
@SpringBootApplication
public class ConsumerCLIRunner implements CommandLineRunner {
    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${infrastructure.redis.keys.stocks}")
    private String redisHashKeyPrefixStocks;

    @SuppressWarnings("unchecked")
    @KafkaListener(topics = "${infrastructure.topics.price-changes}", groupId = "consumer-group-stock-price-updates")
    public void listen(String message) {
        System.out.println("Received message: " + message);
        try {
            Instrument instrument = Instrument.fromJSON(message);
            redisTemplate.opsForHash().put(redisHashKeyPrefixStocks + ":" + instrument.getName(), "price", instrument.toJSON());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumerCLIRunner.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Starting consumer app...");
    }
}
