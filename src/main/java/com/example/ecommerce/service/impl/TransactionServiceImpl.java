package com.example.ecommerce.service.impl;

import com.example.ecommerce.domain.Transaction;
import com.example.ecommerce.domain.Transaction.TransactionType;
import com.example.ecommerce.dto.response.PageResponse;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.repository.TransactionRepository;
import com.example.ecommerce.service.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.example.ecommerce.domain.Transaction.TransactionType.*;
import static com.example.ecommerce.domain.Transaction.TransactionType.IN;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public ResponseEntity<Response> getAllTransactions(Long userId, Integer pageNumber, Integer elementsPerPage, String status, String filter, String sortBy) {

        Pageable pageable = PageRequest.of(pageNumber, elementsPerPage, Sort.Direction.valueOf(sortBy.toUpperCase()), filter);
        Page<Transaction> page;

        if (status.equalsIgnoreCase("ALL")) {
            page = transactionRepository.findAllByUser_Id(userId, pageable);
        } else {

            if (status.equalsIgnoreCase("IN")) {
                page = transactionRepository.findAllByUser_IdAndTransactionType(userId, IN, pageable);
            } else {
                page = transactionRepository.findAllByUser_IdAndTransactionType(userId, OUT, pageable);
            }
        }


        PageResponse pageResponse = PageResponse.builder()
                .content(page.getContent())
                .totalPages(page.getTotalPages())
                .size(page.getSize())
                .pageNumber(page.getNumber())
                .build();

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get all transactions successfully!")
                .data(pageResponse)
                .build());

    }
}
