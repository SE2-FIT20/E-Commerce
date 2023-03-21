package com.example.ecommerce.domain;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String category;
    private Double price;
    private String image;
    private Integer quantity;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviews;

    public static Product createProduct(String name, String description, String category, Double price, String image, Integer quantity) {
        return Product.builder()
                .name(name)
                .description(description)
                .category(category)
                .price(price)
                .image(image)
                .quantity(quantity)
                .build();
    }

}
