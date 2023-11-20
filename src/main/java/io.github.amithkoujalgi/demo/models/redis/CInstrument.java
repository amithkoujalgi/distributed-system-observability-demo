package io.gitub.amithkoujalgi.demo.models.redis;

public class CInstrument {
    private String trend;
    private String last_traded_price;
    private String timestamp;

    public String getTrend() {
        return trend;
    }

    public void setTrend(String trend) {
        this.trend = trend;
    }

    public String getLast_traded_price() {
        return last_traded_price;
    }

    public void setLast_traded_price(String last_traded_price) {
        this.last_traded_price = last_traded_price;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
