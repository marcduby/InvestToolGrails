
-- create the cash flow for an account id and year

DELIMITER //
CREATE PROCEDURE createYearCashFlow(IN account_id int(9), IN year int(9))
  declare v_max int unsigned default 12;
  declare v_counter int unsigned default 1;

  BEGIN
    while v_counter <= v_max DO
      insert into inv_account (name, description, account_type_id, user_id, last_updated, date_created, version)
        values('Red Bank', 'Red bank account', (select account_type_id from inv_account_type where name = 'Taxable Bank'),
          (select user_id from inv_user where name = 'King'),
          sysdate(), sysdate(), 0);

  END //
DELIMITER ;

