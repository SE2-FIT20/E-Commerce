package com.example.ecommerce.dto.request.product;

import lombok.Data;

import java.awt.*;
import java.util.List;

@Data
public class CreateProductRequest {
    private String name;
    private String description;
    private String category;
    private Double price;
    private Integer quantity;
    private List<String> images;

}
