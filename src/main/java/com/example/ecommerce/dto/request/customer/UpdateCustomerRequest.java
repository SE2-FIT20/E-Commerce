package com.example.ecommerce.dto.request.customer;

import lombok.Data;

@Data
public class UpdateCustomerRequest {

    private String name;
    private String email;
    private String address;
    private String avatar;
    private String phone;
}
