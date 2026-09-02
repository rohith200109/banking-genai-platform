package com.bank.ai.loanservice.dto;

import java.math.BigDecimal;

import com.bank.ai.loanservice.entity.LoanType;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LoanCreateRequest(

        @NotNull(message = "Customer ID is required")
        Long customerId,

        @NotNull(message = "Account ID is required")
        Long accountId,

        @NotNull(message = "Loan type is required")
        LoanType loanType,

        @NotNull(message = "Principal amount is required")
        @DecimalMin(
                value = "0.01",
                message = "Principal amount must be greater than zero"
        )
        BigDecimal principalAmount,

        @NotNull(message = "Interest rate is required")
        @DecimalMin(
                value = "0.0",
                message = "Interest rate cannot be negative"
        )
        BigDecimal interestRate,

        @NotNull(message = "Tenure is required")
        @Positive(message = "Tenure must be greater than zero")
        Integer tenureMonths
) {
}