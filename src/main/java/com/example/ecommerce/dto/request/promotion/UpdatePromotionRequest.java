package com.example.ecommerce.dto.request.promotion;

import lombok.Data;

@Data
public class UpdatePromotionRequest {
    private Long promotionId;
    private String code;
    private String description;
    private double percent;
    private Long storedId;
}
