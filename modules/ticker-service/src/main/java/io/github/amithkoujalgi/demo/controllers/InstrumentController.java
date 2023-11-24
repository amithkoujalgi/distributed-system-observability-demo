package io.github.amithkoujalgi.demo.controllers;

import io.github.amithkoujalgi.demo.models.http.Instrument;
import io.github.amithkoujalgi.demo.repositories.InstrumentRepository;
import io.github.amithkoujalgi.demo.util.AuthTokenValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Instrument", description = "Instrument APIs")
@RestController
@RequestMapping("/api/instrument")
public class InstrumentController {
    @Autowired
    private InstrumentRepository instrumentRepository;
    @Autowired
    AuthTokenValidator authTokenValidator;

    @Operation(summary = "List all stock instruments")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = List.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/stocks")
    public List<Instrument> stocks(@RequestHeader(value = "access-token") String accessToken) {
        try {
            if (authTokenValidator.validateAuthToken(accessToken)) {
                return instrumentRepository.fetchAllStockInstruments();
            } else {
                throw new IllegalArgumentException("Invalid token!");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "Get stock instrument by name")
    @GetMapping("/stock/{name}")
    public Instrument stockByName(@PathVariable String name) throws Exception {
        return instrumentRepository.fetchStockInstrumentByName(name);
    }

    @Operation(summary = "Find stock instrument by keyword")
    @GetMapping("/stock/find/{keyword}")
    public List<Instrument> findStockByKeyword(@PathVariable String keyword) throws Exception {
        return instrumentRepository.findStockInstrumentsByKeyword(keyword);
    }
}

