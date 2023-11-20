package io.gitub.amithkoujalgi.demo.services;

import io.gitub.amithkoujalgi.demo.entities.PortfolioInstrument;
import io.gitub.amithkoujalgi.demo.repositories.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioService {
    @Autowired
    private PortfolioRepository portfolioRepository;


    public List<PortfolioInstrument> getAllPortfolioInstrumentsByUserId(Long userId) {
        return portfolioRepository.findAllByUser_Id(userId);
    }
}
