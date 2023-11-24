package io.github.amithkoujalgi.demo.repositories.impl;

import io.github.amithkoujalgi.demo.models.http.Instrument;
import io.github.amithkoujalgi.demo.repositories.InstrumentRepository;
import io.micrometer.observation.annotation.Observed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unchecked")
public class InstrumentRepositoryImpl implements InstrumentRepository {
    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${infrastructure.redis.keys.stocks}")
    private String stocksKeyname;

    @Value("${infrastructure.redis.keys.indices}")
    private String indicesKeyname;

    @Observed(name = "InstrumentRepository.fetchAllStockInstruments",
            contextualName = "fetchAllStockInstruments",
            lowCardinalityKeyValues = {"description", "Fetches all stock instruments from Redis", "classification", "stock"}
    )
    @Override
    public List<Instrument> fetchAllStockInstruments() throws Exception {
        Set<String> stockKeys = redisTemplate.keys(stocksKeyname + "*");
        List<Instrument> instrumentList = new ArrayList<>();
        if (stockKeys != null) {
            for (String key : stockKeys) {
                Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
                Instrument i = Instrument.fromJSON(entries.get("price").toString());
                instrumentList.add(i);
            }
        }
        return instrumentList;
    }

    @Observed(name = "InstrumentRepository.fetchStockInstrumentByName",
            contextualName = "fetchStockInstrumentByName",
            lowCardinalityKeyValues = {"description", "Fetches stock instrument by name from Redis", "classification", "stock"}
    )
    @Override
    public Instrument fetchStockInstrumentByName(String symbol) throws Exception {
        Map<Object, Object> map = redisTemplate.opsForHash().entries(stocksKeyname + ":" + symbol);
        return Instrument.fromJSON(map.get("price").toString());
    }

    @Observed(name = "InstrumentRepository.findStockInstrumentsByKeyword",
            contextualName = "findStockInstrumentsByKeyword",
            lowCardinalityKeyValues = {"description", "Fetches stock instrument by keyword from Redis", "classification", "stock"}
    )
    @Override
    public List<Instrument> findStockInstrumentsByKeyword(String keyword) throws Exception {
        List<Instrument> matchedInstruments = new ArrayList<>();
        for (Instrument i : fetchAllStockInstruments()) {
            if (i.getName().toLowerCase().contains(keyword.toLowerCase())) {
                matchedInstruments.add(i);
            }
        }
        return matchedInstruments;
    }

    @Observed(name = "InstrumentRepository.fetchAllIndexInstruments",
            contextualName = "fetchAllIndexInstruments",
            lowCardinalityKeyValues = {"description", "Fetches index instrument by keyword from Redis", "classification", "index"}
    )
    @Override
    public List<Instrument> fetchAllIndexInstruments() throws Exception {
        Set<String> stockKeys = redisTemplate.keys(indicesKeyname + "*");
        List<Instrument> instrumentList = new ArrayList<>();
        if (stockKeys != null) {
            for (String key : stockKeys) {
                Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
                instrumentList.add(Instrument.fromJSON(Instrument.fromJSON(entries.get("price").toString()).toString()));
            }
        }
        return instrumentList;
    }

    @Observed(name = "InstrumentRepository.fetchIndexInstrumentByName",
            contextualName = "fetchIndexInstrumentByName",
            lowCardinalityKeyValues = {"description", "Fetches index instrument by name from Redis", "classification", "index"}
    )
    @Override
    public Instrument fetchIndexInstrumentByName(String key) throws Exception {
        Map<Object, Object> map = redisTemplate.opsForHash().entries(indicesKeyname + ":" + key);
        return Instrument.fromJSON(map.get("price").toString());
    }

}
