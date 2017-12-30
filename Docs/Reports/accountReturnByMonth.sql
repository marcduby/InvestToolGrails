

-- for a year, get the return
select min(total_balance), max(total_balance), sum(transfer), max(total_balance) - 7096.65 - sum(transfer),
    ((max(total_balance) - 7096.65 - sum(transfer)) * 100)/ 7096.65 as percent
from inv_balance_sheet bs
where bs.account_id = 12
    and (bs.month_id div 100) = 2016;
    
select min(total_balance), max(total_balance), sum(transfer), max(total_balance) - 27841.20 - sum(transfer),
    ((max(total_balance) - 27841.20 - sum(transfer)) * 100)/ 27841.20 as percent
from inv_balance_sheet bs
where bs.account_id = 12
    and (bs.month_id div 100) = 2017;
    
    