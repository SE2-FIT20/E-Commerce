package com.example.ecommerce.dto.request.promotion;

import lombok.Data;

@Data
public class CreatePromotionRequest {

    private String code;
    private String description;
    private double percent;
    private Long storedId;
}
