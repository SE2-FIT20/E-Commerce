package com.example.ecommerce.dto.request.customer;

import lombok.Data;

@Data
public class UpdateCustomerRequest {
    private String name;
    private String email;
    //TODO: change password for another endpoint
    private String password;
    private String address;
    private String phoneNumber;
}
