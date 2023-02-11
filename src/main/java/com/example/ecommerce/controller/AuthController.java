package com.example.ecommerce.controller;

import com.example.ecommerce.dto.request.RegistrationRequest;
import com.example.ecommerce.dto.response.Response;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

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
                                        "data": {
                                            "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiQ1VTVE9NRVIiLCJlbWFpbCI6ImN1czNAYy5jb20iLCJpYXQiOjE2NzQ1NTgwMzksImV4cCI6MTY3NTE2MjgzOX0.AJmtO2jLNF1GahDPnbstJwVZCulIGI6_pB-ObbqhVMo"
                                        }
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
    public ResponseEntity<Response> register(@RequestBody RegistrationRequest request) {
        return null;
    }
}
