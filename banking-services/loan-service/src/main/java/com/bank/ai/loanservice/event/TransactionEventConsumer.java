package com.bank.ai.loanservice.event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TransactionEventConsumer {

    @KafkaListener(
            topics = "transaction-events",
            groupId = "loan-service-group",
            containerFactory = "transactionKafkaListenerContainerFactory"
    )
    public void consumeTransactionCompleted(
            TransactionCompletedEvent event) {

        log.info("========== TRANSACTION EVENT RECEIVED ==========");

        log.info(
                "eventId={}, transactionId={}, accountId={}, " +
                "customerId={}, transactionType={}, amount={}, " +
                "balanceAfter={}",
                event.eventId(),
                event.transactionId(),
                event.accountId(),
                event.customerId(),
                event.transactionType(),
                event.amount(),
                event.balanceAfter()
        );
    }
}
