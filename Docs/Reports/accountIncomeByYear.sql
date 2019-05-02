
-- get the account return by year for a group id and grouped by accoun type
select sum(balance.income) as income_total, account.account_id, account.name account_name,
account_type.account_type_id, account_type.name account_type_name, balance.month_id div 100 as year_id
from inv_balance_sheet balance, inv_account account, inv_month imonth, inv_account_type account_type, inv_user_group_link link
where balance.account_id = account.account_id
and balance.month_id = imonth.month_id
and account.account_type_id = account_type.account_type_id
and account.user_id = link.user_id
and link.group_id = 3
and balance.month_id div 100 > 2007
group by account.name, account_type.name, year_id
order by account_type.name, account.name, year_id;


select sum(balance.income) as income_total, account.account_id, account.name account_name,
account_type.account_type_id, account_type.name account_type_name, balance.month_id div 100 as year_id, link.group_id
from inv_balance_sheet balance, inv_account account, inv_month imonth, inv_account_type account_type, inv_user_group_link link
where balance.account_id = account.account_id
and balance.month_id = imonth.month_id
and account.account_type_id = account_type.account_type_id
and account.user_id = link.user_id
and balance.month_id div 100 > 2007
group by account.name, account_type.name, year_id, link.group_id
order by account_type.name, account.name, year_id, link.group_id;


-- total all accounts
select sum(balance.income), balance.month_id div 100 as year_id
from inv_balance_sheet balance, inv_account account, inv_month imonth, inv_account_type account_type, inv_user_group_link link
where balance.account_id = account.account_id
and balance.month_id = imonth.month_id
and account.account_type_id = account_type.account_type_id
and account.user_id = link.user_id
and link.group_id = 4
and balance.month_id div 100 > 2007
group by year_id
order by year_id;




-- current sql service query
select sum(balance.income) as income_total, account.account_id, account.name account_name,
account_type.account_type_id, account_type.name account_type_name, balance.month_id div 100 as year_id, link.group_id
from inv_balance_sheet balance, inv_account account, inv_month imonth, inv_account_type account_type, inv_user_group_link link
where balance.account_id = account.account_id
and balance.month_id = imonth.month_id
and account.account_type_id = account_type.account_type_id
and account.user_id = link.user_id
and balance.month_id div 100 > 2007
and link.group_id = 4
group by account.name, account_type.name, year_id, link.group_id
order by account_type.name, account.name, year_id, link.group_id;
