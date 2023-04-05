package com.example.ecommerce.service.impl;

import com.example.ecommerce.domain.*;
import com.example.ecommerce.dto.request.order.UpdateOrderRequest;
import com.example.ecommerce.dto.request.product.CreateProductRequest;
import com.example.ecommerce.dto.request.product.UpdateProductRequest;
import com.example.ecommerce.dto.request.promotion.CreatePromotionRequest;
import com.example.ecommerce.dto.request.promotion.UpdatePromotionRequest;
import com.example.ecommerce.dto.response.PageResponse;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.dto.response.StoreInformation;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.PromotionRepository;
import com.example.ecommerce.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreService {

    @Value("${default.elementPerPage}")
    private String defaultElementPerPage;

    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PromotionRepository promotionRepository;
    @Autowired
    private OrderRepository orderRepository;

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
                .createdAt(LocalDateTime.now())
                .build();

        productRepository.save(product); // save product to database, since product is the ownind sstoreIde, the store will have this product in the inventory
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
        } else {
            inventory.removeIf(product -> product.getId().equals(productId));
        }
        productRepository.deleteById(productId); // delete product from database (product is the owning side
        storeRepository.save(store); // save store to database (store is the inverse side)

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Delete product successfully")
                .data(null)
                .build());

    }

    public ResponseEntity<Response> getAllOrders(Long storeId, Integer pageNumber, Integer elementsPerPage, String status, String filter, String sortBy, LocalDateTime from, LocalDateTime to) {
        Store store = findStoreById(storeId);

        Pageable pageable = PageRequest.of(pageNumber, elementsPerPage, Sort.by(Sort.Direction.valueOf(sortBy.toUpperCase()), filter));
    //TODO; check this one
        Page<Order> page;
        if (status.equals("ALL")) {
            page = orderRepository.findAllByStoreAndCreatedAtBetween(store, from, to, pageable);
        } else {
            Order.OrderStatus orderStatus = Order.OrderStatus.fromString(status.toUpperCase());
            page = orderRepository.findAllByStoreAndStatusAndCreatedAtBetween(store, orderStatus, from, to, pageable);
        }

        PageResponse pageResponse = PageResponse.builder()
                .content(page.getContent())
                .totalPages(page.getTotalPages())
                .size(page.getSize())
                .pageNumber(page.getNumber())
                .build();

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get all orders successfully")
                .data(pageResponse)
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

        promotionRepository.save(promotion); // save promotion to database, since promotion is the owning side, the store will have this promotion in the promotions

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
                promotionRepository.save(promotion);
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
                productRepository.save(product);
            }
        }

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Update product successfully")
                .data(null)
                .build());
    }

    public List<Store> searchStore(String keyword) {
        return storeRepository.findByNameContainingIgnoreCase(keyword);
    }

    public ResponseEntity<Response> getOrderById(Long id, Long orderId) {
        Store store = findStoreById(id);
        List<Order> orders = store.getOrders();

        boolean isExist = orders.stream().anyMatch(order -> order.getId().equals(orderId));
        if (!isExist) {
            throw new NotFoundException("Order not found for orderId: " + orderId);
        }

        Order order = orders.stream().filter(o -> o.getId().equals(orderId)).findFirst().get();

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get order successfully")
                .data(order)
                .build());
    }
//    public ResponseEntity<Response> getProductByStore(Integer pageNumber, Long storeId) {
//        Store store = findStoreById(storeId);
//        Page<Product> page = productService.getProductOfStore(pageNumber, store);
//        PageResponse pageResponse = PageResponse.builder()
//                .content(page.getContent())
//                .totalPages(page.getTotalPages())
//                .pageNumber(pageNumber)
//                .build();
//        return ResponseEntity.ok(Response.builder()
//                .status(200)
//                .message("Get all products successfully")
//                .data(pageResponse)
//                .build());
//    }

    public ResponseEntity<Response> getProductByStoreFilterByReview(Long storeId) {
        Store store = findStoreById(storeId);
        List<Product> products = store.getInventory();

        List<Product> productsFilterByReview = products.stream()
                .filter(product -> product.getReviews().size() > 0)
                .collect(Collectors.toList());

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get all products successfully")
                .data(productsFilterByReview)
                .build());
    }

    public ResponseEntity<Response> getProductByStoreSortByPriceAsc(Long storeId) {
        Store store = findStoreById(storeId);
        List<Product> products = store.getInventory();

        List<Product> productsSortByPrice = products.stream()
                .sorted(Comparator.comparing(Product::getPrice))
                .collect(Collectors.toList());

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get all products successfully")
                .data(productsSortByPrice)
                .build());
    }

    public ResponseEntity<Response> getProductByStoreSortByPriceDesc(Long storeId) {
        Store store = findStoreById(storeId);
        List<Product> products = store.getInventory();

        List<Product> productsSortByPrice = products.stream()
                .sorted(Comparator.comparing(Product::getPrice).reversed())
                .collect(Collectors.toList());

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get all products successfully")
                .data(productsSortByPrice)
                .build());
    }

    public ResponseEntity<Response> getProductsByStatus(Long id, Integer pageNumber, Integer elementsPerPage, String status, String sortBy) {
        Store store = findStoreById(id);
        Page<Product> pageProduct;
        Pageable pageable = PageRequest.of(pageNumber, elementsPerPage);

        if (status.toUpperCase().equals(Product.Status.AVAILABLE.name())){
             pageable = PageRequest.of(pageNumber, elementsPerPage, Sort.Direction.valueOf(sortBy.toUpperCase()), "price");
            pageProduct = productRepository.findAllByStoreAndQuantityGreaterThan(store, 0, pageable);
        } else if (status.toUpperCase().equals(Product.Status.SOLD_OUT.name())){
            pageProduct = productRepository.findAllByStoreAndQuantityEquals(store, 0, pageable);
        } else {
            pageProduct = productRepository.findAllByStore(store, pageable);
        }


        PageResponse pageResponse = PageResponse.builder()
                .content(pageProduct.getContent())
                .totalPages(pageProduct.getTotalPages())
                .pageNumber(pageNumber)
                .size(pageProduct.getSize())
                .build();

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get all products successfully")
                .data(pageResponse)
                .build());
    }
}
