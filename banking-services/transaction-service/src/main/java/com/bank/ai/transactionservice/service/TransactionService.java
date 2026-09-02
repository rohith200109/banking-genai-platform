package com.bank.ai.transactionservice.service;

import org.springframework.data.domain.Page;

import com.bank.ai.transactionservice.dto.TransactionCreateRequest;
import com.bank.ai.transactionservice.dto.TransactionResponse;

public interface TransactionService {

    TransactionResponse createTransaction(
            TransactionCreateRequest request
    );

    TransactionResponse getTransactionById(
            Long transactionId
    );

    Page<TransactionResponse> getTransactionsByAccount(
            Long accountId,
            int page,
            int size
    );

    Page<TransactionResponse> getTransactionsByCustomer(
            Long customerId,
            int page,
            int size
    );
}