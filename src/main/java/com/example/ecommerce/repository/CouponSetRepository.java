package com.example.ecommerce.repository;

import com.example.ecommerce.domain.Coupon;
import com.example.ecommerce.domain.CouponSet;
import com.example.ecommerce.domain.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CouponSetRepository extends JpaRepository<CouponSet, Long> {
    Page<CouponSet> findAllByStore(Store store, Pageable pageable);

    Page<CouponSet> findAllByStoreAndExpiredAtBefore(Store store, LocalDateTime now, Pageable pageable);
}
