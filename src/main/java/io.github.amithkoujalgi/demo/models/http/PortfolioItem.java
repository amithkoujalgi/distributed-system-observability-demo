package io.gitub.amithkoujalgi.demo.models.http;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.util.Date;

public class PortfolioItem {
    private String instrument;
    private int quantity;
    private double price;
    private Date timestamp;

    public PortfolioItem() {
    }

    public PortfolioItem(String instrument, int quantity, double price, Date timestamp) {
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public static PortfolioItem fromJSONString(String portfolioItem) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new CustomSerializationModule());
        return objectMapper.readValue(portfolioItem, PortfolioItem.class);
    }
}

class CustomSerializationModule extends SimpleModule {
    public CustomSerializationModule() {
        addDeserializer(Date.class, new DateDeserializer());
    }
}

class DateDeserializer extends JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        double timestamp = p.getDoubleValue();
        return new Date((long) (timestamp * 1000)); // Convert to milliseconds
    }
}