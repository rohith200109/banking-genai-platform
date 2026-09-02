package com.bank.ai.transactionservice.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bank.ai.transactionservice.dto.TransactionCreateRequest;
import com.bank.ai.transactionservice.dto.TransactionResponse;
import com.bank.ai.transactionservice.service.TransactionService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponse>
    createTransaction(
            @Valid @RequestBody
            TransactionCreateRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        transactionService
                                .createTransaction(request)
                );
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionResponse>
    getTransaction(
            @PathVariable Long transactionId) {

        return ResponseEntity.ok(
                transactionService
                        .getTransactionById(transactionId)
        );
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<Page<TransactionResponse>>
    getAccountTransactions(
            @PathVariable Long accountId,
            @RequestParam(defaultValue = "0")
            int page,
            @RequestParam(defaultValue = "10")
            int size) {

        return ResponseEntity.ok(
                transactionService
                        .getTransactionsByAccount(
                                accountId,
                                page,
                                size
                        )
        );
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Page<TransactionResponse>>
    getCustomerTransactions(
            @PathVariable Long customerId,
            @RequestParam(defaultValue = "0")
            int page,
            @RequestParam(defaultValue = "10")
            int size) {

        return ResponseEntity.ok(
                transactionService
                        .getTransactionsByCustomer(
                                customerId,
                                page,
                                size
                        )
        );
    }
}