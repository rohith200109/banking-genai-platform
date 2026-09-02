package com.bank.ai.transactionservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bank.ai.transactionservice.entity.TransactionStatus;
import com.bank.ai.transactionservice.entity.TransactionType;

public record TransactionResponse(

        Long transactionId,

        String transactionReference,

        Long accountId,

        Long customerId,

        TransactionType transactionType,

        BigDecimal amount,

        BigDecimal balanceBefore,

        BigDecimal balanceAfter,

        TransactionStatus status,

        String description,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}