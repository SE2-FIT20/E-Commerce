package com.example.ecommerce.repository;

import com.example.ecommerce.domain.Coupon;
import com.example.ecommerce.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
