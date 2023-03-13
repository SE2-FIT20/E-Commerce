package com.example.ecommerce.service.impl;

import com.example.ecommerce.domain.Promotion;
import com.example.ecommerce.dto.request.promotion.UpdatePromotionRequest;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.repository.PromotionRepository;
import com.example.ecommerce.service.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;
    @Override
    public ResponseEntity<Response> createPromotion(Promotion promotion) {
        promotionRepository.save(promotion);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Create promotion successfully")
                .data(promotion)
                .build());
    }

    @Override
    public ResponseEntity<Response> deletePromotion(Long promotionId) {
        promotionRepository.deleteById(promotionId);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Delete promotion successfully")
                .build());
    }

    @Override
    public ResponseEntity<Response> updatePromotion(Long promotionId, UpdatePromotionRequest updatePromotionRequest) {
        Promotion updatePromotion = promotionRepository.getById(promotionId);
        updatePromotion.setName(updatePromotionRequest.getName());
        updatePromotion.setPercent(updatePromotionRequest.getPercent());
        updatePromotion.setStoreId(updatePromotionRequest.getStoredId());
        updatePromotion.setGlobal(updatePromotionRequest.isGlobal());
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Update promotion successfully")
                .data(updatePromotion)
                .build());
    }

    @Override
    public ResponseEntity<Response> getPromotionById(Long promotionId) {
        Promotion promotion = promotionRepository.getById(promotionId);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get promotion successfully")
                .data(promotion)
                .build());
    }

    @Override
    public ResponseEntity<Response> getAllPromotions() {
        List<Promotion> promotions = promotionRepository.findAll();
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get all promotions successfully")
                .data(promotions)
                .build());
    }
}
