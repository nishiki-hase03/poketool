CREATE TABLE faborite
(
    id  SERIAL PRIMARY KEY,
    mail_address VARCHAR(200) UNIQUE,
    rental_id CHARACTER(6) NOT NULL UNIQUE ,
    memo TEXT
);

INSERT INTO faborite(user_id,rental_id, memo)
VALUES(1,'party1','user2');