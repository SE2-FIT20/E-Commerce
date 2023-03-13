package com.example.ecommerce.dto.request.promotion;

import lombok.Data;

@Data
public class UpdatePromotionRequest {
    private Long promotionId;
    private String name;
    private String description;
    private double percent;
    private Long storedId;
    private boolean isGlobal;
}
