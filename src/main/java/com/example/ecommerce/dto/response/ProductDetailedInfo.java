package com.example.ecommerce.dto.response;

import com.example.ecommerce.domain.Review;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
//TODO: return this dto
public class ProductDetailedInfo {
    private Long id;
    private String name;
    private String description;
    private String category;
    private Double price;
    @ElementCollection
    private List<String> images;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviews;
    private String storeName;
    private String storeImage;
}
