package com.bank.ai.loanservice.service;

// import java.util.List;

import com.bank.ai.loanservice.dto.LoanCreateRequest;
import com.bank.ai.loanservice.dto.LoanResponse;
import com.bank.ai.loanservice.dto.LoanStatusUpdateRequest;
import com.bank.ai.loanservice.dto.PageResponse;

public interface LoanService {

    LoanResponse createLoan(
            LoanCreateRequest request
    );

    LoanResponse getLoanById(
            Long loanId
    );

PageResponse<LoanResponse> getLoansByCustomerId(
        Long customerId,
        int page,
        int size
);

  PageResponse<LoanResponse> getLoans(
        int page,
        int size
);

    LoanResponse updateLoanStatus(
            Long loanId,
            LoanStatusUpdateRequest request
    );

    void deleteLoan(
            Long loanId
    );
}