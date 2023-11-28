DROP TABLE IF EXISTS rentalparty;
CREATE TABLE rentalparty
(
    id  SERIAL PRIMARY KEY,
    mail_address VARCHAR(200),
    rental_id CHARACTER(6) NOT NULL UNIQUE ,
    introduce TEXT
);

INSERT INTO rentalparty(mail_address,rental_id, introduce)
VALUES('nishiki.hase@gmail.com','123456','abstract');
INSERT INTO rentalparty(mail_address,rental_id, introduce)
VALUES('nishiki.hase03@gmail.com','abcdef','abstract');
INSERT INTO rentalparty(mail_address,rental_id, introduce)
VALUES('kawausoo9@gmail.com','party1','abstract');

DROP TABLE IF EXISTS faborite;
CREATE TABLE faborite
(
    id  SERIAL PRIMARY KEY,
    mail_address VARCHAR(200),
    rental_id CHARACTER(6) NOT NULL,
    memo TEXT
);

INSERT INTO faborite(mail_address,rental_id, memo)
VALUES('nishiki.hase@gmail.com','party1','user2');