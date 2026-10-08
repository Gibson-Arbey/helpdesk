CREATE TABLE customers (
    cust_id BIGSERIAL PRIMARY KEY,
    cust_name VARCHAR(100) NOT NULL,
    cust_email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE agents (
    agen_id BIGINT PRIMARY KEY,
    agen_name VARCHAR(100) NOT NULL,
    agen_email VARCHAR(255) NOT NULL UNIQUE,
    agen_status VARCHAR(50) NOT NULL
);

CREATE TABLE tickets (
    tick_id BIGSERIAL PRIMARY KEY,
    tick_title VARCHAR(255) NOT NULL,
    tick_description TEXT NOT NULL,
    cust_id BIGINT NOT NULL,
    agen_id BIGINT,
    tick_status VARCHAR(50) NOT NULL,
    tick_assignedon TIMESTAMP,
    tick_createdon TIMESTAMP NOT NULL,
    CONSTRAINT fk_ticket_customer FOREIGN KEY (cust_id) REFERENCES customers(cust_id),
    CONSTRAINT fk_ticket_agent FOREIGN KEY (agen_id) REFERENCES agents(agen_id)
);

CREATE INDEX idx_ticket_customer
    ON tickets(cust_id);

CREATE INDEX idx_ticket_agent
    ON tickets(agen_id);

CREATE INDEX idx_ticket_status
    ON tickets(tick_status);