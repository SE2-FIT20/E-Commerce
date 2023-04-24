package com.example.ecommerce.dto.response;

import com.example.ecommerce.domain.Category;
import com.example.ecommerce.domain.Product;
import com.example.ecommerce.domain.Review;
import com.example.ecommerce.domain.Store;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    private StoreInformationForProduct store;// this information is needed when seeing product details
    private LocalDateTime createdAt;


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
        this.store = new StoreInformationForProduct(product.getStore());
        this.createdAt = product.getCreatedAt();
    }

    public static List<ProductDetailedInfo> from(List<Product> products) {
        return products.stream().map(ProductDetailedInfo::new).collect(Collectors.toList());
    }

    @Data
    private class StoreInformationForProduct {
        private Long id;
        private String name;
        private String avatar;
        private long numberOfProducts;
        private long numbersOfReviews;
        private double averageRating;
        private LocalDateTime createdAt;
        private String city;

        public StoreInformationForProduct(Store store) {
            this.id = store.getId();
            this.name = store.getName();
            this.avatar = store.getAvatar();
            this.numberOfProducts = store.getInventory().size();
            this.numbersOfReviews = store.getInventory().stream()
                    .mapToInt(p -> p.getReviews().size()).sum();
            this.averageRating = store.getInventory()
                    .stream().filter(product -> product.getReviews().size() > 0)
                    .mapToDouble(product -> product.getReviews().stream().mapToDouble(Review::getRating).average().orElse(0.0))
                    .average()
                    .orElse(0.0);
            this.createdAt = store.getCreatedAt();
            this.city = store.getCity();
        }
    }
}
