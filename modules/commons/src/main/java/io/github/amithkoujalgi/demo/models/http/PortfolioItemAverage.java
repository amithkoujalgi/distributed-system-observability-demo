package io.github.amithkoujalgi.demo.models.http;

public class PortfolioItemAverage {
    private String instrument;
    private int quantity;
    private double avgPrice;

    public PortfolioItemAverage() {
    }

    public PortfolioItemAverage(String instrument, int quantity, double avgPrice) {
        this.instrument = instrument;
        this.quantity = quantity;
        this.avgPrice = avgPrice;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(double avgPrice) {
        this.avgPrice = avgPrice;
    }
}