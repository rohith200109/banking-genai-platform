package com.bank.ai.accountservice.service;

import com.bank.ai.accountservice.dto.AccountRequest;
import com.bank.ai.accountservice.dto.AccountResponse;
import com.bank.ai.accountservice.dto.PageResponse;
import com.bank.ai.accountservice.entity.Account;
import com.bank.ai.accountservice.entity.AccountStatus;
import com.bank.ai.accountservice.event.AccountEventProducer;
import com.bank.ai.accountservice.exception.AccountNotFoundException;
import com.bank.ai.accountservice.exception.DuplicateAccountException;
import com.bank.ai.accountservice.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;
import com.bank.ai.accountservice.event.AccountCreatedEvent;
import com.bank.ai.accountservice.event.AccountEventProducer;

import java.util.UUID;
@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl
        implements AccountService {

    private final AccountRepository accountRepository;
private final AccountEventProducer accountEventProducer;
    @Override
    public AccountResponse createAccount(
            AccountRequest request) {

        String accountNumber = generateAccountNumber();

        if (accountRepository.existsByAccountNumber(
                accountNumber)) {

            throw new DuplicateAccountException(
                    "Account number already exists"
            );
        }

        Account account = Account.builder()
                .customerId(request.customerId())
                .accountNumber(accountNumber)
                .accountType(request.accountType())
                .balance(request.initialBalance())
                .status(AccountStatus.ACTIVE)
                .build();

        Account savedAccount =
                accountRepository.save(account);
AccountCreatedEvent event =
            new AccountCreatedEvent(
                    UUID.randomUUID().toString(),
                    "ACCOUNT_CREATED",
                    savedAccount.getAccountId(),
                    savedAccount.getCustomerId(),
                    savedAccount.getAccountNumber(),
                    savedAccount.getAccountType().name(),
                    savedAccount.getBalance(),
                    savedAccount.getStatus().name(),
                    savedAccount.getCreatedAt()
            );

    accountEventProducer.publishAccountCreated(event);
        return mapToResponse(savedAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponse getAccountById(
            Long accountId) {

        Account account =
                accountRepository.findById(accountId)
                        .orElseThrow(() ->
                                new AccountNotFoundException(
                                        "Account not found with id: "
                                                + accountId
                                )
                        );

        return mapToResponse(account);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<AccountResponse> getAllAccounts(
            Pageable pageable) {

        Page<Account> accountPage =
                accountRepository.findAll(pageable);

        var content = accountPage.getContent()
                .stream()
                .map(this::mapToResponse)
                .toList();

        return new PageResponse<>(
                content,
                accountPage.getNumber(),
                accountPage.getSize(),
                accountPage.getTotalElements(),
                accountPage.getTotalPages(),
                accountPage.isFirst(),
                accountPage.isLast()
        );
    }

    @Override
    public void closeAccount(Long accountId) {

        Account account =
                accountRepository.findById(accountId)
                        .orElseThrow(() ->
                                new AccountNotFoundException(
                                        "Account not found with id: "
                                                + accountId
                                )
                        );

        if (account.getBalance()
                .compareTo(BigDecimal.ZERO) != 0) {

            throw new IllegalStateException(
                    "Account cannot be closed while balance is not zero"
            );
        }

        account.setStatus(AccountStatus.CLOSED);
    }

    private String generateAccountNumber() {

        return "ACC"
                + UUID.randomUUID()
                        .toString()
                        .replace("-", "")
                        .substring(0, 12)
                        .toUpperCase();
    }

    private AccountResponse mapToResponse(
            Account account) {

        return new AccountResponse(
                account.getAccountId(),
                account.getCustomerId(),
                account.getAccountNumber(),
                account.getAccountType(),
                account.getBalance(),
                account.getStatus(),
                account.getCreatedAt(),
                account.getUpdatedAt()
        );
    }
}