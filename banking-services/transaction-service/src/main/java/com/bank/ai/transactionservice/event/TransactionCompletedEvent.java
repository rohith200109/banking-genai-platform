package com.bank.ai.transactionservice.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bank.ai.transactionservice.entity.TransactionType;

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