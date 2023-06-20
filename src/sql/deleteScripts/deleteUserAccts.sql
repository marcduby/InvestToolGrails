

-- buttercup accts
delete from inv_balance_sheet where account_id in (select account_id from inv_account where user_id = 2);
delete from inv_holding where account_id in (select account_id from inv_account where user_id = 2);
delete from inv_account where user_id = 2;



-- banana accts
delete from inv_balance_sheet where account_id in (select account_id from inv_account where user_id = 1 and account_id not in (1, 29, 2, 10));
delete from inv_holding where account_id in (select account_id from inv_account where user_id = 1 and account_id not in (1, 29, 2, 10));
delete from inv_account where user_id = 1 and account_id not in (1, 29, 2, 10);

-- modify amounts
update inv_balance_sheet set total_balance = 0.6 * total_balance where account_id = 1;
update inv_balance_sheet set cash_balance = 0.6 * cash_balance where account_id = 1;
update inv_balance_sheet set cd_balance = 0.6 * cd_balance where account_id = 1;
update inv_balance_sheet set income = 0.6 * income where account_id = 1;
update inv_balance_sheet set transfer = 0.6 * transfer where account_id = 1;
update inv_balance_sheet set money_market = 0.6 * money_market where account_id = 1;




