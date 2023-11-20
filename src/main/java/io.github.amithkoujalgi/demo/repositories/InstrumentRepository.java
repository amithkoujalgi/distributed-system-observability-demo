package io.gitub.amithkoujalgi.demo.repositories;


import io.gitub.amithkoujalgi.demo.models.http.Instrument;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstrumentRepository {
    List<Instrument> fetchAllStockInstruments() throws Exception;

    Instrument fetchStockInstrumentByName(String key) throws Exception;

    List<Instrument> findStockInstrumentsByKeyword(String keyword) throws Exception;

    List<Instrument> fetchAllIndexInstruments() throws Exception;

    Instrument fetchIndexInstrumentByName(String key) throws Exception;

}