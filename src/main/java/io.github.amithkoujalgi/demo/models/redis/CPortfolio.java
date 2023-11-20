package io.gitub.amithkoujalgi.demo.models.redis;

public class CPortfolio {
    private String instrument;
    private String quantity;
    private String price;
    private String timestamp;

    public CPortfolio(String instrument, String quantity, String price, String timestamp) {
        this.instrument = instrument;
        this.quantity = quantity;
        this.price = price;
        this.timestamp = timestamp;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
