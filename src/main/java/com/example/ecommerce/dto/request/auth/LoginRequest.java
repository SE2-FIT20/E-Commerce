package com.example.ecommerce.dto.request.auth;

import lombok.*;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
