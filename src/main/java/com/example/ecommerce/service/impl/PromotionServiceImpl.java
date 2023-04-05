package com.example.ecommerce.service.impl;

import com.example.ecommerce.domain.Promotion;
import com.example.ecommerce.dto.request.promotion.CreatePromotionRequest;
import com.example.ecommerce.dto.request.promotion.UpdatePromotionRequest;
import com.example.ecommerce.dto.response.PageResponse;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.PromotionRepository;
import com.example.ecommerce.service.service.PromotionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.ecommerce.utils.Utils.generateAvatarLink;
import static com.example.ecommerce.utils.Utils.generateRandomString;

@AllArgsConstructor
@Service
public class PromotionServiceImpl implements PromotionService {


    private final PromotionRepository promotionRepository;
//    private final StoreService storeService;
    @Override
    public ResponseEntity<Response> createPromotion(CreatePromotionRequest request) {
        List<Promotion> promotions = new ArrayList<>();


        for (int i = 0; i < request.getQuantity(); i++) {
            String img = generateAvatarLink(String.valueOf(request.getPercent()));
            String code = generateRandomString();
            Promotion promotion = Promotion.builder()
                    .name(request.getName())
                    .description(request.getDescription())
                    .percent(request.getPercent())
                    .code(code)
                    .createdAt(LocalDateTime.now())
                    .expiredAt(request.getExpiredAt())
                    .image(img)
                    .isUsed(false)
                    .build();
            promotions.add(promotion);
        }


        promotionRepository.saveAll(promotions);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Create promotion successfully")
                .data(null)
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
    public ResponseEntity<Response> getAllPromotions(Integer pageNumber, Integer elementsPerPage, String status, String filter, String sortBy) {

        Pageable pageable = PageRequest.of(pageNumber, elementsPerPage, Sort.by(Sort.Direction.valueOf(sortBy.toUpperCase()), filter));
        Page<Promotion> page;

        if (status.toUpperCase().equals("ALL")) {
            page = promotionRepository.findAll(pageable);
        } else {
            if (status.toUpperCase().equals("USED")) {
                page = promotionRepository.findAllByIsUsed(true, pageable);
            } else {
                page = promotionRepository.findAllByIsUsed(false, pageable);
            }
        }

        PageResponse pageResponse = PageResponse.builder()
                .totalPages(page.getTotalPages())
                .content(page.getContent())
                .size(page.getSize())
                .build();

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get all promotions successfully")
                .data(pageResponse)
                .build());
    }

    @Override
    public ResponseEntity<Response> getPromotionByCode(String code) {
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
