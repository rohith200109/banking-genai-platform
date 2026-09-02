package com.bank.ai.accountservice.controller;

import com.bank.ai.accountservice.dto.AccountRequest;
import com.bank.ai.accountservice.dto.AccountResponse;
import com.bank.ai.accountservice.dto.PageResponse;
import com.bank.ai.accountservice.service.AccountService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(
            @Valid @RequestBody AccountRequest request) {

        AccountResponse response =
                accountService.createAccount(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponse> getAccount(
            @PathVariable Long accountId) {

        return ResponseEntity.ok(
                accountService.getAccountById(accountId)
        );
    }

    @GetMapping
    public ResponseEntity<PageResponse<AccountResponse>>
    getAllAccounts(

            @PageableDefault(
                    size = 10,
                    sort = "accountId",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable) {

        return ResponseEntity.ok(
                accountService.getAllAccounts(pageable)
        );
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> closeAccount(
            @PathVariable Long accountId) {

        accountService.closeAccount(accountId);

        return ResponseEntity.noContent().build();
    }
}