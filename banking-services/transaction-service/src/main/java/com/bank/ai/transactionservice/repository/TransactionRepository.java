package com.bank.ai.transactionservice.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.ai.transactionservice.entity.Transaction;

public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByTransactionReference(
            String transactionReference
    );

    Page<Transaction> findByAccountId(
            Long accountId,
            Pageable pageable
    );

    Page<Transaction> findByCustomerId(
            Long customerId,
            Pageable pageable
    );

    boolean existsByTransactionReference(
            String transactionReference
    );
}