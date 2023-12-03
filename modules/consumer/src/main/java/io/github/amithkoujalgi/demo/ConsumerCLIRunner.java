package io.github.amithkoujalgi.demo;

import io.github.amithkoujalgi.demo.entities.PortfolioInstrument;
import io.github.amithkoujalgi.demo.entities.User;
import io.github.amithkoujalgi.demo.models.http.Instrument;
import io.github.amithkoujalgi.demo.models.http.Order;
import io.github.amithkoujalgi.demo.repositories.InstrumentRepository;
import io.github.amithkoujalgi.demo.repositories.PortfolioRepository;
import io.github.amithkoujalgi.demo.repositories.UserRepository;
import io.github.amithkoujalgi.demo.services.InstrumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

import java.math.BigDecimal;
import java.sql.Timestamp;

@SpringBootApplication(scanBasePackages = {"io.github.amithkoujalgi.demo"})
@EnableDiscoveryClient
@EnableKafka
public class ConsumerCLIRunner implements CommandLineRunner {
    @Autowired
    private InstrumentService instrumentService;

    @Autowired
    PortfolioRepository portfolioRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    InstrumentRepository instrumentRepository;

    private static final Logger log = LoggerFactory.getLogger(ConsumerCLIRunner.class);

    @KafkaListener(topics = "${infrastructure.topics.price-changes}", groupId = "consumer-group-stock-price-updates")
    public void listenPriceChanges(Instrument instrument) {
        log.info("Received price change: " + instrument.getName());
        try {
            if (instrumentRepository.findByName(instrument.getName()) == null) {
                io.github.amithkoujalgi.demo.entities.Instrument instrumentEntity = new io.github.amithkoujalgi.demo.entities.Instrument(instrument.getName());
                instrumentRepository.save(instrumentEntity);
            }
            instrumentService.addStockInstrument(instrument);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(topics = "${infrastructure.topics.orders-placed}", groupId = "consumer-group-stock-price-updates")
    public void listenOrdersPlaced(Order order) {
        log.info("Received order: " + order.getInstrument());
        try {
            User user = userRepository.findAllById(Long.parseLong(order.getUserId()));
            io.github.amithkoujalgi.demo.entities.Instrument instrument = instrumentRepository.findByName(order.getInstrument());
            if (user == null || instrument == null) {
                log.info("Invalid user ID or instrument ID. Ignoring...");
                return;
            }
            // get current price from redis
            double price = instrumentService.fetchStockInstrumentByName(instrument.getName()).getLastTradedPrice();
            PortfolioInstrument portfolioInstrument = new PortfolioInstrument(user, instrument, new BigDecimal(order.getQuantity()), BigDecimal.valueOf(price), new Timestamp(order.getTimestamp().getTime()));
            portfolioRepository.save(portfolioInstrument);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(ConsumerCLIRunner.class)
//                .web(WebApplicationType.NONE)
                .run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Starting consumer app...");
    }
}
