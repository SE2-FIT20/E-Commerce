package com.example.ecommerce.repository;

import com.example.ecommerce.domain.Product;
import com.example.ecommerce.domain.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCase(String name);
    Page<Product> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<Product> findAllByStore(Store store, Pageable pageable);

}
