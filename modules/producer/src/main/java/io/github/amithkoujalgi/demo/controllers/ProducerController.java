package io.github.amithkoujalgi.demo.controllers;

import io.github.amithkoujalgi.demo.models.http.Instrument;
import io.github.amithkoujalgi.demo.repositories.InstrumentRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Instrument", description = "Instrument APIs")
@RestController
@RequestMapping("/api/instrument")
public class ProducerController {
    @Autowired
    private InstrumentRepository instrumentRepository;

    @Operation(summary = "List all stock instruments")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = List.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/stocks")
    public List<Instrument> stocks() {
        try {
            return instrumentRepository.fetchAllStockInstruments();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

