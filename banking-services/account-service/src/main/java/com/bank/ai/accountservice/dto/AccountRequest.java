package com.bank.ai.accountservice.dto;

import com.bank.ai.accountservice.entity.AccountType;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AccountRequest(

        @NotNull(message = "Customer ID is required")
        Long customerId,

        @NotNull(message = "Account type is required")
        AccountType accountType,

        @NotNull(message = "Initial balance is required")
        @DecimalMin(
                value = "0.00",
                inclusive = true,
                message = "Initial balance cannot be negative"
        )
        BigDecimal initialBalance
) {
}