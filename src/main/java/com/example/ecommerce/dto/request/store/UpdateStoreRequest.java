package com.example.ecommerce.dto.request.store;

import lombok.Data;

import java.util.List;

@Data
public class UpdateStoreRequest {

    private String name;
    private String description;
    private String address;
    private String avatar;
    private String phoneNumber;
    private String city;
}
