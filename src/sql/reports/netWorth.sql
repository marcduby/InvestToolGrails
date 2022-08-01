
-- net worth by year
select format(sum(ba.total_balance), 2) as total, floor(ba.month_id / 100) as acc_year, us.name as name
from inv_balance_sheet ba, inv_account acc, inv_user us
where ba.account_id = acc.account_id 
and acc.user_id = us.user_id
and acc.user_id = 1
and (ba.month_id % 100) = 12
group by acc_year, name
order by acc_year asc;

-- all years in the db
select distinct floor(ba.month_id / 100) as acc_year
from inv_month ba
order by acc_year asc;

-- get total end of year for all accounts
select ba.total_balance as total, acc.account_type_id, floor(ba.month_id / 100) as acc_year, us.name as name
from inv_balance_sheet ba, inv_account acc, inv_user us, inv_user_group_link link
where ba.account_id = acc.account_id 
and acc.user_id = us.user_id
and acc.user_id = link.user_id
and link.group_id = 3
and (ba.month_id % 100) = 12
and floor(ba.month_id / 100) >= 2010
order by acc_year asc;
