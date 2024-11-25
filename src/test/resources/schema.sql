CREATE TABLE member (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(300) UNIQUE,
    encoded_password VARCHAR(300),
    created_at datetime
);
