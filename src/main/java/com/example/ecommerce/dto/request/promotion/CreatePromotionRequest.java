package com.example.ecommerce.dto.request.promotion;

import lombok.Data;

@Data
public class CreatePromotionRequest {

    private String name;
    private double percent;
    private String description;
    private Long storeId;
    private boolean isGlobal;

}
