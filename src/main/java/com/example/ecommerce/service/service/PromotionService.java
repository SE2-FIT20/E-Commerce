package com.example.ecommerce.service.service;

import com.example.ecommerce.domain.Promotion;
import com.example.ecommerce.dto.request.promotion.CreatePromotionRequest;
import com.example.ecommerce.dto.request.promotion.UpdatePromotionRequest;
import com.example.ecommerce.dto.response.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PromotionService {

    ResponseEntity<Response> createVoucherSet(CreatePromotionRequest request);
    ResponseEntity<Response> getVoucherByCode(String code);

    void save(Promotion promotion);
    boolean checkIfPromotionAndThrowExceptionIfUsable(Promotion promotion);

    ResponseEntity<Response> getAllVoucherSets(Integer page, Integer elementsPerPage, String filter, String sortBy);


    ResponseEntity<Response> deleteVoucherSet(Long id);

    ResponseEntity<Response> addVoucherToSet(Long voucherSetId, int quantity);

    ResponseEntity<Response> subtractVoucherToSet(Long voucherSetId, int quantity);

    ResponseEntity<Response> getAllVouchersInSet(Long voucherSetId, Integer pageNumber, Integer elementsPerPage, String status, String filter, String sortBy);

    ResponseEntity<Response> deleteVoucherById(Long id);

    ResponseEntity<Response> createCouponSet(Long storeId, CreatePromotionRequest request);

    ResponseEntity<Response> getAllCouponSetsOfStore(Long storeId, Integer page, Integer elementsPerPage, String filter, String sortBy);

    ResponseEntity<Response> addCouponToCouponSet(Long storeId, Long couponSetId, int quantity);

    ResponseEntity<Response> subtractCouponFromCouponSet(Long storeId, Long couponSetId, int quantity);

    ResponseEntity<Response> getAllCouponsOfCouponSet(Long id, Long couponSetId, Integer page, Integer elementsPerPage, String status, String filter, String sortBy);

    ResponseEntity<Response> deleteCouponById(Long id, Long couponSetId);

    ResponseEntity<Response> deleteCouponSetById(Long id, Long couponSetId);

    ResponseEntity<Response> getVouchersCoupons(Long id);

    ResponseEntity<Response> updateVoucherSet(Long voucherSetId, UpdatePromotionRequest promotionRequest);

    ResponseEntity<Response> updateCouponSet(Long storeId, Long couponSetId, UpdatePromotionRequest request);

    ResponseEntity<Response> getMiniGameVouchers(String filter, String sortBy);

    ResponseEntity<Response> getCouponSetsByStore(Long storeId, Integer page, Integer elementsPerPage, String filter, String sortBy);

    ResponseEntity<Response> saveVoucherOrCoupon(Long customerId, Long promotionId);

    ResponseEntity<Response> addVouchersCouponsToCart(Long customerId, List<Long> promotionId);

    ResponseEntity<Response> getVoucherSetById(Long id);

    ResponseEntity<Response> getCouponSetById(Long storeId, Long couponSetId);

    ResponseEntity<Response> removeVouchersCouponsToCart(Long customerId, Long promotionId);

    ResponseEntity<Response> removeAllVouchersCouponsToCart(Long id);
}
