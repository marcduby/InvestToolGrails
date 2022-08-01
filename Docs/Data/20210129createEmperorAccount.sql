

-- create est invest account account
insert into inv_account (name, description, account_type_id, user_id, last_updated, date_created, version)
  values('Blue Est Inv', 'Blue Est invest account', (select account_type_id from inv_account_type where name = 'Invest'),
    (select user_id from inv_user where name = 'King'),
    sysdate(), sysdate(), 0);

