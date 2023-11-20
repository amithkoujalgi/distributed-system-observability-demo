package io.gitub.amithkoujalgi.demo.models.http;

public class PlaceOrder {
    private String instrument;
    private Double price;
    private Integer quantity;
    private String userId;

    private OrderType type;

    public PlaceOrder() {
    }

    public PlaceOrder(String instrument, Double price, Integer quantity, String userId, OrderType type) {
        this.instrument = instrument;
        this.price = price;
        this.quantity = quantity;
        this.userId = userId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }
}
