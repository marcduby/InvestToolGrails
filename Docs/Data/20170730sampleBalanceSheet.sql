

-- create the 401k account
insert into inv_account (name, description, account_type_id, user_id, last_updated, date_created, version)
  values('Gene 403b', 'Gene 403b', 2, 1, sysdate(), sysdate(), 0);

-- add in the data
insert into 
