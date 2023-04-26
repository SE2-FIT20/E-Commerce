package com.example.ecommerce.service.service;

import com.example.ecommerce.domain.Transaction;
import com.example.ecommerce.dto.response.Response;
import org.springframework.http.ResponseEntity;

public interface TransactionService {
    void save(Transaction transaction);

    ResponseEntity<Response> getAllTransactions(Long id, Integer page, Integer elementsPerPage, String status, String filter, String sortBy);

}
