package io.gitub.amithkoujalgi.demo.models.http;

import java.util.Date;

public class UserOrder {
    private String instrument;
    private Double price;
    private Integer quantity;
    private String orderId;
    private Date timestamp;
    private OrderType type;

    public UserOrder() {
    }

    public UserOrder(String instrument, Double price, Integer quantity, String orderId, Date timestamp, OrderType type) {
        this.instrument = instrument;
        this.price = price;
        this.quantity = quantity;
        this.orderId = orderId;
        this.timestamp = timestamp;
        this.type = type;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }
}
