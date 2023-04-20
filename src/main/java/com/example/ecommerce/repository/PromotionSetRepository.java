package com.example.ecommerce.repository;

import com.example.ecommerce.domain.PromotionSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionSetRepository extends JpaRepository<PromotionSet, Long> {
}
