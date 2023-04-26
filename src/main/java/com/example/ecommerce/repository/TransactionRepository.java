package com.example.ecommerce.repository;

import com.example.ecommerce.domain.Transaction;
import com.example.ecommerce.domain.Transaction.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Page<Transaction> findAllByUser_Id(Long id, org.springframework.data.domain.Pageable pageable);

    Page<Transaction> findAllByUser_IdAndTransactionType(Long userId, TransactionType transactionType, Pageable pageable);
}
