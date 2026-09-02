package com.bank.ai.accountservice.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bank.ai.accountservice.entity.Account;
import com.bank.ai.accountservice.entity.ProcessedEvent;
import com.bank.ai.accountservice.repository.AccountRepository;
import com.bank.ai.accountservice.repository.ProcessedEventRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransactionEventConsumer {

    private final AccountRepository accountRepository;

    private final ProcessedEventRepository
            processedEventRepository;

@KafkaListener(
        topics = "transaction-events",
        groupId = "account-service-transaction-group",
        containerFactory = "transactionKafkaListenerContainerFactory"
)
@Transactional
public void consumeTransactionCompleted(
        TransactionCompletedEvent event) {

    log.info(
            "Received transaction event. " +
            "eventId={}, transactionId={}, accountId={}, " +
            "type={}, amount={}",
            event.eventId(),
            event.transactionId(),
            event.accountId(),
            event.transactionType(),
            event.amount()
    );

    /*
     * 1. Idempotency check
     */
    if (processedEventRepository
            .existsByEventId(event.eventId())) {

        log.info(
                "Event already processed. " +
                "Ignoring eventId={}",
                event.eventId()
        );

        return;
    }

    /*
     * 2. Lock account row
     */
    Account account =
            accountRepository
                    .findByIdForUpdate(event.accountId())
                    .orElseThrow(() ->
                            new IllegalStateException(
                                    "Account not found: "
                                            + event.accountId()
                            )
                    );

    /*
     * 3. Calculate new balance
     */
    BigDecimal currentBalance =
            account.getBalance();

    BigDecimal newBalance;

    switch (event.transactionType()) {

        case DEPOSIT -> {

            newBalance =
                    currentBalance.add(
                            event.amount()
                    );
        }

        case WITHDRAWAL -> {

            if (currentBalance.compareTo(
                    event.amount()) < 0) {

                throw new IllegalStateException(
                        "Insufficient account balance"
                );
            }

            newBalance =
                    currentBalance.subtract(
                            event.amount()
                    );
        }

        case TRANSFER -> {

            // We'll implement transfer properly later.
            throw new UnsupportedOperationException(
                    "TRANSFER processing is not implemented yet"
            );
        }

        default -> throw new IllegalStateException(
                "Unsupported transaction type: "
                        + event.transactionType()
        );
    }

    /*
     * 4. Update account balance
     */
    account.setBalance(newBalance);

    accountRepository.save(account);

    /*
     * 5. Mark event as processed
     */
    ProcessedEvent processedEvent =
            ProcessedEvent.builder()
                    .eventId(event.eventId())
                    .eventType(event.eventType())
                    .processedAt(LocalDateTime.now())
                    .build();

    processedEventRepository.save(
            processedEvent
    );

    log.info(
            "Account balance updated successfully. " +
            "accountId={}, oldBalance={}, " +
            "transactionAmount={}, newBalance={}",
            account.getAccountId(),
            currentBalance,
            event.amount(),
            newBalance
    );
}
}