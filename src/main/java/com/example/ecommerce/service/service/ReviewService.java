package com.example.ecommerce.service.service;

import com.example.ecommerce.domain.Product;
import com.example.ecommerce.dto.request.review.CreateReviewRequest;
import com.example.ecommerce.dto.request.review.UpdateReviewRequest;
import com.example.ecommerce.dto.response.Response;
import org.springframework.http.ResponseEntity;

public interface ReviewService {

    ResponseEntity<Response> getReviewByProductId(Long productId);

    ResponseEntity<Response> createReview(Long customerId, CreateReviewRequest request);

    ResponseEntity<Response> updateReview(Long productId, UpdateReviewRequest request);
}
