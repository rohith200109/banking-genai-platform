package com.bank.ai.loanservice.event;

import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AccountEventConsumer {

    @KafkaListener(
            topics = "account-events",
            groupId = "loan-service-group"
    )
    public void consumeAccountCreated(
            AccountCreatedEvent event) {

        log.info("========== ACCOUNT EVENT RECEIVED ==========");

        log.info(
                "accountId={}, customerId={}, accountNumber={}",
                event.accountId(),
                event.customerId(),
                event.accountNumber()
        );
    }
}