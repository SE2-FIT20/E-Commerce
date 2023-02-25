package com.example.ecommerce.dto.request.auth;

import io.swagger.v3.oas.annotations.media.ExampleObject;
import lombok.Data;

@Data
public class RegistrationRequest {
    private String name;
    private String email;
    private String password;
    private String role;
}
