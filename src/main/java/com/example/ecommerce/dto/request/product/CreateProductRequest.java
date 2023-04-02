package com.example.ecommerce.dto.request.product;

import com.example.ecommerce.domain.Category;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.awt.*;
import java.util.List;

@Data
public class CreateProductRequest {
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private Category category;
    private Double price;
    private Integer quantity;
    private List<String> images;

}
