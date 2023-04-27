package com.example.ecommerce.controller;

import com.example.ecommerce.domain.User;
import com.example.ecommerce.dto.request.auth.ChangePasswordRequest;
import com.example.ecommerce.dto.request.auth.LoginRequest;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/change-password")
    public ResponseEntity<Response> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        User currentUser = getCurrentUser();
        return authService.changePassword(currentUser,changePasswordRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody  RegistrationRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest logInRequest) {
        return authService.login(logInRequest);
    }

    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
