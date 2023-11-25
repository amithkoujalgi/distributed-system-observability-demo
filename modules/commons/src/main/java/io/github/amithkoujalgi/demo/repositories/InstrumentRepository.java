package io.github.amithkoujalgi.demo.repositories;


import io.github.amithkoujalgi.demo.entities.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstrumentRepository extends JpaRepository<Instrument, Long> {
    Instrument findByName(String name);
}
