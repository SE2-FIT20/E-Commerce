package com.example.ecommerce.service.impl;

import com.example.ecommerce.domain.Promotion;
import com.example.ecommerce.domain.Store;
import com.example.ecommerce.dto.request.promotion.CreatePromotionRequest;
import com.example.ecommerce.dto.request.promotion.UpdatePromotionRequest;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.PromotionRepository;
import com.example.ecommerce.service.service.PromotionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;
//    private final StoreService storeService;
    @Override
    public ResponseEntity<Response> createPromotion(CreatePromotionRequest request) {
        //TODO: admin  use this method
        Promotion promotion = Promotion.builder()
                .name(request.getName())
                .description(request.getDescription())
                .percent(request.getPercent())
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
        //TODO: admin  use this method

        promotion.setName(updatePromotionRequest.getName());
        promotion.setPercent(updatePromotionRequest.getPercent());
        promotion.setDescription(updatePromotionRequest.getDescription());



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

    @Override
    public void save(Promotion promotion) {
        promotionRepository.save(promotion);
    }
}
