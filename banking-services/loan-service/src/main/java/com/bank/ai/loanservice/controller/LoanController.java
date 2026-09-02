package com.bank.ai.loanservice.controller;

// import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bank.ai.loanservice.dto.LoanCreateRequest;
import com.bank.ai.loanservice.dto.LoanResponse;
import com.bank.ai.loanservice.dto.LoanStatusUpdateRequest;
import com.bank.ai.loanservice.dto.PageResponse;
import com.bank.ai.loanservice.service.LoanService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    public ResponseEntity<LoanResponse> createLoan(
            @Valid @RequestBody LoanCreateRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(loanService.createLoan(request));
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<LoanResponse> getLoan(
            @PathVariable Long loanId) {

        return ResponseEntity.ok(
                loanService.getLoanById(loanId)
        );
    }

@GetMapping("/customer/{customerId}")
public ResponseEntity<PageResponse<LoanResponse>>
getLoansByCustomer(
        @PathVariable Long customerId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {

    return ResponseEntity.ok(
            loanService.getLoansByCustomerId(
                    customerId,
                    page,
                    size
            )
    );
}

@GetMapping
public ResponseEntity<PageResponse<LoanResponse>> getLoans(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {

    return ResponseEntity.ok(
            loanService.getLoans(page, size)
    );
}

    @PutMapping("/{loanId}/status")
    public ResponseEntity<LoanResponse>
    updateLoanStatus(
            @PathVariable Long loanId,
            @Valid @RequestBody
            LoanStatusUpdateRequest request) {

        return ResponseEntity.ok(
                loanService.updateLoanStatus(
                        loanId,
                        request
                )
        );
    }

    @DeleteMapping("/{loanId}")
    public ResponseEntity<Void> deleteLoan(
            @PathVariable Long loanId) {

        loanService.deleteLoan(loanId);

        return ResponseEntity.noContent().build();
    }
}