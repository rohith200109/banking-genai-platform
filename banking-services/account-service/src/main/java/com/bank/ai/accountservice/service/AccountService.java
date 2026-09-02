package com.bank.ai.accountservice.service;

import com.bank.ai.accountservice.dto.AccountRequest;
import com.bank.ai.accountservice.dto.AccountResponse;
import com.bank.ai.accountservice.dto.PageResponse;

import org.springframework.data.domain.Pageable;

public interface AccountService {

    AccountResponse createAccount(AccountRequest request);

    AccountResponse getAccountById(Long accountId);

    PageResponse<AccountResponse> getAllAccounts(
            Pageable pageable
    );

    void closeAccount(Long accountId);
}