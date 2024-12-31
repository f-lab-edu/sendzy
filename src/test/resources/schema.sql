CREATE TABLE member (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(300) UNIQUE,
    encoded_password VARCHAR(300),
    created_at datetime
);

CREATE TABLE account (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    balance BIGINT NOT NULL
);

CREATE INDEX idx_member_id ON account (member_id);
