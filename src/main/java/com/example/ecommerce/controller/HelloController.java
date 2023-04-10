package com.example.ecommerce.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@CrossOrigin(value = "*", allowedHeaders = "*", origins = "*", maxAge = 3600)
public class HelloController {

    @Operation(
            summary = "Hello World",
            description = "This is the endpoint for checking if the backend is running"
    )
    @GetMapping("/")
    public ResponseEntity<?> hello() {
        Map<String, String> body = new LinkedHashMap<>();
        body.put("date", LocalDateTime.now().toString());
        body.put("message", "Hello World!");
        return ResponseEntity.ok(body);
    }
}
