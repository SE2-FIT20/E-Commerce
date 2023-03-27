package com.example.ecommerce.service.impl;

import com.example.ecommerce.domain.*;
import com.example.ecommerce.dto.request.order.UpdateOrderRequest;
import com.example.ecommerce.dto.request.product.CreateProductRequest;
import com.example.ecommerce.dto.request.product.UpdateProductRequest;
import com.example.ecommerce.dto.request.promotion.CreatePromotionRequest;
import com.example.ecommerce.dto.request.promotion.UpdatePromotionRequest;
import com.example.ecommerce.dto.response.ProductBriefInfo;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.dto.response.StoreInformation;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.StoreRepository;
import com.example.ecommerce.service.service.ProductService;
import com.example.ecommerce.service.service.PromotionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final ProductService productService;
    private final PromotionService promotionService;

    public void  save(Store store) {
        storeRepository.save(store);
    }

    public ResponseEntity<Response> getStoreInformationById(Long storeId) {
        Store store = findStoreById(storeId);

        StoreInformation storeInformation = new StoreInformation(store);
        Response response = Response.builder()
                .status(200)
                .message("Get Store information successfully")
                .data(storeInformation)
                .build();

        return ResponseEntity.ok(response);
    }

    public Store findStoreById(Long storeId) {
        return storeRepository
                .findById(storeId)
                .orElseThrow(() -> new NotFoundException("Store not found for storeId: " + storeId));
    }

    public ResponseEntity<Response> storeService(Long storeId) {
        Store store = findStoreById(storeId);
        List<ProductBriefInfo> productBriefInfos = ProductBriefInfo.from(store.getInventory());
        // reverse the list so that the newest product will be at the top
        Collections.reverse(productBriefInfos);

        Response response = Response.builder()
                .status(200)
                .message("Get Store information successfully")
                .data(productBriefInfos)
                .build();
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Response> createProduct(Long storeId, CreateProductRequest request) {

        Store store = findStoreById(storeId);
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .category(request.getCategory())
                .store(store)
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .images(request.getImages())
                .build();

        productService.save(product); // save product to database, since product is the ownind sstoreIde, the store will have this product in the inventory
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Create product successfully")
                .data(null)
                .build());
    }

    public ResponseEntity<Response> deleteProductById(Long storeId, Long productId) {
        Store store = findStoreById(storeId);
        List<Product> inventory = store.getInventory();

        boolean isExist = inventory.stream().anyMatch(product -> product.getId().equals(productId));
        if (!isExist) {
            throw new NotFoundException("Product not found for productId: " + productId);
        }

        productService.deleteProductById(productId);;

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Delete product successfully")
                .data(null)
                .build());

    }

    public ResponseEntity<Response> getAllOrder(Long storeId) {
        Store store = findStoreById(storeId);
        List<Order> orders = store.getOrders();
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get all orders successfully")
                .data(orders)
                .build()
        );
    }

    public ResponseEntity<Response> updateOrder(Long storeId, UpdateOrderRequest request) {
        Store store = findStoreById(storeId);
        List<Order> orders = store.getOrders();

        boolean isExist = orders.stream().anyMatch(order -> order.getId().equals(request.getOrderId()));
        if (!isExist) {
            throw new NotFoundException("Order not found for orderId: " + request.getOrderId());
        }

        for (Order order : orders) {
            if (order.getId().equals(request.getOrderId())) {
                order.setStatus(Order.OrderStatus.fromString(request.getStatus()));
            }
        }
        storeRepository.save(store);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Update order successfully")
                .data(null)
                .build());
    }

    public ResponseEntity<Response> getAllPromotions(Long storeId) {
        Store store = findStoreById(storeId);
        List<Promotion> promotions = store.getPromotions();

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get all promotions successfully")
                .data(promotions)
                .build());
    }

    public ResponseEntity<Response> createPromotion(Long storeId, CreatePromotionRequest promotionRequest) {
        Store store = findStoreById(storeId);
        Promotion promotion = Promotion.builder()
                .name(promotionRequest.getName())
                .description(promotionRequest.getDescription())
                .percent(promotionRequest.getPercent())
                .store(store)
                .build();

        promotionService.save(promotion); // save promotion to database, since promotion is the owning side, the store will have this promotion in the promotions

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Create promotion successfully")
                .data(null)
                .build());
    }

    public ResponseEntity<Response> updatePromotion(Long storeId, UpdatePromotionRequest updatePromotionRequest) {
        Store store = findStoreById(storeId);
        List<Promotion> promotions = store.getPromotions();

        boolean isExist = promotions.stream().anyMatch(promotion -> promotion.getId().equals(updatePromotionRequest.getPromotionId()));
        if (!isExist) {
            throw new NotFoundException("Promotion not found for promotionId: " + updatePromotionRequest.getPromotionId());
        }

        for (Promotion promotion : promotions) {
            if (promotion.getId().equals(updatePromotionRequest.getPromotionId())) {
                promotion.setName(updatePromotionRequest.getName());
                promotion.setDescription(updatePromotionRequest.getDescription());
                promotion.setPercent(updatePromotionRequest.getPercent());
                promotionService.save(promotion);
            }
        }


        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Update promotion successfully")
                .data(null)
                .build());
    }

    public ResponseEntity<Response> updateProduct(Long storeId, UpdateProductRequest request) {
        Store store = findStoreById(storeId);
        List<Product> inventory = store.getInventory();

        boolean isExist = inventory.stream().anyMatch(product -> product.getId().equals(request.getProductId()));
        if (!isExist) {
            throw new NotFoundException("Product not found for productId: " + request.getProductId());
        }

        for (Product product : inventory) {
            if (product.getId().equals(request.getProductId())) {
                product.setName(request.getName());
                product.setDescription(request.getDescription());
                product.setCategory(request.getCategory());
                product.setPrice(request.getPrice());
                product.setQuantity(request.getQuantity());
                product.setImages(request.getImages());
                productService.save(product);
            }
        }

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Update product successfully")
                .data(null)
                .build());
    }
}
