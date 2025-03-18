package com.pranjal.endpoint;

import com.pranjal.dto.LoginRequest;
import com.pranjal.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Tag(name = "User Authentication", description = "All the user authentication APIs")
@RequestMapping("/api/v1/auth")
public interface AuthEndpoint {

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request — invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized — authentication required"),
            @ApiResponse(responseCode = "500", description = "Internal server error — something went wrong")
    })
    @Operation(
            summary = "Register a new user",
            description = "Accepts user details and registers a new user account.",
            tags = {"User Authentication"}
    )
    @PostMapping("/")
    ResponseEntity<?> registerUser(@RequestBody UserDto userDto, HttpServletRequest request) throws Exception;

    @Operation(
            summary = "User login",
            description = "Handles user login with email/username and password.",
            tags = {"User Authentication"}
    )
    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody LoginRequest loginRequest);
}