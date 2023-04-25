package com.example.ecommerce.service.impl;

import com.example.ecommerce.domain.VoucherSet;
import com.example.ecommerce.dto.request.promotion.CreatePromotionRequest;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.VoucherRepository;
import com.example.ecommerce.repository.VoucherSetRepository;
import com.example.ecommerce.service.service.VoucherSetService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.ecommerce.utils.Utils.generateAvatarLink;

@Service
@AllArgsConstructor
public class VoucherSetServiceImpl implements VoucherSetService {
    private final VoucherSetRepository voucherSetRepository;
    private final VoucherRepository voucherRepository;
    @Override
    public void createVoucherSet(CreatePromotionRequest request) {

        VoucherSet voucherSet = new VoucherSet();
        voucherSet.setName(request.getName());
        voucherSet.setImage(generateAvatarLink(String.valueOf(request.getPercent())));
        voucherSet.setPercent(request.getPercent());
        voucherSet.setDescription(request.getDescription());
        voucherSet.setExpiredAt(request.getExpiredAt());
        voucherSet.setCreatedAt(LocalDateTime.now());
        voucherSet.setImage(generateAvatarLink(String.valueOf(request.getPercent())));
        if (request.getStartAt() == null) {
            voucherSet.setStartAt(LocalDateTime.now());
        } else {
            voucherSet.setStartAt(request.getStartAt());
        }
        voucherSet.setName(request.getName());

        voucherSet.setVouchers(new ArrayList<>());
        voucherSet.addItems(request.getQuantity());
        System.out.println(voucherSet.getVouchers().size());
        voucherSetRepository.save(voucherSet); // save voucherSet to database, this will cascade save all vouchers


    }


    @Override
    public Page<VoucherSet> findAll(Pageable pageable) {

        return voucherSetRepository.findAll(pageable);
    }

    @Override
    public List<VoucherSet> findAll() {
        return voucherSetRepository.findAll();
    }

    @Override
    public VoucherSet findById(Long id) {

        return voucherSetRepository.findById(id).orElseThrow(() -> new NotFoundException("Voucher set not found"));
    }

    @Override
    public void deleteVoucherSet(VoucherSet voucherSet) {
        voucherSetRepository.delete(voucherSet);
    }

    @Override
    public void save(VoucherSet voucherSet) {
        voucherSetRepository.save(voucherSet);
    }

    @Override
    public List<VoucherSet> findAllByExpiredAtAfter(LocalDateTime now) {

        return voucherSetRepository.findAllByExpiredAtAfter(now);
    }
}
