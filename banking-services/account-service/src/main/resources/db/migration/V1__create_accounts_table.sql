CREATE SCHEMA IF NOT EXISTS account_schema;

CREATE TABLE account_schema.accounts (
    account_id BIGSERIAL PRIMARY KEY,

    customer_id BIGINT NOT NULL,

    account_number VARCHAR(20) NOT NULL UNIQUE,

    account_type VARCHAR(30) NOT NULL,

    balance NUMERIC(19, 4) NOT NULL DEFAULT 0,

    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL
);