package io.github.amithkoujalgi.demo.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.amithkoujalgi.demo.models.http.Order;
import io.github.amithkoujalgi.demo.models.http.PortfolioItem;
import io.github.amithkoujalgi.demo.models.http.PortfolioItemAverage;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TradeService {

    boolean placeOrder(Order order);

//    List<UserOrder> listOrdersOfUser(String userId);

    List<PortfolioItem> getPortfolioOfUser(String userId) throws JsonProcessingException;

    List<PortfolioItemAverage> getPortfolioWithInstrumentPriceAveragesOfUser(String userId) throws JsonProcessingException;

}