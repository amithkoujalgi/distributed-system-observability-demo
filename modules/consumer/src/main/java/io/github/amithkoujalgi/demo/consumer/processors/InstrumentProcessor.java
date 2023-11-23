package io.github.amithkoujalgi.demo.consumer.processors;

import io.github.amithkoujalgi.demo.models.http.Instrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class InstrumentProcessor implements ItemProcessor<Instrument, Instrument> {
    private static final Logger log = LoggerFactory.getLogger(InstrumentProcessor.class);
    @Override
    public Instrument process(final Instrument instrument) {
        log.info("Processing instrument: {}", instrument.getName());
        return instrument;
    }
}
