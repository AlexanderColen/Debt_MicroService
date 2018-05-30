/**
 * Author:  Alexander
 * Created: May 29, 2018
 */
INSERT INTO debt(date, description, type, interest, amount, currency)
VALUES('01-01-2018', 'Alex', 'PERSONAL', 0, 2000.00, 'CAD');
INSERT INTO debt(date, description, type, interest, amount, currency)
VALUES('01-01-2018', 'CashMoney', 'LOAN', 0, 555.55, 'CAD');
INSERT INTO payment(date, spent, currency, debt)
VALUES('01-02-2018', 500, 'CAD', 1);