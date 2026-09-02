CREATE TABLE account_schema.processed_events (

    event_id VARCHAR(100) PRIMARY KEY,

    event_type VARCHAR(100) NOT NULL,

    processed_at TIMESTAMP NOT NULL
);