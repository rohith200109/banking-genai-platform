package com.bank.ai.loanservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bank.ai.loanservice.entity.LoanStatus;
import com.bank.ai.loanservice.entity.LoanType;

public record LoanResponse(

        Long loanId,
        Long customerId,

        Long accountId,

        String loanNumber,

        LoanType loanType,

        BigDecimal principalAmount,

        BigDecimal outstandingAmount,

        BigDecimal interestRate,

        Integer tenureMonths,

        LoanStatus status,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}