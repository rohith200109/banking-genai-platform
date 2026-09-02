package com.bank.ai.loanservice.repository;

// import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.ai.loanservice.entity.Loan;
import com.bank.ai.loanservice.entity.LoanStatus;

public interface LoanRepository
        extends JpaRepository<Loan, Long> {

    Optional<Loan> findByLoanNumber(
            String loanNumber
    );

    Page<Loan> findByCustomerId(
            Long customerId,
            Pageable pageable
    );

    Page<Loan> findByAccountId(
            Long accountId,
            Pageable pageable
    );

    boolean existsByLoanNumber(
            String loanNumber
    );

    boolean existsByAccountIdAndStatus(
            Long accountId,
            LoanStatus status
    );
}