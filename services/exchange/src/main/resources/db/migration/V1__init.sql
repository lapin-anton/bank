CREATE TABLE exchange_rate
(
    id       BIGINT PRIMARY KEY,
    currency VARCHAR NOT NULL,
    value    DECIMAL NOT NULL default 0.00,

    created  TIMESTAMP WITH TIME ZONE,
    updated  TIMESTAMP WITH TIME ZONE
);

create sequence if not exists exchange_rate_seq start with 1 increment by 50;

CREATE UNIQUE INDEX idx__exchange_rate_currency ON exchange_rate (currency);