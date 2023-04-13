package com.example.ecommerce.config;

import com.example.ecommerce.dto.response.Response;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Response> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.badRequest().body(Response.builder()
                .status(400)
                .message(e.getMessage())
                .build());
    }
}
