CREATE SCHEMA IF NOT EXISTS transaction_schema;

CREATE TABLE transaction_schema.transactions (

    transaction_id BIGSERIAL PRIMARY KEY,

    transaction_reference VARCHAR(50) NOT NULL UNIQUE,

    account_id BIGINT NOT NULL,

    customer_id BIGINT NOT NULL,

    transaction_type VARCHAR(20) NOT NULL,

    amount NUMERIC(19, 4) NOT NULL,

    balance_before NUMERIC(19, 4) NOT NULL,

    balance_after NUMERIC(19, 4) NOT NULL,

    status VARCHAR(20) NOT NULL,

    description VARCHAR(255),

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL
);