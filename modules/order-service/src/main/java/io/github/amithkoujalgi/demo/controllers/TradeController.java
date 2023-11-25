package io.github.amithkoujalgi.demo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.amithkoujalgi.demo.entities.PortfolioInstrument;
import io.github.amithkoujalgi.demo.models.http.*;
import io.github.amithkoujalgi.demo.repositories.TradeRepository;
import io.github.amithkoujalgi.demo.services.PortfolioService;
import io.github.amithkoujalgi.demo.util.AuthTokenValidator;
import io.micrometer.observation.annotation.Observed;
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

    @Autowired
    AuthTokenValidator authTokenValidator;

    @Operation(summary = "Place an order")
    @Observed(name = "TradeController.placeOrder",
            contextualName = "placeOrder",
            lowCardinalityKeyValues = {})
    @ApiResponses({@ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = Boolean.class), mediaType = "application/json")}), @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping("/order")
    @ResponseStatus(HttpStatus.OK)
    public boolean placeOrder(@RequestHeader(value = "access-token") String accessToken, @RequestBody final PlaceOrder order) {
        try {
            if (authTokenValidator.validateAuthToken(accessToken)) {
                Order newOrder = new Order(order.getInstrument(), new Date(System.currentTimeMillis()), order.getPrice(), order.getQuantity(), order.getUserId(), UUID.randomUUID().toString(), order.getType());
                return tradeRepository.placeOrder(newOrder);
            } else {
                throw new IllegalArgumentException("Invalid token!");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "List orders of a user")
    @Observed(name = "TradeController.listOrders",
            contextualName = "listOrders",
            lowCardinalityKeyValues = {})
    @ApiResponses({@ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = Boolean.class), mediaType = "application/json")}), @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/orders/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<UserOrder> listOrders(@RequestHeader(value = "access-token") String accessToken, @PathVariable String userId) {
        try {
            if (authTokenValidator.validateAuthToken(accessToken)) {
                return tradeRepository.listOrdersOfUser(userId);
            } else {
                throw new IllegalArgumentException("Invalid token!");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "Get portfolio of a user")
    @Observed(name = "TradeController.getPortfolioOfUser",
            contextualName = "getPortfolioOfUser",
            lowCardinalityKeyValues = {})
    @ApiResponses({@ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = Boolean.class), mediaType = "application/json")}), @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/portfolio/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<PortfolioItem> getPortfolioOfUser(@RequestHeader(value = "access-token") String accessToken, @PathVariable String userId) throws JsonProcessingException {
        try {
            if (authTokenValidator.validateAuthToken(accessToken)) {
                return tradeRepository.getPortfolioOfUser(userId);
            } else {
                throw new IllegalArgumentException("Invalid token!");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "Get portfolio of a user with instrument price averages")
    @Observed(name = "TradeController.getPortfolioWithInstrumentPriceAveragesOfUser",
            contextualName = "getPortfolioWithInstrumentPriceAveragesOfUser",
            lowCardinalityKeyValues = {})
    @ApiResponses({@ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = Boolean.class), mediaType = "application/json")}), @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/portfolio/averages/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<PortfolioItemAverage> getPortfolioWithInstrumentPriceAveragesOfUser(@RequestHeader(value = "access-token") String accessToken, @PathVariable String userId) throws JsonProcessingException {
        try {
            if (authTokenValidator.validateAuthToken(accessToken)) {
                return tradeRepository.getPortfolioWithInstrumentPriceAveragesOfUser(userId);
            } else {
                throw new IllegalArgumentException("Invalid token!");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "Get user's holdings")
    @Observed(name = "TradeController.holdings",
            contextualName = "holdings",
            lowCardinalityKeyValues = {})
    @ApiResponses({@ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = Boolean.class), mediaType = "application/json")}), @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/holdings/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<UserHolding> holdings(@RequestHeader(value = "access-token") String accessToken, @PathVariable Long userId) {
        try {
            if (authTokenValidator.validateAuthToken(accessToken)) {
                List<PortfolioInstrument> portfolio = portfolioService.getAllPortfolioInstrumentsByUserId(userId);
                return new ArrayList<>();
            } else {
                throw new IllegalArgumentException("Invalid token!");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}