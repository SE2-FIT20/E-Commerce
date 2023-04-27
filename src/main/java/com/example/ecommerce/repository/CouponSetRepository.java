package com.example.ecommerce.repository;

import com.example.ecommerce.domain.CouponSet;
import com.example.ecommerce.domain.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface CouponSetRepository extends JpaRepository<CouponSet, Long> {

    @Query
    Page<CouponSet> findAllByStore(Store store, Pageable pageable);

    @Query("select cs FROM CouponSet cs WHERE cs.id in  (select cs.id from CouponSet cs JOIN Coupon c ON cs = c.couponSet AND c.customer IS NULL  GROUP BY cs.id HAVING COUNT(*) > 0) order by cs.percent desc")
    Page<CouponSet> findAllByStoreAndExpiredAtAfterOrderByPercentDesc(Store store, LocalDateTime now, Pageable pageable);
}
