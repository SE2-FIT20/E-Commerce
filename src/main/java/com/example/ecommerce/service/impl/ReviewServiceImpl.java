package com.example.ecommerce.service.impl;

import com.example.ecommerce.domain.Product;
import com.example.ecommerce.domain.Review;
import com.example.ecommerce.dto.request.review.CreateReviewRequest;
import com.example.ecommerce.dto.request.review.UpdateReviewRequest;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.repository.ReviewRepository;
import com.example.ecommerce.service.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public ResponseEntity<Response> getReviewByProductId(Long productId) {
        Review review = reviewRepository.findReviewByProductId(productId);
        if (review != null) {
            return ResponseEntity.ok(Response.builder()
                    .status(200)
                    .message("Get review successfully")
                    .data(review)
                    .build());
        }
        return null;
    }

    @Override
    public ResponseEntity<Response> createReview(Long customerId, CreateReviewRequest request) {
        Review review = Review.builder()
                .rating(request.getRating())
                .comment(request.getComment())
                .timestamp(LocalDateTime.now())
                .build();

        reviewRepository.save(review);

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Create review successfully")
                .data(review)
                .build());
    }

    @Override
    public ResponseEntity<Response> updateReview(Long productId, UpdateReviewRequest request) {
        Review review = reviewRepository.findReviewByProductId(productId);
        if (review != null) {
            review.setRating(request.getRating());
            review.setComment(request.getComment());
            review.setTimestamp(LocalDateTime.now());
            reviewRepository.save(review);
            return ResponseEntity.ok(Response.builder()
                    .status(200)
                    .message("Update review successfully")
                    .data(review)
                    .build());
        }
        return null;
    }
}
