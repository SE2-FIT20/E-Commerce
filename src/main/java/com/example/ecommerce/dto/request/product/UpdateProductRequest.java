package com.example.ecommerce.dto.request.product;

import com.example.ecommerce.domain.Category;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.List;

@Data
public class UpdateProductRequest {
    private Long productId;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private Category category;
    private Double price;
    private List<String> images;

    private Integer quantity;
}
