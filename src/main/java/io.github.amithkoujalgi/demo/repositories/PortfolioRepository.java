package io.gitub.amithkoujalgi.demo.repositories;


import io.gitub.amithkoujalgi.demo.entities.PortfolioInstrument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<PortfolioInstrument, Long> {
    // custom query methods
    List<PortfolioInstrument> findAllByUser_Id(Long userId);

}
