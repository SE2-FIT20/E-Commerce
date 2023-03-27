package com.example.ecommerce.repository;

import com.example.ecommerce.domain.Product;
import com.example.ecommerce.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review findReviewByProductId(Long productId);
}
