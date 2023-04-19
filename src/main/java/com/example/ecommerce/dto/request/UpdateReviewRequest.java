package com.example.ecommerce.dto.request;

import com.example.ecommerce.domain.Product;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UpdateReviewRequest {

    private Double rating;
    private String comment;
    private List<String> images;
}
