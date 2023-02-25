package com.example.ecommerce.dto.request.auth;

import lombok.Data;

@Data
public class ChangeAccessRequest {
    private Long userId;
    private String operation;
}
