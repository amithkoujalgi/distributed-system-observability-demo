package io.github.amithkoujalgi.demo.repositories.impl;

import io.github.amithkoujalgi.demo.models.http.Instrument;
import io.github.amithkoujalgi.demo.repositories.InstrumentRepository;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

public class InstrumentRepositoryImpl implements InstrumentRepository {
    @Override
    public List<Instrument> fetchAllStockInstruments() throws Exception {
        return new ArrayList<>();
    }

    @Override
    public Instrument fetchStockInstrumentByName(String symbol) throws Exception {
        return null;
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
        return new ArrayList<>();
    }

    @Override
    public Instrument fetchIndexInstrumentByName(String key) throws Exception {
        return null;
    }

}
