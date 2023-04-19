package com.example.ecommerce.service.impl;

import com.example.ecommerce.domain.*;
import com.example.ecommerce.dto.request.promotion.CreatePromotionRequest;
import com.example.ecommerce.dto.request.promotion.UpdatePromotionRequest;
import com.example.ecommerce.dto.response.PageResponse;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.CouponRepository;
import com.example.ecommerce.repository.PromotionRepository;
import com.example.ecommerce.repository.VoucherRepository;
import com.example.ecommerce.service.service.CouponSetService;
import com.example.ecommerce.service.service.PromotionService;
import com.example.ecommerce.service.service.VoucherSetService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PromotionServiceImpl implements PromotionService {


    private final PromotionRepository promotionRepository;
    private final VoucherRepository voucherRepository;
    private final VoucherSetService voucherSetService;
    private final CouponRepository couponRepository;
    private final CouponSetService couponSetService;
    private final StoreService storeService;
//    private final StoreService storeService;
    @Override
    public ResponseEntity<Response> createVoucherSet(CreatePromotionRequest request) {

        voucherSetService.createVoucherSet(request);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Create voucher set successfully")
                .data(null)
                .build());
    }



    @Override
    public ResponseEntity<Response> getAllVoucherSets(Integer page, Integer elementsPerPage, String filter, String sortBy) {
        Pageable pageable = PageRequest.of(page, elementsPerPage, Sort.Direction.valueOf(sortBy.toUpperCase()), filter);

        Page<VoucherSet> vouchers = voucherSetService.findAll(pageable);

        PageResponse pageResponse = PageResponse.builder()
                .totalPages(vouchers.getTotalPages())
                .content(vouchers.getContent())
                .pageNumber(vouchers.getNumber())
                .size(vouchers.getSize())
                .build();
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get all vouchers successfully")
                .data(pageResponse)
                .build());
    }

    @Override
    public ResponseEntity<Response> deleteVoucherSet(Long id) {
        VoucherSet voucherSet = voucherSetService.findById(id);
        voucherSetService.deleteVoucherSet(voucherSet);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Delete voucher set successfully")
                .build());
    }

    @Override
    public ResponseEntity<Response> addVoucherToSet(Long voucherSetId, int quantity) {
        VoucherSet voucherSet = voucherSetService.findById(voucherSetId);
        voucherSet.addVoucher(quantity);

        voucherSetService.save(voucherSet);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Add voucher to set successfully")
                .build());
    }

    @Override
    public ResponseEntity<Response> subtractVoucherToSet(Long voucherSetId, int quantity) {
        VoucherSet voucherSet = voucherSetService.findById(voucherSetId);
        voucherSet.subtractVoucher(quantity);
        voucherSetService.save(voucherSet);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Subtract voucher to set successfully")
                .build());
    }

    @Override
    public ResponseEntity<Response> getAllVouchersInSet(Long voucherSetId, Integer pageNumber, Integer elementsPerPage, String status, String filter, String sortBy) {

        VoucherSet voucherSet = voucherSetService.findById(voucherSetId);
        Pageable pageable = PageRequest.of(pageNumber, elementsPerPage, Sort.Direction.valueOf(sortBy.toUpperCase()), filter);

        Voucher voucher = new Voucher();
        voucher.setVoucherSet(voucherSet);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        if (!status.equalsIgnoreCase("all")) {
            if (status.equalsIgnoreCase("used")) {
                voucher.setUsed(true);
            } else {
                voucher.setUsed(false);
            }
        } else {
            matcher = matcher.withIgnorePaths("used");
        }
        Page<Voucher> vouchers = voucherRepository.findAll(Example.of(voucher, matcher), pageable);
        PageResponse pageResponse = PageResponse.builder()
                .totalPages(vouchers.getTotalPages())
                .content(vouchers.getContent())
                .pageNumber(vouchers.getNumber())
                .size(vouchers.getSize())
                .build();

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get all vouchers in set successfully")
                .data(pageResponse)
                .build());
    }

    @Override
    public ResponseEntity<Response> deleteVoucherById(Long id) {
        Voucher voucher = findVoucherById(id); // check voucher exist
        voucherRepository.delete(voucher);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Delete voucher successfully")
                .build());
    }

    @Override
    public ResponseEntity<Response> createCouponSet(Long storeId, CreatePromotionRequest request) {
        Store store = storeService.findStoreById(storeId);
        couponSetService.createCouponSet(store, request);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Create coupon set successfully")
                .data(null)
                .build());
    }

    @Override
    public ResponseEntity<Response> getAllCouponSetsOfStore(Long id, Integer pageNumber, Integer elementsPerPage, String filter, String sortBy) {
        Store store = storeService.findStoreById(id);
        Pageable pageable = PageRequest.of(pageNumber, elementsPerPage, Sort.Direction.valueOf(sortBy.toUpperCase()), filter);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Coupon coupon = new Coupon();
        coupon.setStore(store);

        Page<Coupon> page =  couponRepository.findAll(Example.of(coupon, matcher), pageable);

        PageResponse pageResponse = PageResponse.builder()
                .totalPages(page.getTotalPages())
                .content(page.getContent())
                .pageNumber(page.getNumber())
                .size(page.getSize())
                .build();

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get all coupon sets of store successfully")
                .data(pageResponse)
                .build());
    }

    @Override
    public ResponseEntity<Response> addCouponToCouponSet(Long storeId, Long couponSetId, int quantity) {
        CouponSet couponSet = couponSetService.findById(couponSetId);
        if (!couponSet.getStore().getId().equals(storeId)) {
            throw new IllegalArgumentException("Coupon set does not belong to this store");
        }
        couponSet.addCoupon(quantity);
        couponSetService.save(couponSet);

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Add coupon to coupon set successfully")
                .build());
    }

    @Override
    public ResponseEntity<Response> subtractCouponFromCouponSet(Long storeId, Long couponSetId, int quantity) {
        CouponSet couponSet = couponSetService.findById(couponSetId);
        if (!couponSet.getStore().getId().equals(storeId)) {
            throw new IllegalArgumentException("Coupon set does not belong to this store");
        }
        couponSet.subtractCoupon(quantity);
        couponSetService.save(couponSet);

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Subtract coupon from coupon set successfully")
                .build());
    }

    @Override
    public ResponseEntity<Response> getAllCouponsOfCouponSet(Long id, Long couponSetId, Integer page, Integer elementsPerPage, String status, String filter, String sortBy) {

        Store store = storeService.findStoreById(id);
        CouponSet couponSet = couponSetService.findById(couponSetId);
        if (!couponSet.getStore().getId().equals(store.getId())) {
            throw new IllegalArgumentException("Coupon set does not belong to this store");
        }

        Pageable pageable = PageRequest.of(page, elementsPerPage, Sort.Direction.valueOf(sortBy.toUpperCase()), filter);

        Coupon coupon = new Coupon();
        coupon.setCouponSet(couponSet);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        if (!status.equalsIgnoreCase("all")) {
            if (status.equalsIgnoreCase("used")) {
                coupon.setUsed(true);
            } else {
                coupon.setUsed(false);
            }
        } else {
            matcher = matcher.withIgnorePaths("used");
        }

        Page<Coupon> coupons = couponRepository.findAll(Example.of(coupon, matcher), pageable);
        PageResponse pageResponse = PageResponse.builder()
                .totalPages(coupons.getTotalPages())
                .content(coupons.getContent())
                .pageNumber(coupons.getNumber())
                .size(coupons.getSize())
                .build();

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get all coupons of coupon set successfully")
                .data(pageResponse)
                .build());
    }

    @Override
    public ResponseEntity<Response> deleteCouponById(Long storeId, Long couponId) {

        Store store = storeService.findStoreById(storeId);
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new NotFoundException("Coupon not found"));
        if (!coupon.getCouponSet().getStore().getId().equals(store.getId())) {
            throw new IllegalArgumentException("Coupon does not belong to this store");
        }
        couponRepository.delete(coupon);

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Delete coupon set successfully")
                .build());
    }

    @Override
    public ResponseEntity<Response> deleteCouponSetById(Long id, Long couponSetId) {

        Store store = storeService.findStoreById(id);
        CouponSet couponSet = couponSetService.findById(couponSetId);
        if (!couponSet.getStore().getId().equals(store.getId())) {
            throw new IllegalArgumentException("Coupon set does not belong to this store");
        }
        couponSetService.deleteById(couponSet);

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Delete coupon set successfully")
                .build());
    }


    private Voucher findVoucherById(Long promotionId) {
        return voucherRepository.findById(promotionId)
                .orElseThrow(() -> new NotFoundException("Promotion not found"));
    }

    @Override
    public ResponseEntity<Response> getVoucherByCode(String code) {
        Promotion promotion = promotionRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Promotion not found"));

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get promotion successfully")
                .data(promotion)
                .build());
    }

    @Override
    public void save(Promotion promotion) {
        promotionRepository.save(promotion);
    }


}
