package com.example.ecommerce.service.service;

import com.example.ecommerce.domain.Promotion;
import com.example.ecommerce.dto.request.promotion.CreateVoucherRequest;
import com.example.ecommerce.dto.request.promotion.UpdatePromotionRequest;
import com.example.ecommerce.dto.response.Response;
import org.springframework.http.ResponseEntity;

public interface PromotionService {

    ResponseEntity<Response> createVoucher(CreateVoucherRequest request);
    ResponseEntity<Response> deleteVoucher(Long promotionId);
    ResponseEntity<Response> updateVoucher(UpdatePromotionRequest updatePromotionRequest);
    ResponseEntity<Response> getPromotionById(Long promotionId);
    ResponseEntity<Response> getAllPromotions(Integer page, Integer elementsPerPage, String status, String filter, String sortBy);
    ResponseEntity<Response> getPromotionByCode(String code);

    void save(Promotion promotion);

}
