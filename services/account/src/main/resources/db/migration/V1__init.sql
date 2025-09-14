
CREATE TABLE account
(
    id             BIGINT PRIMARY KEY,
    number         VARCHAR NOT NULL,
    user_id        VARCHAR NOT NULL,
    balance        DECIMAL NOT NULL default 0.00,
    status         VARCHAR NOT NULL,
    currency       VARCHAR NOT NULL,

    created        TIMESTAMP WITH TIME ZONE,
    updated        TIMESTAMP WITH TIME ZONE
);

create sequence if not exists account_seq start with 1 increment by 50;

CREATE INDEX idx__account_user_id ON account (user_id);

CREATE UNIQUE INDEX idx__account_number ON account (number);
CREATE UNIQUE INDEX idx__account_user_currency ON account (user_id, currency);