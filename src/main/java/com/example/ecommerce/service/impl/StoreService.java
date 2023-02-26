package com.example.ecommerce.service.impl;

import com.example.ecommerce.domain.Store;
import com.example.ecommerce.domain.User;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.dto.response.StoreInformation;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

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

    private Store findStoreById(Long id) {
        return storeRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Store not found for id: " + id));
    }
}
