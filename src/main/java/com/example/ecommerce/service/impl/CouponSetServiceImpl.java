package com.example.ecommerce.service.impl;

import com.example.ecommerce.domain.Coupon;
import com.example.ecommerce.domain.CouponSet;
import com.example.ecommerce.domain.Store;
import com.example.ecommerce.domain.VoucherSet;
import com.example.ecommerce.dto.request.promotion.CreatePromotionRequest;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.CouponRepository;
import com.example.ecommerce.repository.CouponSetRepository;
import com.example.ecommerce.service.service.CouponSetService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.ecommerce.utils.Utils.generateAvatarLink;
import static com.example.ecommerce.utils.Utils.generateRandomString;

@Service
@AllArgsConstructor
public class CouponSetServiceImpl implements CouponSetService {
    private final CouponSetRepository couponSetRepository;
    private final CouponRepository couponRepository;
    @Override
    public void createCouponSet(Store store, CreatePromotionRequest request) {

        CouponSet couponSet = new CouponSet();
        couponSet.setStore(store);
        couponSet.setName(request.getName());
        couponSet.setPercent(request.getPercent());
        couponSet.setDescription(request.getDescription());
        couponSet.setExpiredAt(request.getExpiredAt());
        couponSet.setCreatedAt(LocalDateTime.now());
        couponSet.setImage(generateAvatarLink(String.valueOf(request.getPercent())));
        if (request.getStartAt() == null) {
            couponSet.setStartAt(LocalDateTime.now());
        } else {
            couponSet.setStartAt(request.getStartAt());
        }
                
        couponSet.setCoupons(new ArrayList<>());
        couponSet.addCoupon(request.getQuantity());

        couponSetRepository.save(couponSet); // save couponSet to database, this will cascade save all coupons
    }


    @Override
    public CouponSet findById(Long couponSetId) {
        
        
        return couponSetRepository.findById(couponSetId).orElseThrow(() -> new NotFoundException("Coupon set not found"));
    }

    @Override
    public void save(CouponSet couponSet) {
        couponSetRepository.save(couponSet);
    }

    @Override
    public void deleteById(CouponSet couponSet) {
        couponSetRepository.delete(couponSet);
    }

    @Override
    public Page<CouponSet> findAllByStore(Store store, Pageable pageable) {


        return couponSetRepository.findAllByStore(store, pageable);
    }
}
