package com.example.ecommerce.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
//TODO: category list (admin create)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private Category category;
    private Double price;

    @ElementCollection
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<String> images;
    private Integer quantity;
    private int sold;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Review> reviews;

    @ManyToOne
    @JsonIgnore
    private Store store;


    public enum Status {
        AVAILABLE, SOLD_OUT;

        public static Status fromString(String str) {
            if (str.toUpperCase().equals("AVAILABLE")) {
                return AVAILABLE;
            } else if (str.toUpperCase().equals("SOLDOUT") || str.toUpperCase().equals("SOLD_OUT")) {
                return SOLD_OUT;
            }
            return null;
        }
    }
}
