package com.example.ecommerce.service.service;

import com.example.ecommerce.domain.Customer;
import com.example.ecommerce.domain.Product;
import com.example.ecommerce.domain.Review;
import com.example.ecommerce.domain.User;
import com.example.ecommerce.dto.response.Response;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReviewService {
    void save(Review currentReview);
    void deleteByReviewId(Long reviewId);
    Review getReviewById(Long reviewId);

    List<Review> getAllReview();


    List<Review>  getReviewByCustomer(User customer);

    Review findReviewById(Long reviewId);

    Page<Review> findAll(Example<Review> example, Pageable pageable);

    Page<Review> findAllByProduct(Product product, Pageable pageable);

    boolean existsByCustomerAndProduct(Customer customer, Product product);
}
