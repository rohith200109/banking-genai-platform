package com.bank.ai.transactionservice.dto;

import java.math.BigDecimal;

import com.bank.ai.transactionservice.entity.TransactionType;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TransactionCreateRequest(

        @NotNull(message = "Account ID is required")
        Long accountId,

        @NotNull(message = "Customer ID is required")
        Long customerId,

        @NotNull(message = "Transaction type is required")
        TransactionType transactionType,

        @NotNull(message = "Amount is required")
        @DecimalMin(
                value = "0.01",
                message = "Amount must be greater than zero"
        )
        BigDecimal amount,

        BigDecimal balanceBefore,

        @Positive(message = "Balance after must be positive")
        BigDecimal balanceAfter,

        String description
) {
}