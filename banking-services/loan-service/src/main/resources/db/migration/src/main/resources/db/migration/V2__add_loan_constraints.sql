ALTER TABLE loan_schema.loans
    ADD CONSTRAINT chk_loan_principal_positive
    CHECK (principal_amount > 0);

ALTER TABLE loan_schema.loans
    ADD CONSTRAINT chk_loan_outstanding_non_negative
    CHECK (outstanding_amount >= 0);

ALTER TABLE loan_schema.loans
    ADD CONSTRAINT chk_loan_interest_rate_non_negative
    CHECK (interest_rate >= 0);

ALTER TABLE loan_schema.loans
    ADD CONSTRAINT chk_loan_tenure_positive
    CHECK (tenure_months > 0);

ALTER TABLE loan_schema.loans
    ADD CONSTRAINT chk_loan_type
    CHECK (
        loan_type IN (
            'PERSONAL',
            'HOME',
            'AUTO',
            'EDUCATION'
        )
    );

ALTER TABLE loan_schema.loans
    ADD CONSTRAINT chk_loan_status
    CHECK (
        status IN (
            'PENDING',
            'APPROVED',
            'REJECTED',
            'ACTIVE',
            'CLOSED'
        )
    );