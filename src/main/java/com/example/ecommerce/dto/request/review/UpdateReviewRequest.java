package com.example.ecommerce.dto.request.review;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateReviewRequest {
    private int rating;
    private String comment;
    private LocalDateTime timestamp;
}
