package com.example.ecommerce.service.service;

import com.example.ecommerce.domain.Promotion;
import com.example.ecommerce.dto.request.promotion.CreatePromotionRequest;
import com.example.ecommerce.dto.request.promotion.UpdatePromotionRequest;
import com.example.ecommerce.dto.response.Response;
import org.springframework.http.ResponseEntity;

public interface PromotionService {

    ResponseEntity<Response> createPromotion(CreatePromotionRequest request);
    ResponseEntity<Response> deletePromotion(Long promotionId);
    ResponseEntity<Response> updatePromotion(UpdatePromotionRequest updatePromotionRequest);
    ResponseEntity<Response> getPromotionById(Long promotionId);
    ResponseEntity<Response> getAllPromotions(Integer page, Integer elementsPerPage, String status, String filter, String sortBy);
    ResponseEntity<Response> getPromotionByCode(String code);

    void save(Promotion promotion);

}
