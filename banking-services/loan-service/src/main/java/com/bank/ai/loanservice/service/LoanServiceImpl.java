package com.bank.ai.loanservice.service;

// import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bank.ai.loanservice.exception.LoanNotFoundException;
import com.bank.ai.loanservice.dto.LoanCreateRequest;
import com.bank.ai.loanservice.dto.LoanResponse;
import com.bank.ai.loanservice.dto.LoanStatusUpdateRequest;
import com.bank.ai.loanservice.entity.Loan;
import com.bank.ai.loanservice.entity.LoanStatus;
import com.bank.ai.loanservice.repository.LoanRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.bank.ai.loanservice.dto.PageResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;

    @Override
    public LoanResponse createLoan(
            LoanCreateRequest request) {

        Loan loan = Loan.builder()
                .customerId(request.customerId())
                .accountId(request.accountId())
                .loanNumber(generateLoanNumber())
                .loanType(request.loanType())
                .principalAmount(request.principalAmount())
                .outstandingAmount(request.principalAmount())
                .interestRate(request.interestRate())
                .tenureMonths(request.tenureMonths())
                .status(LoanStatus.PENDING)
                .build();

        Loan savedLoan = loanRepository.save(loan);

        return mapToResponse(savedLoan);
    }

    @Override
    @Transactional(readOnly = true)
    public LoanResponse getLoanById(
            Long loanId) {

        Loan loan = loanRepository.findById(loanId)
               .orElseThrow(() ->
        new LoanNotFoundException(loanId)
);

        return mapToResponse(loan);
    }

@Override
@Transactional(readOnly = true)
public PageResponse<LoanResponse> getLoansByCustomerId(
        Long customerId,
        int page,
        int size) {

    Pageable pageable =
            PageRequest.of(page, size);

    Page<Loan> loanPage =
            loanRepository.findByCustomerId(
                    customerId,
                    pageable
            );

    return mapToPageResponse(loanPage);
}
 @Override
@Transactional(readOnly = true)
public PageResponse<LoanResponse> getLoans(
        int page,
        int size) {

    Pageable pageable =
            PageRequest.of(page, size);

    Page<Loan> loanPage =
            loanRepository.findAll(pageable);

    return mapToPageResponse(loanPage);
}
    @Override
    public LoanResponse updateLoanStatus(
            Long loanId,
            LoanStatusUpdateRequest request) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() ->
        new LoanNotFoundException(loanId)
);

        loan.setStatus(request.status());

        return mapToResponse(loanRepository.save(loan));
    }

    @Override
    public void deleteLoan(Long loanId) {

        if (!loanRepository.existsById(loanId)) {
           throw new LoanNotFoundException(loanId);
        }

        loanRepository.deleteById(loanId);
    }

    private String generateLoanNumber() {

        return "LN-" +
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();
    }

private PageResponse<LoanResponse> mapToPageResponse(
        Page<Loan> loanPage) {

    return new PageResponse<>(
            loanPage.getContent()
                    .stream()
                    .map(this::mapToResponse)
                    .toList(),

            loanPage.getNumber(),
            loanPage.getSize(),
            loanPage.getTotalElements(),
            loanPage.getTotalPages(),
            loanPage.isFirst(),
            loanPage.isLast()
    );
}


    private LoanResponse mapToResponse(
            Loan loan) {

        return new LoanResponse(
                loan.getLoanId(),
                loan.getCustomerId(),
                loan.getAccountId(),
                loan.getLoanNumber(),
                loan.getLoanType(),
                loan.getPrincipalAmount(),
                loan.getOutstandingAmount(),
                loan.getInterestRate(),
                loan.getTenureMonths(),
                loan.getStatus(),
                loan.getCreatedAt(),
                loan.getUpdatedAt()
        );
    }
}