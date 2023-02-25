package com.example.ecommerce.controller;

import com.example.ecommerce.dto.LoginRequest;
import com.example.ecommerce.dto.request.auth.RegistrationRequest;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.service.security.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;


    @Operation(
            summary = "Register account",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RegistrationRequest.class),
                            examples = @ExampleObject(value = """
                                        {
                                        "email": "Hainguyen@gmail.com",
                                        "password": "123456",
                                        "name": "Hai Nguyen BuyItAll Store",
                                        "role": "STORE"
                                        }
                                        """
                            )
                    )
            )

    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Register account successfully!",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Response.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "status": 200,
                                        "message": "Customer registered successfully",
                                        "data": null
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Wrong username or password",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Response.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "status": 400,
                                        "message": "Wrong username or password",
                                        "data": null
                                    }
                                    """)
                    )
            )
    }
    )
    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody   RegistrationRequest request) {
        return null;
    }

    @Operation(
            summary = "Login",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RegistrationRequest.class),
                            examples = @ExampleObject(value = """
                                        {
                                        "email": "Hainguyen@gmail.com",
                                        "password": "123456"
                                        }
                                        """
                            )
                    )
            )

    )
    @ApiResponses (
            value = {
                    @ApiResponse (
                            responseCode = "200",
                            description = "Login successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                    {
                                        "status": 200,
                                        "message": "Login successfully",
                                        "data": {
                                            "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiQ1VTVE9NRVIiLCJlbWFpbCI6ImN1czNAYy5jb20iLCJpYXQiOjE2NzQ1NTgwMzksImV4cCI6MTY3NTE2MjgzOX0.AJmtO2jLNF1GahDPnbstJwVZCulIGI6_pB-ObbqhVMo",
                                            "role": "STORE"
                                        }
                                    }
                                    """)
                            )

                    ),
                    @ApiResponse (
                            responseCode = "400",
                            description = "Wrong username or password",
                            content = @Content (
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject ( value ="""
                                    {
                                        "status": 400,
                                        "message": "Wrong username or password",
                                        "data": null
                                    }
                                    """)
                            )
                    )
            }
    )

    @PostMapping (value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Response> login(@RequestBody LoginRequest logInRequest) {
        return authService.login(logInRequest);
    }
}
