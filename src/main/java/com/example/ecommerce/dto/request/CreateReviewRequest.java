package com.example.ecommerce.dto.request;

import com.example.ecommerce.domain.Product;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateReviewRequest {
    private int rating;
    private String comment;
    private Long productId;
}
