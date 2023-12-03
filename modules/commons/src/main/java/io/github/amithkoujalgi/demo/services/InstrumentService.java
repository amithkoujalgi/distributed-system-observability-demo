package io.github.amithkoujalgi.demo.services;


import io.github.amithkoujalgi.demo.models.http.Instrument;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * APIs to access stock instruments from Redis
 */
@Service
public interface InstrumentService {
    List<Instrument> fetchAllStockInstruments() throws Exception;

    Instrument fetchStockInstrumentByName(String key) throws Exception;

    List<Instrument> findStockInstrumentsByKeyword(String keyword) throws Exception;

    void addStockInstrument(Instrument instrument) throws Exception;
}