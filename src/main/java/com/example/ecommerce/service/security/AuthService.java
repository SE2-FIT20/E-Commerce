package com.example.ecommerce.service.security;

import com.example.ecommerce.dto.request.auth.LoginRequest;
import com.example.ecommerce.dto.request.auth.RegistrationRequest;
import com.example.ecommerce.dto.response.Response;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<Response> register(RegistrationRequest registrationRequest);

    ResponseEntity<Response> login(LoginRequest loginRequest);
}
