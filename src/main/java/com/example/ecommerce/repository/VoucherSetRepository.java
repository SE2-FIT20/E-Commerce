package com.example.ecommerce.repository;

import com.example.ecommerce.domain.VoucherSet;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherSetRepository extends JpaRepository<VoucherSet, Long> {
}
