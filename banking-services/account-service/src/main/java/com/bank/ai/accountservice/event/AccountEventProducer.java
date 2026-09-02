package com.bank.ai.accountservice.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccountEventProducer {

    private static final String ACCOUNT_EVENTS_TOPIC =
            "account-events";

    private final KafkaTemplate<String, AccountCreatedEvent>
            kafkaTemplate;

    public void publishAccountCreated(
            AccountCreatedEvent event) {

        log.info(
                "Publishing ACCOUNT_CREATED event. accountId={}, eventId={}",
                event.accountId(),
                event.eventId()
        );

        kafkaTemplate.send(
                ACCOUNT_EVENTS_TOPIC,
                String.valueOf(event.accountId()),
                event
        );
    }
}