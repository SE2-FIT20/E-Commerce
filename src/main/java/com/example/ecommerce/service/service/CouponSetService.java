package com.example.ecommerce.service.service;

import com.example.ecommerce.domain.Coupon;
import com.example.ecommerce.domain.CouponSet;
import com.example.ecommerce.domain.Store;
import com.example.ecommerce.dto.request.promotion.CreatePromotionRequest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CouponSetService {
    void createCouponSet(Store store, CreatePromotionRequest request);


    CouponSet findById(Long couponSetId);

    void save(CouponSet couponSet);

    void deleteById(CouponSet couponSet);

}
