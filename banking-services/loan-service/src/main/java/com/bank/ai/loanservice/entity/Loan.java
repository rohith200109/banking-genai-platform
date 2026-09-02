package com.bank.ai.loanservice.entity;

import jakarta.persistence.*;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "loans",
        schema = "loan_schema"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id")
    private Long loanId;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(
            name = "loan_number",
            nullable = false,
            unique = true,
            length = 30
    )
    private String loanNumber;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "loan_type",
            nullable = false,
            length = 30
    )
    private LoanType loanType;

    @Column(
            name = "principal_amount",
            nullable = false,
            precision = 19,
            scale = 4
    )
    private BigDecimal principalAmount;

    @Column(
            name = "outstanding_amount",
            nullable = false,
            precision = 19,
            scale = 4
    )
    private BigDecimal outstandingAmount;

    @Column(
            name = "interest_rate",
            nullable = false,
            precision = 7,
            scale = 4
    )
    private BigDecimal interestRate;

    @Column(
            name = "tenure_months",
            nullable = false
    )
    private Integer tenureMonths;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "status",
            nullable = false,
            length = 20
    )
    private LoanStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {

        LocalDateTime now = LocalDateTime.now();

        createdAt = now;
        updatedAt = now;

        if (status == null) {
            status = LoanStatus.PENDING;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}