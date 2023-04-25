package com.example.ecommerce.service.service;

import com.example.ecommerce.domain.VoucherSet;
import com.example.ecommerce.dto.request.promotion.CreatePromotionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;


public interface VoucherSetService {
    void createVoucherSet(CreatePromotionRequest request);

    Page<VoucherSet> findAll(Pageable pageable);

    List<VoucherSet> findAll();
    VoucherSet findById(Long id);

    void deleteVoucherSet(VoucherSet voucherSet);

    void save(VoucherSet voucherSet);

    List<VoucherSet> findAllByExpiredAtAfter(LocalDateTime now);
}
