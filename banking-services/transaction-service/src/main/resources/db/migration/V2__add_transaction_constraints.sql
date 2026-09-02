ALTER TABLE transaction_schema.transactions
    ADD CONSTRAINT chk_transaction_amount
    CHECK (amount > 0);

ALTER TABLE transaction_schema.transactions
    ADD CONSTRAINT chk_balance_before
    CHECK (balance_before >= 0);

ALTER TABLE transaction_schema.transactions
    ADD CONSTRAINT chk_balance_after
    CHECK (balance_after >= 0);

ALTER TABLE transaction_schema.transactions
    ADD CONSTRAINT chk_transaction_type
    CHECK (
        transaction_type IN (
            'DEPOSIT',
            'WITHDRAWAL',
            'TRANSFER'
        )
    );

ALTER TABLE transaction_schema.transactions
    ADD CONSTRAINT chk_transaction_status
    CHECK (
        status IN (
            'PENDING',
            'COMPLETED',
            'FAILED',
            'REVERSED'
        )
    );