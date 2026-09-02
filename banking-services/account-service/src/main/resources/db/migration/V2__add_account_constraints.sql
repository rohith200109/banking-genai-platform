ALTER TABLE account_schema.accounts
    ADD CONSTRAINT chk_account_balance_non_negative
    CHECK (balance >= 0);

ALTER TABLE account_schema.accounts
    ADD CONSTRAINT chk_account_type
    CHECK (
        account_type IN (
            'SAVINGS',
            'CHECKING',
            'CURRENT'
        )
    );

ALTER TABLE account_schema.accounts
    ADD CONSTRAINT chk_account_status
    CHECK (
        status IN (
            'ACTIVE',
            'BLOCKED',
            'CLOSED'
        )
    );

ALTER TABLE account_schema.accounts
    ADD CONSTRAINT chk_account_number_not_blank
    CHECK (length(trim(account_number)) > 0);