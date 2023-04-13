package com.example.ecommerce.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProductOnSaleInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean isOnSale;
    private Double discountPercent;
    private LocalDateTime expirationDate;

    @OneToOne(mappedBy = "onSaleInformation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Product product;

    @Transient
    //TODO: calculate the discounted price
    private Double discountedPrice;
}
