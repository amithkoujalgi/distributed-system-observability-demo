package io.github.amithkoujalgi.demo.controllers;

import io.micrometer.observation.annotation.Observed;
import io.micrometer.tracing.Tracer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Test", description = "Test APIs")
@RestController
@RequestMapping("/api/test")
public class TestController {
    @Autowired
    Tracer tracer;

    // use this for external API calls. This will enable tracing of requests.
    @Autowired
    RestTemplate restTemplate;

    @Operation(summary = "Test")
    @ApiResponses({@ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = Boolean.class), mediaType = "application/json")}), @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/hello")
    @ResponseStatus(HttpStatus.OK)
    @Observed(name = "test.api",
            contextualName = "testing",
            lowCardinalityKeyValues = {"test1", "test2"})
    public List<Integer> test(HttpServletRequest request) {
//        Span newSpan = this.tracer.nextSpan().name("test");
//        try (Tracer.SpanInScope ws = this.tracer.withSpan(newSpan.start())) {
//            newSpan.event("test-evt");
//        } finally {
//            newSpan.end();
//        }
//        ResponseEntity<String> response = restTemplate.exchange("https://reqres.in/api/users?page=2", HttpMethod.GET, null, String.class);
        return new ArrayList<>();
    }
}