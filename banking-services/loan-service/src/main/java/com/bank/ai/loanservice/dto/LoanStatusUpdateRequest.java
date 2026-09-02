package com.bank.ai.loanservice.dto;

import com.bank.ai.loanservice.entity.LoanStatus;

import jakarta.validation.constraints.NotNull;

public record LoanStatusUpdateRequest(

        @NotNull(message = "Loan status is required")
        LoanStatus status
) {
}