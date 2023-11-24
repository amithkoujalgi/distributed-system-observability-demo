package io.github.amithkoujalgi.demo.repositories.impl;

import io.github.amithkoujalgi.demo.models.http.Instrument;
import io.github.amithkoujalgi.demo.repositories.InstrumentRepository;
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

    @Override
    public Instrument fetchStockInstrumentByName(String symbol) throws Exception {
        Map<Object, Object> map = redisTemplate.opsForHash().entries(stocksKeyname + ":" + symbol);
        return Instrument.fromJSON(map.get("price").toString());
    }

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

    @Override
    public Instrument fetchIndexInstrumentByName(String key) throws Exception {
        Map<Object, Object> map = redisTemplate.opsForHash().entries(indicesKeyname + ":" + key);
        return Instrument.fromJSON(map.get("price").toString());
    }

}
