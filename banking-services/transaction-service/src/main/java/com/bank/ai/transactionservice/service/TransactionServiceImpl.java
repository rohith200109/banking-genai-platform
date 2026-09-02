package com.bank.ai.transactionservice.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.ai.transactionservice.dto.TransactionCreateRequest;
import com.bank.ai.transactionservice.dto.TransactionResponse;
import com.bank.ai.transactionservice.entity.Transaction;
import com.bank.ai.transactionservice.entity.TransactionStatus;
import com.bank.ai.transactionservice.exception.TransactionNotFoundException;
import com.bank.ai.transactionservice.repository.TransactionRepository;
import com.bank.ai.transactionservice.event.TransactionCompletedEvent;
import com.bank.ai.transactionservice.event.TransactionEventProducer;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImpl
        implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionEventProducer transactionEventProducer;

    @Override
    public TransactionResponse createTransaction(
            TransactionCreateRequest request) {

        Transaction transaction = Transaction.builder()
                .transactionReference(
                        generateTransactionReference()
                )
                .accountId(request.accountId())
                .customerId(request.customerId())
                .transactionType(request.transactionType())
                .amount(request.amount())
                .balanceBefore(request.balanceBefore())
                .balanceAfter(request.balanceAfter())
                .description(request.description())
                .status(TransactionStatus.COMPLETED)
                .build();

     Transaction saved =
        transactionRepository.save(transaction);

TransactionCompletedEvent event =
        new TransactionCompletedEvent(
                UUID.randomUUID().toString(),
                "TRANSACTION_COMPLETED",
                saved.getTransactionId(),
                saved.getTransactionReference(),
                saved.getAccountId(),
                saved.getCustomerId(),
                saved.getTransactionType(),
                saved.getAmount(),
                LocalDateTime.now()
        );
transactionEventProducer
        .publishTransactionCompleted(event);

return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public TransactionResponse getTransactionById(
            Long transactionId) {

        Transaction transaction =
                transactionRepository.findById(transactionId)
                        .orElseThrow(() ->
                                new TransactionNotFoundException(
                                        transactionId
                                ));

        return mapToResponse(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionResponse>
    getTransactionsByAccount(
            Long accountId,
            int page,
            int size) {

        Pageable pageable =
                PageRequest.of(page, size);

        return transactionRepository
                .findByAccountId(accountId, pageable)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionResponse>
    getTransactionsByCustomer(
            Long customerId,
            int page,
            int size) {

        Pageable pageable =
                PageRequest.of(page, size);

        return transactionRepository
                .findByCustomerId(customerId, pageable)
                .map(this::mapToResponse);
    }

    private String generateTransactionReference() {

        return "TXN-" +
                UUID.randomUUID()
                        .toString()
                        .replace("-", "")
                        .substring(0, 16)
                        .toUpperCase();
    }

    private TransactionResponse mapToResponse(
            Transaction transaction) {

        return new TransactionResponse(
                transaction.getTransactionId(),
                transaction.getTransactionReference(),
                transaction.getAccountId(),
                transaction.getCustomerId(),
                transaction.getTransactionType(),
                transaction.getAmount(),
                transaction.getBalanceBefore(),
                transaction.getBalanceAfter(),
                transaction.getStatus(),
                transaction.getDescription(),
                transaction.getCreatedAt(),
                transaction.getUpdatedAt()
        );
    }
}