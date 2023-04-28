package com.example.ecommerce.dto.request.promotion;

import lombok.Data;

import java.util.List;

@Data
public class AddPromotionToCartRequest {
    private List<Long> promotionIds;
}
