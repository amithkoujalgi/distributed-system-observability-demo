package io.gitub.amithkoujalgi.demo.controllers;

import io.gitub.amithkoujalgi.demo.models.http.Instrument;
import io.gitub.amithkoujalgi.demo.repositories.InstrumentRepository;
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
public class InstrumentController {
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

    @Operation(summary = "Get all index instruments")
    @GetMapping("/indices")
    public List<Instrument> indices() {
        try {
            return instrumentRepository.fetchAllIndexInstruments();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "Get index instrument by name")
    @GetMapping("/index/{name}")
    public Instrument indexByName(@PathVariable String name) throws Exception {
        return instrumentRepository.fetchIndexInstrumentByName(name);
    }

    @Operation(summary = "Update an index instrument")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = Instrument.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    })
    @PutMapping("/index/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Instrument updateIndex(
            @PathVariable("name") final String name, @RequestBody final Instrument updatedIndex) {
        return updatedIndex;
    }
}

