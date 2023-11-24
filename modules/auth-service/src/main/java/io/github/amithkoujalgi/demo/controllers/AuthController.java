package io.github.amithkoujalgi.demo.controllers;

import io.github.amithkoujalgi.demo.models.http.UserAuthToken;
import io.github.amithkoujalgi.demo.models.http.UserCreds;
import io.github.amithkoujalgi.demo.models.http.UserLoginStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Auth", description = "Auth APIs")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Operation(summary = "Login")
    @ApiResponses({@ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = Boolean.class), mediaType = "application/json")}), @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public UserAuthToken login(@RequestBody final UserCreds userCreds) {
        return new UserAuthToken(UUID.randomUUID().toString());
    }

    @Operation(summary = "Check if user is logged in")
    @ApiResponses({@ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = Boolean.class), mediaType = "application/json")}), @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/is-logged-in/{token}")
    @ResponseStatus(HttpStatus.OK)
    public UserLoginStatus isLoggedIn(@PathVariable String token) {
        UUID uuid = UUID.fromString(token);
        return new UserLoginStatus(true);
    }

}