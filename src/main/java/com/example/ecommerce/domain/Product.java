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
//TODO: category list (admin create)
//TODO: product (get all - brief, get detail - detailedInfo with rating)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String category;
    private Double price;
    @ElementCollection
    private List<String> images;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviews;

    public static Product createProduct(String name, String description, String category, Double price, List<String> images) {
        return Product.builder()
                .name(name)
                .description(description)
                .category(category)
                .price(price)
                .images(images)
                .build();
    }

}
