package com.example.ecommerce.repository;

import com.example.ecommerce.domain.CouponSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponSetRepository extends JpaRepository<CouponSet, Long> {
}
