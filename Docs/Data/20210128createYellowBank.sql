

-- create yellow bank account account
insert into inv_account (name, description, account_type_id, user_id, last_updated, date_created, version)
  values('Yellow Bank', 'Yellow bank account', (select account_type_id from inv_account_type where name = 'Bank'),
    (select user_id from inv_user where name = 'Harold'),
    sysdate(), sysdate(), 0);

