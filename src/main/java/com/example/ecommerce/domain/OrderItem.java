package com.example.ecommerce.domain;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class OrderItem {
    private Long productId;
    private Integer quantity;
}
