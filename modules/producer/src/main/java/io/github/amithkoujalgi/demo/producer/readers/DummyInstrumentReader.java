package io.github.amithkoujalgi.demo.producer.readers;

import io.github.amithkoujalgi.demo.models.http.Instrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;

public class DummyInstrumentReader implements ItemReader<Instrument> {
    private static final Logger log = LoggerFactory.getLogger(DummyInstrumentReader.class);

    @Override
    public Instrument read() {
        return Instrument.random();
    }
}
