package com.example.ecommerce.service.security;

import com.example.ecommerce.domain.User;
import com.example.ecommerce.dto.JwtToken;
import com.example.ecommerce.dto.LoginRequest;
import com.example.ecommerce.dto.request.auth.RegistrationRequest;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<Response> register(RegistrationRequest registrationRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Response> login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        if (authentication.isAuthenticated()) {

        }
        User user = userRepository.findByEmail(loginRequest.getUsername());
        String jwtToken = jwtService.generateToken(user);
        JwtToken token = JwtToken.builder().token(jwtToken).build();
        return ResponseEntity.ok(Response.builder().status(200).message("Log in successfully!").data(token).build());
    }
}
