package com.example.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class HelloController {
    @GetMapping("/")
    public ResponseEntity<?> hello() {
        Map<String, String> body = new LinkedHashMap<>();
        body.put("date", LocalDateTime.now().toString());
        body.put("message", "Hello World!");
        return ResponseEntity.ok(body);
    }
}
