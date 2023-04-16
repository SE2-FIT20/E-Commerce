package com.example.ecommerce.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Cascade;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString(exclude = {"reviews", "store"})// to prevent the error of "could not initialize proxy - no Session"
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(columnDefinition="LONGTEXT")
    private String description;
    @Enumerated(EnumType.STRING)
    private Category category;
    private Double price;

    @ElementCollection
    @Cascade({org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
    private List<String> images;
    private Integer quantity;
    private int sold;

    @Transient
    @JsonIgnore
    private Status status;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Review> reviews;

    @ManyToOne
    @JsonIgnore
    private Store store;

    @OneToMany(mappedBy = "product", orphanRemoval = true)
    private List<OrderItem> orderItem;
    @Transient
    private Double rating;

    public Double getRating() {
        if (reviews == null || reviews.isEmpty()) {
            return 0.0;
        }
        double sum = 0;
        for (Review review : reviews) {
            sum += review.getRating();
        }
        return sum / reviews.size();
    }

    public Status getStatus() {
        if (quantity > 0) {
            return Status.AVAILABLE;
        } else {
            return Status.SOLD_OUT;
        }
    }

    public enum Status {
        AVAILABLE, SOLD_OUT;

        public static Status fromString(String str) {
            if (str.equalsIgnoreCase("AVAILABLE")) {
                return AVAILABLE;
            } else if (str.equalsIgnoreCase("SOLDOUT") || str.equalsIgnoreCase("SOLD_OUT")) {
                return SOLD_OUT;
            }
            return null;
        }
    }
}
