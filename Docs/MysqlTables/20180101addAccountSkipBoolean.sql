
-- add a boolean to skip the cacj flow for that month
alter table inv_balance_sheet add skip tinyint(1) default 0;
