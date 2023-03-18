package com.example.ecommerce.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double percent;
    private String description;
    private Long storeId;
    private boolean isGlobal;


    public static Promotion create(String name, double percent, String description, Long storeId, boolean isGlobal) {
        Promotion promotion = new Promotion();
        promotion.setName(name);
        promotion.setPercent(percent);
        promotion.setDescription(description);
        promotion.setStoreId(storeId);
        promotion.setGlobal(isGlobal);
        return promotion;
    }
}
