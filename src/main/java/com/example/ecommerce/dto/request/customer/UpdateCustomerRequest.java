package com.example.ecommerce.dto.request.customer;

import lombok.Data;

import java.util.List;

@Data
public class UpdateCustomerRequest {

    private String name;
    private String email;
    private List<String> addresses;
    private String avatar;
    private String phone;
}
