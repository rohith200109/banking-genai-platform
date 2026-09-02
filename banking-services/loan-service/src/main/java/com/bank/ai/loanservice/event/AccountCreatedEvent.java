package com.bank.ai.loanservice.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountCreatedEvent(

        String eventId,

        String eventType,

        Long accountId,

        Long customerId,

        String accountNumber,

        String accountType,

        BigDecimal initialBalance,

        String status,

        LocalDateTime timestamp
) {
}