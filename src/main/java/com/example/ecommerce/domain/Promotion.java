package com.example.ecommerce.domain;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Promotion {

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
