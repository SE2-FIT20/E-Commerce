package com.example.ecommerce.repository;

import com.example.ecommerce.domain.Customer;
import com.example.ecommerce.domain.Product;
import com.example.ecommerce.domain.Review;
import com.example.ecommerce.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findAllByProduct(Product product, Pageable pageable);

    List<Review> findAllByCustomer(User customer);

    boolean existsByCustomerAndProduct(Customer customer, Product product);
}
