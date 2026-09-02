package com.bank.ai.transactionservice.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransactionEventProducer {

    private static final String TRANSACTION_EVENTS_TOPIC =
            "transaction-events";

    private final KafkaTemplate<String, TransactionCompletedEvent>
            kafkaTemplate;

    public void publishTransactionCompleted(
            TransactionCompletedEvent event) {

        log.info(
                "Publishing TRANSACTION_COMPLETED event. " +
                "transactionId={}, accountId={}, eventId={}",
                event.transactionId(),
                event.accountId(),
                event.eventId()
        );

        kafkaTemplate.send(
                TRANSACTION_EVENTS_TOPIC,
                String.valueOf(event.accountId()),
                event
        );
    }
}