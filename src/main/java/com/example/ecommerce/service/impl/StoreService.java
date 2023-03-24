package com.example.ecommerce.service.impl;

import com.example.ecommerce.domain.Product;
import com.example.ecommerce.domain.Store;
import com.example.ecommerce.domain.User;
import com.example.ecommerce.dto.request.product.CreateProductRequest;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.dto.response.StoreInformation;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.StoreRepository;
import com.example.ecommerce.service.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final ProductService productService;
    public void save(Store store) {
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

    public Store findStoreById(Long id) {
        return storeRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Store not found for id: " + id));
    }

    public ResponseEntity<Response> storeService(Long id) {
        Store store = findStoreById(id);
        List<Product> products = store.getInventory();
//        for (Product product : products) {
////            System.out.println(product.getName());
//            System.out.println(product.getStore());
//        }
        Response response = Response.builder()
                .status(200)
                .message("Get Store information successfully")
                .data(store.getInventory())
                .build();
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Response> createProduct(Long id, CreateProductRequest request) {

        Store store = findStoreById(id);
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .category(request.getCategory())
                .store(store)
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .images(request.getImages())
                .build();

        productService.save(product); // save product to database, since product is the ownind side, the store will have this product in the inventory
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Create product successfully")
                .data(null)
                .build());
    }
}
