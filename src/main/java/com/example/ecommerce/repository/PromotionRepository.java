package com.example.ecommerce.repository;

import com.example.ecommerce.domain.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    Page<Promotion> findAllByIsUsed(boolean isUsed, Pageable pageable);

    Optional<Promotion> findByCode(String code);
}
