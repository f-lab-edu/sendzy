CREATE TABLE IF NOT EXISTS member (
  `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `email` varchar(300) NOT NULL UNIQUE,
  `encoded_password` varchar(300) NOT NULL,
  `created_at` datetime NOT NULL
);

CREATE TABLE IF NOT EXISTS remittance_history (
  `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `remittance_request_id` bigint,
  `member_id` bigint NOT NULL,
  `email` bigint NOT NULL,
  `description` varchar(100) NOT NULL,
  `amount` bigint NOT NULL,
  `balance` bigint NOT NULL,
  `created_at` datetime NOT NULL
);

CREATE TABLE IF NOT EXISTS remittance_request (
  `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `sender_id` bigint NOT NULL,
  `receiver_id` bigint NOT NULL,
  `status` varchar(50) NOT NULL, -- PENDING, ACCEPTED, EXPIRED, CANCELLED, ...
  `amount` bigint NOT NULL,
  `created_at` datetime NOT NULL
);

CREATE TABLE IF NOT EXISTS remittance_status_history (
  `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `request_id` bigint NOT NULL,
  `sender_id` bigint NOT NULL,
  `receiver_id` bigint NOT NULL,
  `amount` bigint NOT NULL,
  `status` varchar(50) NOT NULL, -- PENDING, ACCEPTED, EXPIRED, CANCELLED, ...
  `created_at` datetime NOT NULL,
  `expired_at` datetime,
  `accepted_at` datetime
);

CREATE TABLE IF NOT EXISTS account (
   `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
   `member_id` BIGINT NOT NULL,
   `balance` BIGINT NOT NULL
);

ALTER TABLE remittance_request ADD CONSTRAINT remittance_request_receiver_id_fk FOREIGN KEY (receiver_id) REFERENCES member (id);
ALTER TABLE remittance_request ADD CONSTRAINT remittance_request_sender_id_fk FOREIGN KEY (sender_id) REFERENCES member (id);

CREATE INDEX idx_member_email ON member (email);
CREATE INDEX idx_remittance_history_member_id ON remittance_history (member_id);
CREATE INDEX idx_remittance_request_sender_id ON remittance_request (sender_id);
CREATE INDEX idx_remittance_request_receiver_id ON remittance_request (receiver_id);
CREATE INDEX idx_remittance_status_history_request_id ON remittance_status_history (request_id);
CREATE INDEX idx_remittance_status_history_sender_id ON remittance_status_history (sender_id);
CREATE INDEX idx_remittance_status_history_receiver_id ON remittance_status_history (receiver_id);
CREATE INDEX idx_member_id ON account (member_id);
