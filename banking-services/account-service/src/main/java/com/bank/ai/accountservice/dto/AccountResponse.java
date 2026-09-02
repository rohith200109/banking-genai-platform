package com.bank.ai.accountservice.dto;

import com.bank.ai.accountservice.entity.AccountStatus;
import com.bank.ai.accountservice.entity.AccountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountResponse(

        Long accountId,

        Long customerId,

        String accountNumber,

        AccountType accountType,

        BigDecimal balance,

        AccountStatus status,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}