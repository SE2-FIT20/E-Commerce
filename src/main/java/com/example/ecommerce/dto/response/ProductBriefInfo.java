package com.example.ecommerce.dto.response;

import com.example.ecommerce.domain.Product;
import com.example.ecommerce.domain.Review;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductBriefInfo {
    private Long id;
    private String name;
    private String description;
    private String category;
    private Double price;
    private List<String> images;

    private StoreBriefInfo store;

    public ProductBriefInfo(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.category = product.getCategory();
        this.price = product.getPrice();
        this.images = product.getImages();
        this.store = new StoreBriefInfo(product.getStore());

    }
}
