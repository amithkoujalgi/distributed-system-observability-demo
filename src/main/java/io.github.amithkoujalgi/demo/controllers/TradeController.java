package io.gitub.amithkoujalgi.demo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.gitub.amithkoujalgi.demo.entities.PortfolioInstrument;
import io.gitub.amithkoujalgi.demo.models.http.*;
import io.gitub.amithkoujalgi.demo.repositories.TradeRepository;
import io.gitub.amithkoujalgi.demo.services.PortfolioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Tag(name = "Trade", description = "Trading APIs")
@RestController
@RequestMapping("/api/trade")
public class TradeController {
    @Autowired
    private TradeRepository tradeRepository;
    @Autowired
    PortfolioService portfolioService;

    @Operation(summary = "Place an order")
    @ApiResponses({@ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = Boolean.class), mediaType = "application/json")}), @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping("/order")
    @ResponseStatus(HttpStatus.OK)
    public boolean placeOrder(@RequestBody final PlaceOrder order) {
        Order newOrder = new Order(order.getInstrument(), new Date(System.currentTimeMillis()), order.getPrice(), order.getQuantity(), order.getUserId(), UUID.randomUUID().toString(), order.getType());
        return tradeRepository.placeOrder(newOrder);
    }

    @Operation(summary = "List orders of a user")
    @ApiResponses({@ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = Boolean.class), mediaType = "application/json")}), @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/orders/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<UserOrder> listOrders(@PathVariable String userId) {
        return tradeRepository.listOrdersOfUser(userId);
    }

    @Operation(summary = "Get portfolio of a user")
    @ApiResponses({@ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = Boolean.class), mediaType = "application/json")}), @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/portfolio/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<PortfolioItem> getPortfolioOfUser(@PathVariable String userId) throws JsonProcessingException {
        return tradeRepository.getPortfolioOfUser(userId);
    }

    @Operation(summary = "Get portfolio of a user with instrument price averages")
    @ApiResponses({@ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = Boolean.class), mediaType = "application/json")}), @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/portfolio/averages/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<PortfolioItemAverage> getPortfolioWithInstrumentPriceAveragesOfUser(@PathVariable String userId) throws JsonProcessingException {
        return tradeRepository.getPortfolioWithInstrumentPriceAveragesOfUser(userId);
    }

    @Operation(summary = "Get user's holdings")
    @ApiResponses({@ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = Boolean.class), mediaType = "application/json")}), @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/holdings/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<UserHolding> holdings(@PathVariable Long userId) {
        List<PortfolioInstrument> portfolio = portfolioService.getAllPortfolioInstrumentsByUserId(userId);
        return new ArrayList<>();
    }
}