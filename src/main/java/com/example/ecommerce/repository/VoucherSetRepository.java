package com.example.ecommerce.repository;

import com.example.ecommerce.domain.VoucherSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VoucherSetRepository extends JpaRepository<VoucherSet, Long> {
    List<VoucherSet> findAllByExpiredAtAfterOrderByPercentAsc(LocalDateTime now);

}
