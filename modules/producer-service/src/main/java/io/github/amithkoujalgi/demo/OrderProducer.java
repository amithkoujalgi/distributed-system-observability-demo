package io.github.amithkoujalgi.demo;

import io.github.amithkoujalgi.demo.entities.User;
import io.github.amithkoujalgi.demo.models.http.Order;
import io.github.amithkoujalgi.demo.models.http.OrderType;
import io.github.amithkoujalgi.demo.repositories.InstrumentRepository;
import io.github.amithkoujalgi.demo.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
public class OrderProducer {
    private static final Logger log = LoggerFactory.getLogger(OrderProducer.class);
    private final KafkaTemplate<String, Object> kafkaProducer;

    @Value("${infrastructure.topics.orders-placed}")
    private String topicOrders;

    @Autowired
    UserRepository userRepository;

    @Autowired
    InstrumentRepository instrumentRepository;

    public OrderProducer(KafkaTemplate<String, Object> kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    public void start() {

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                User user = userRepository.findRandomUser();
                if (user == null) {
                    log.info("No users available.");
                    continue;
                }
                io.github.amithkoujalgi.demo.entities.Instrument i = instrumentRepository.findRandomInstrument();
                if (i == null) {
                    log.info("No instruments available.");
                    continue;
                }
                int qty = new Random().nextInt(91) + 10;
                Order o = new Order(i.getName(), new Date(), qty, user.getId().toString(), UUID.randomUUID().toString(), OrderType.BUY);
                log.info("Producing order: {}", o.getOrderId());
                CompletableFuture<SendResult<String, Object>> msg = kafkaProducer.send(topicOrders, o.getOrderId(), o);
            }
        }).start();
    }
}
