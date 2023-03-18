package com.example.ecommerce.service.impl;

import com.example.ecommerce.domain.Promotion;
import com.example.ecommerce.dto.request.promotion.CreatePromotionRequest;
import com.example.ecommerce.dto.request.promotion.UpdatePromotionRequest;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.PromotionRepository;
import com.example.ecommerce.service.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;
    @Override
    public ResponseEntity<Response> createPromotion(CreatePromotionRequest request) {
        Promotion promotion = Promotion.builder()
                .name(request.getName())
                .percent(request.getPercent())
                .storeId(request.getStoreId())
                .isGlobal(request.isGlobal())
                .build();

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
    public ResponseEntity<Response> updatePromotion(UpdatePromotionRequest updatePromotionRequest) {
        Promotion promotion = findPromotionById(updatePromotionRequest.getPromotionId());

        promotion.setName(updatePromotionRequest.getName());
        promotion.setPercent(updatePromotionRequest.getPercent());
        promotion.setStoreId(updatePromotionRequest.getStoredId());
        promotion.setGlobal(updatePromotionRequest.isGlobal());

        promotionRepository.save(promotion);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Update promotion successfully")
                .data(null)
                .build());
    }

    private Promotion findPromotionById(Long promotionId) {
        return promotionRepository.findById(promotionId)
                .orElseThrow(() -> new NotFoundException("Promotion not found"));
    }

    @Override
    public ResponseEntity<Response> getPromotionById(Long promotionId) {
        Promotion promotion = findPromotionById(promotionId);
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
