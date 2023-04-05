package com.example.ecommerce.domain;

import com.example.ecommerce.dto.response.ProductBriefInfo;
import com.example.ecommerce.dto.response.ProductDetailedInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @ManyToOne
    private Product product;
    private Integer quantity;

    public ProductBriefInfo getProduct() {
        return new ProductBriefInfo(product);
    }
}
