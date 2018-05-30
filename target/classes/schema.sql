/**
 * Author:  Alexander
 * Created: May 29, 2018
 */
DROP TABLE IF EXISTS debt;
CREATE TABLE debt (
    id              INT             NOT NULL AUTO_INCREMENT,
    date            VARCHAR(255)    DEFAULT SYSDATE,
    description     VARCHAR(255)    ,
    type            VARCHAR(25)     ,
    interest        DOUBLE          DEFAULT 0,
    amount          DOUBLE          DEFAULT 0,
    currency        VARCHAR(10)     ,
    PRIMARY KEY(id)
);
DROP TABLE IF EXISTS payment;
CREATE TABLE payment (
    id              INT             NOT NULL AUTO_INCREMENT,
    date            VARCHAR(255)    DEFAULT SYSDATE,
    spent           DOUBLE          DEFAULT 0,
    currency        VARCHAR(10)     ,
    debt            INT             ,
    PRIMARY KEY(id),
    FOREIGN KEY(debt) REFERENCES debt(id)
);