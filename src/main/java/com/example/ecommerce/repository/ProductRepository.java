package com.example.ecommerce.repository;

import com.example.ecommerce.domain.Category;
import com.example.ecommerce.domain.Product;
import com.example.ecommerce.domain.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Product> findAllByStore(Store store, Pageable pageable);


    Page<Product> findAllByStoreAndCategoryAndQuantityGreaterThan(Store store, Category category, Integer quantity, Pageable pageable);
    Page<Product> findAllByStoreAndQuantityGreaterThan(Store store, Integer quantity, Pageable pageable);
    Page<Product> findAllByCategoryAndQuantityGreaterThan(Category category, Integer quantity, Pageable pageable);


    Page<Product> findAllByQuantityGreaterThan(Integer quantity, Pageable pageable);

    Page<Product> findAllByStoreAndQuantityEquals(Store store, Integer quantity, Pageable pageable);
}
