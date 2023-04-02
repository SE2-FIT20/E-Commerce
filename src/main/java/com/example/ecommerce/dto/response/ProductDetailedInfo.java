package com.example.ecommerce.dto.response;

import com.example.ecommerce.domain.Category;
import com.example.ecommerce.domain.Product;
import com.example.ecommerce.domain.Review;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//TODO: return this dto
public class ProductDetailedInfo {
    private Long id;
    private String name;
    private String description;
    @Enumerated
    private Category category;
    private Double price;
    private List<String> images;

    private List<Review> reviews;

    private int quantity;
    private int sold;
    private Double rating;
    private StoreBriefInfo store;

    public ProductDetailedInfo(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.category = product.getCategory();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        this.sold = product.getSold();
        this.images = product.getImages();
        this.reviews = product.getReviews();
        this.rating = product.getReviews().stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);
        this.store = new StoreBriefInfo(product.getStore());
    }

    public static List<ProductDetailedInfo> from(List<Product> products) {
        return products.stream().map(ProductDetailedInfo::new).collect(Collectors.toList());
    }
}
