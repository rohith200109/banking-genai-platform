CREATE SCHEMA IF NOT EXISTS loan_schema;

CREATE TABLE loan_schema.loans (

    loan_id BIGSERIAL PRIMARY KEY,

    customer_id BIGINT NOT NULL,

    account_id BIGINT NOT NULL,

    loan_number VARCHAR(30) NOT NULL UNIQUE,

    loan_type VARCHAR(30) NOT NULL,

    principal_amount NUMERIC(19, 4) NOT NULL,

    outstanding_amount NUMERIC(19, 4) NOT NULL,

    interest_rate NUMERIC(7, 4) NOT NULL,

    tenure_months INTEGER NOT NULL,

    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL
);