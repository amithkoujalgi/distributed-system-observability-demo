package io.github.amithkoujalgi.demo.repositories;


import io.github.amithkoujalgi.demo.entities.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstrumentRepository extends JpaRepository<Instrument, Long> {
    Instrument findByName(String name);

    default Instrument findRandomInstrument() {
        List<Instrument> allInstruments = findAll();
        if (!allInstruments.isEmpty()) {
            int randomIndex = (int) (Math.random() * allInstruments.size());
            return allInstruments.get(randomIndex);
        }
        return null; // Return null or handle the case when there are no instruments
    }
}
