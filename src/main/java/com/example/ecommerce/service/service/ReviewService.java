package com.example.ecommerce.service.service;

import com.example.ecommerce.domain.Product;
import com.example.ecommerce.domain.Review;
import com.example.ecommerce.domain.User;
import com.example.ecommerce.dto.response.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReviewService {
    void save(Review currentReview);
    void deleteByReviewId(Long reviewId);
    Review getReviewById(Long reviewId);

    List<Review> getAllReview();

    List<Review> getReviewByProduct(Product product);

    List<Review>  getReviewByCustomer(User customer);
}
