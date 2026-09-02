package com.bank.ai.loanservice.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionCompletedEvent(

        String eventId,

        String eventType,

        Long transactionId,

        String transactionReference,

        Long accountId,

        Long customerId,

        String transactionType,

        BigDecimal amount,

        BigDecimal balanceBefore,

        BigDecimal balanceAfter,

        LocalDateTime timestamp
) {
}
