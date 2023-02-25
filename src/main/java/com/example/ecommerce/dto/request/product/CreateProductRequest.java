package com.example.ecommerce.dto.request.product;

import lombok.Data;

@Data
public class CreateProductRequest {
    private String name;
    private String description;
    private String category;
    private Double price;
    private String image;
}
