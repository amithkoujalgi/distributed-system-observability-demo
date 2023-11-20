package io.github.amithkoujalgi.demo.repositories;


import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.amithkoujalgi.demo.models.http.Order;
import io.github.amithkoujalgi.demo.models.http.PortfolioItem;
import io.github.amithkoujalgi.demo.models.http.PortfolioItemAverage;
import io.github.amithkoujalgi.demo.models.http.UserOrder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeRepository {

    boolean placeOrder(Order order);

    List<UserOrder> listOrdersOfUser(String userId);


    List<PortfolioItem> getPortfolioOfUser(String userId) throws JsonProcessingException;

    List<PortfolioItemAverage> getPortfolioWithInstrumentPriceAveragesOfUser(String userId) throws JsonProcessingException;

}