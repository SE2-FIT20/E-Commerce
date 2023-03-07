package com.example.ecommerce.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String category;
    private Double price;
    private String image;
    private List<Review> reviews;

    public static Product createProduct(String name, String description, String category, Double price, String image) {
        return Product.builder()
                .name(name)
                .description(description)
                .category(category)
                .price(price)
                .image(image)
                .build();
    }

}
