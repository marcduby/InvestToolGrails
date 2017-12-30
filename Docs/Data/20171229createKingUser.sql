

-- create king user
insert into inv_user (name, full_name, last_updated, date_created, version) values('King', 'King', sysdate(), sysdate(), 0);

-- create queen user
insert into inv_user (name, full_name, last_updated, date_created, version) values('Queen', 'Queen', sysdate(), sysdate(), 0);

-- create bank account type
insert into inv_account_type (name, description, last_updated, date_created, version)
  values('Taxable Bank', 'Taxable bank account', sysdate(), sysdate(), 0);

-- create red bank account
insert into inv_account (name, description, account_type_id, user_id, last_updated, date_created, version)
  values('Red Bank', 'Red bank account', (select account_type_id from inv_account_type where name = 'Taxable Bank'),
    (select user_id from inv_user where name = 'King'),
    sysdate(), sysdate(), 0);


