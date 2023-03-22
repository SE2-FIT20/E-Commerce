package com.example.ecommerce.dto.request.product;

import lombok.Data;

import java.util.List;

@Data
public class UpdateProductRequest {
    private Long productId;
    private String name;
    private String description;
    private String category;
    private Double price;
    private List<String> images;
}
