package io.gitub.amithkoujalgi.demo.models.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

public class Order {
    private String instrument;
    private Date timestamp;
    private Double price;
    private Integer quantity;
    private String userId;

    private String orderId;

    private OrderType type;

    public Order() {
    }

    @Override
    public String toString() {
        return toJSON();
    }

    public String toJSON() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Order(String instrument, Date timestamp, Double price, Integer quantity, String userId, String orderId, OrderType type) {
        this.instrument = instrument;
        this.timestamp = timestamp;
        this.price = price;
        this.quantity = quantity;
        this.userId = userId;
        this.orderId = orderId;
        this.type = type;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }
}
