package com.example.ecommerce.dto.request.promotion;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdatePromotionRequest {
    private String description;
    private Double percent;
    private LocalDateTime startAt;
    private LocalDateTime expiredAt;
    private String name;
}
