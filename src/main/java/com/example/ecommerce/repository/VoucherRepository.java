package com.example.ecommerce.repository;

import com.example.ecommerce.domain.Coupon;
import com.example.ecommerce.domain.Customer;
import com.example.ecommerce.domain.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    List<Voucher> findAllByCustomerAndIsUsed(Customer customer, boolean isUsed);
}
