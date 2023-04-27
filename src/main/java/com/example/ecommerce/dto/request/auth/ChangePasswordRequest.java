package com.example.ecommerce.dto.request.auth;

import lombok.Data;

@Data
public class ChangePasswordRequest {

    private String oldPassword;
    private String newPassword;
}
