package io.github.amithkoujalgi.demo.controllers;

import io.github.amithkoujalgi.demo.entities.User;
import io.github.amithkoujalgi.demo.repositories.UserRepository;
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

import java.util.List;

@Tag(name = "User", description = "User APIs")
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Operation(summary = "Get users")
    @Observed(name = "UserController.allUsers",
            contextualName = "allUsers",
            lowCardinalityKeyValues = {})
    @ApiResponses({@ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = Boolean.class), mediaType = "application/json")}), @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    @Operation(summary = "Add user")
    @Observed(name = "UserController.addUser",
            contextualName = "addUser",
            lowCardinalityKeyValues = {})
    @ApiResponses({@ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = Boolean.class), mediaType = "application/json")}), @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    public User addUser(User user) {
        userRepository.save(user);
        return user;
    }
}