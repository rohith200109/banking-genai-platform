ALTER TABLE customer_schema.customers
    ADD CONSTRAINT chk_customer_first_name_not_blank
    CHECK (length(trim(first_name)) > 0);

ALTER TABLE customer_schema.customers
    ADD CONSTRAINT chk_customer_last_name_not_blank
    CHECK (length(trim(last_name)) > 0);

ALTER TABLE customer_schema.customers
    ADD CONSTRAINT chk_customer_email_not_blank
    CHECK (length(trim(email)) > 0);

ALTER TABLE customer_schema.customers
    ADD CONSTRAINT chk_customer_phone_format
    CHECK (
        phone_number IS NULL
        OR phone_number ~ '^[0-9+() -]{7,20}$'
    );