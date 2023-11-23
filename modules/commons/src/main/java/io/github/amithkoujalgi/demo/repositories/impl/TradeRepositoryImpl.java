package io.github.amithkoujalgi.demo.repositories.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.amithkoujalgi.demo.models.http.Order;
import io.github.amithkoujalgi.demo.models.http.PortfolioItem;
import io.github.amithkoujalgi.demo.models.http.PortfolioItemAverage;
import io.github.amithkoujalgi.demo.models.http.UserOrder;
import io.github.amithkoujalgi.demo.repositories.TradeRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.RecordDeserializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class TradeRepositoryImpl implements TradeRepository {

    @Autowired
    private KafkaTemplate<String, Object> kafkaProducer;

    @Autowired
    private KafkaConsumer<String, Object> kafkaConsumer;

    @Value("${infrastructure.topics.orders-placed}")
    private String ordersTopic;

    @Override
    public boolean placeOrder(Order order) {
        CompletableFuture<SendResult<String, Object>> msg = kafkaProducer.send(ordersTopic, order.getUserId(), order);
        // if we need to wait for ack
        //  while (!msg.isDone()) {
        //       System.out.println("waiting for ack");
        //  }
        return true;
    }

    @Override
    public List<UserOrder> listOrdersOfUser(String userId) {
        TopicPartition partition = new TopicPartition(ordersTopic, 0);
        List<TopicPartition> partitions = new ArrayList<>();
        partitions.add(partition);
        kafkaConsumer.assign(partitions);
        kafkaConsumer.seekToBeginning(Collections.singleton(partition));

        List<UserOrder> ordersPlaced = new ArrayList<>();
        try {
            ConsumerRecords<String, Object> records = kafkaConsumer.poll(Duration.ofMillis(3000));
            for (ConsumerRecord<String, Object> record : records) {
                try {
                    String key = record.key();
                    if (key.equals(userId)) {
                        Order value = (Order) record.value();
                        ordersPlaced.add(new UserOrder(value.getInstrument(), value.getPrice(), value.getQuantity(), value.getOrderId(), value.getTimestamp(), value.getType()));
                    }
                } catch (RecordDeserializationException e) {
                    System.err.println("Error deserializing record: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ordersPlaced;
    }

    @Override
    public List<PortfolioItem> getPortfolioOfUser(String userId) throws JsonProcessingException {
        List<PortfolioItem> portfolioItemList = new ArrayList<>();
        return portfolioItemList;
    }

    @Override
    public List<PortfolioItemAverage> getPortfolioWithInstrumentPriceAveragesOfUser(String userId) throws JsonProcessingException {
        List<PortfolioItem> portfolioItemList = getPortfolioOfUser(userId);
        Map<String, PortfolioItemAverage> instrumentPriceAverages = new HashMap<>();

        for (PortfolioItem p : portfolioItemList) {
            String instrument = p.getInstrument();
            PortfolioItemAverage average = instrumentPriceAverages.getOrDefault(instrument, new PortfolioItemAverage(instrument, 0, 0.0));

            int qty = average.getQuantity() + p.getQuantity();
            double price = average.getAvgPrice() + p.getPrice();
            instrumentPriceAverages.put(instrument, new PortfolioItemAverage(instrument, qty, price));
        }
        // TODO: Fix the averaging logic!
        return instrumentPriceAverages.values().stream().peek(item -> item.setAvgPrice(item.getAvgPrice() / item.getQuantity())).collect(Collectors.toList());
    }
}
