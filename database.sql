CREATE DATABASE budget_tracker;

USE budget_tracker;

CREATE TABLE users
(
    id VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE accounts
(
    id VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    account_name VARCHAR(100) NOT NULL,
    account_type VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE expenses
(
    id VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    expenses_date DATE NOT NULL,
    account_id VARCHAR(255) NOT NULL,
    amount BIGINT NOT NULL,
    type VARCHAR(50) NOT NULL,
    description VARCHAR(200) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (account_id) REFERENCES accounts(id)
);

CREATE TABLE schedule
(
    id VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    expenses_id VARCHAR(255) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    by_time VARCHAR(10) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY fk_exp_id (expenses_id) REFERENCES expenses (id)
);

CREATE TABLE goal
(
    id VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    by_time VARCHAR(10) NOT NULL,
    by_acc_id VARCHAR(255),
    amount BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY fk_user_id (user_id) REFERENCES users (id)
);