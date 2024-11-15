CREATE TABLE IF NOT EXISTS auth (
  id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  member_id bigint NOT NULL,
  password varchar(100) NOT NULL,
  created_at datetime NOT NULL
);

CREATE TABLE IF NOT EXISTS member (
  id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `email` varchar(100) NOT NULL,
  created_at datetime NOT NULL
);

CREATE TABLE IF NOT EXISTS member_remittance_history (
  member_id bigint NOT NULL,
  remittance_history_id bigint NOT NULL,
  PRIMARY KEY (member_id, remittance_history_id)
);

CREATE TABLE IF NOT EXISTS remittance_history (
  id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  remittance_request_id bigint,
  member_id bigint NOT NULL,
  description varchar(100) NOT NULL,
  amount decimal(10, 2) NOT NULL,
  balance decimal(10, 2) NOT NULL,
  created_at datetime NOT NULL
);

CREATE TABLE IF NOT EXISTS remittance_request (
  id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  sender_id bigint NOT NULL,
  receiver_id bigint NOT NULL,
  status varchar(20) NOT NULL, -- PENDING, ACCEPTED, EXPIRED, CANCELLED
  `amount` decimal(10, 2) NOT NULL,
  created_at datetime NOT NULL,
  expired_at datetime NOT NULL
);

CREATE TABLE IF NOT EXISTS remittance_status_history (
  id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  request_id bigint NOT NULL,
  status varchar(20) NOT NULL, -- PENDING, ACCEPTED, EXPIRED, CANCELLED
  created_at datetime NOT NULL
);

ALTER TABLE auth ADD CONSTRAINT auth_member_id_fk FOREIGN KEY (member_id) REFERENCES member (id);
ALTER TABLE member_remittance_history ADD CONSTRAINT member_remittance_history_member_id_fk FOREIGN KEY (member_id) REFERENCES member (id);
ALTER TABLE member_remittance_history ADD CONSTRAINT member_remittance_history_remittance_history_id_fk FOREIGN KEY (remittance_history_id) REFERENCES remittance_history (id);
ALTER TABLE remittance_request ADD CONSTRAINT remittance_request_id_fk FOREIGN KEY (id) REFERENCES remittance_history (remittance_request_id);
ALTER TABLE remittance_status_history ADD CONSTRAINT remittance_status_history_remittance_request_id_fk FOREIGN KEY (request_id) REFERENCES remittance_request (id);
ALTER TABLE remittance_request ADD CONSTRAINT remittance_request_sender_id_fk FOREIGN KEY (sender_id) REFERENCES member (id);
ALTER TABLE remittance_request ADD CONSTRAINT remittance_request_receiver_id_fk FOREIGN KEY (receiver_id) REFERENCES member (id);
ALTER TABLE remittance_history ADD CONSTRAINT remittance_history_member_id_fk FOREIGN KEY (member_id) REFERENCES member (id);
