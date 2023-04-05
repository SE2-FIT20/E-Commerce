package com.example.ecommerce.config;


import com.example.ecommerce.dto.response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException, IOException {

        // if authentication failed due to locked account
        if (authException.getClass().equals(LockedException.class)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(APPLICATION_JSON_VALUE);
            Response body = Response.builder()
                    .status(HttpServletResponse.SC_UNAUTHORIZED)
                    .message("Your account is locked")
                    .build();
            new ObjectMapper().writeValue(response.getOutputStream(), body);
        } else {
            // if authentication failed due to incorrect credentials
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());

        }
    }

}
