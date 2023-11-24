package io.github.amithkoujalgi.demo.models.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

public class Instrument {
    private String name;
    private Date timestamp;
    private Double lastTradedPrice;
    private String trend;

    public Instrument() {
    }

    public Instrument(String name, Date timestamp, Double lastTradedPrice, String trend) {
        this.name = name;
        this.timestamp = timestamp;
        this.lastTradedPrice = lastTradedPrice;
        this.trend = trend;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Double getLastTradedPrice() {
        return lastTradedPrice;
    }

    public void setLastTradedPrice(Double lastTradedPrice) {
        this.lastTradedPrice = lastTradedPrice;
    }

    public String getTrend() {
        return trend;
    }

    public void setTrend(String trend) {
        this.trend = trend;
    }

    public static Instrument buildFromMap(String name, Map<Object, Object> map) throws Exception {
        if (map.keySet().isEmpty()) {
            throw new IllegalArgumentException("Invalid instrument or instrument not found!");
        }
        Instrument newIndex = new Instrument();
        newIndex.setName(name);
        for (Map.Entry<Object, Object> e : map.entrySet()) {
            switch (e.getKey().toString()) {
                case "trend" -> newIndex.setTrend(e.getValue().toString());
                case "last_traded_price" -> newIndex.setLastTradedPrice(Double.parseDouble(e.getValue().toString()));
                case "timestamp" -> {
                    double unixTimestamp = Double.parseDouble(e.getValue().toString());
                    Instant instant = Instant.ofEpochSecond((long) unixTimestamp, (long) ((unixTimestamp - Math.floor(unixTimestamp)) * 1_000_000_000));
                    LocalDateTime localDateTime = instant.atOffset(ZoneOffset.UTC).toLocalDateTime();
                    Date date = Date.from(localDateTime.atZone(ZoneOffset.systemDefault()).toInstant());
                    newIndex.setTimestamp(date);
                }
                default -> {
                    // other fields, if any
                }
            }
        }
        return newIndex;
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

    private static String getRandomTrend() {
        List<String> trends = Arrays.asList("UP", "DOWN");
        return trends.get(new Random().nextInt(trends.size()));
    }

    private static String getRandomInstrument(List<String> instrumentNames) {
        return instrumentNames.get(new Random().nextInt(instrumentNames.size()));
    }

    public static Instrument random(List<String> instrumentNames) {
        if (instrumentNames == null || instrumentNames.isEmpty()) {
            instrumentNames = Arrays.asList("AAPL", "GOOGL", "ADANI", "TATA");
        }
        String name = getRandomInstrument(instrumentNames);
        Date timestamp = new Date();
        Double lastTradedPrice = 10.0 + Math.random() * (100.0 - 10.0);
        String trend = getRandomTrend();
        return new Instrument(name, timestamp, lastTradedPrice, trend);
    }

    public static Instrument fromJSON(String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString, Instrument.class);
    }
}
