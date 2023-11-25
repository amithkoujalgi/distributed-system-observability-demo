package io.github.amithkoujalgi.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.amithkoujalgi.demo.entities.PortfolioInstrument;
import io.github.amithkoujalgi.demo.entities.User;
import io.github.amithkoujalgi.demo.models.http.Instrument;
import io.github.amithkoujalgi.demo.models.http.Order;
import io.github.amithkoujalgi.demo.repositories.InstrumentRepository;
import io.github.amithkoujalgi.demo.repositories.PortfolioRepository;
import io.github.amithkoujalgi.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;

import java.math.BigDecimal;
import java.sql.Timestamp;

@SuppressWarnings("rawtypes")
@SpringBootApplication
public class ConsumerCLIRunner implements CommandLineRunner {
    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${infrastructure.redis.keys.stocks}")
    private String redisHashKeyPrefixStocks;

    @Autowired
    PortfolioRepository portfolioRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    InstrumentRepository instrumentRepository;

    @SuppressWarnings("unchecked")
    @KafkaListener(topics = "${infrastructure.topics.price-changes}", groupId = "consumer-group-stock-price-updates")
    public void listenPriceChanges(String message) {
        System.out.println("Received price change: " + message);
        try {
            Instrument instrument = Instrument.fromJSON(message);
            if (instrumentRepository.findByName(instrument.getName()) == null) {
                io.github.amithkoujalgi.demo.entities.Instrument instrumentEntity = new io.github.amithkoujalgi.demo.entities.Instrument(instrument.getName());
                instrumentRepository.save(instrumentEntity);
            }
            redisTemplate.opsForHash().put(redisHashKeyPrefixStocks + ":" + instrument.getName(), "price", instrument.toJSON());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @KafkaListener(topics = "${infrastructure.topics.orders-placed}", groupId = "consumer-group-stock-price-updates")
    public void listenOrdersPlaced(String message) {
        System.out.println("Received order: " + message);
        try {
            Order order = Order.fromJSON(message);
            User user = userRepository.findAllById(Long.parseLong(order.getUserId()));
            io.github.amithkoujalgi.demo.entities.Instrument instrument = instrumentRepository.findByName(order.getInstrument());
            if (user == null || instrument == null) {
                System.out.println("Invalid user ID or instrument ID. Ignoring...");
                return;
            }
            PortfolioInstrument portfolioInstrument = new PortfolioInstrument(user, instrument, new BigDecimal(order.getQuantity()), BigDecimal.valueOf(order.getPrice()), new Timestamp(order.getTimestamp().getTime()));
            portfolioRepository.save(portfolioInstrument);
        } catch (Exception e) {
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
