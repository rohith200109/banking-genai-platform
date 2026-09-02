package com.bank.ai.accountservice.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bank.ai.accountservice.event.TransactionType;

public record TransactionCompletedEvent(

        String eventId,

        String eventType,

        Long transactionId,

        String transactionReference,

        Long accountId,

        Long customerId,

        TransactionType transactionType,

        BigDecimal amount,

        LocalDateTime timestamp
) {
}