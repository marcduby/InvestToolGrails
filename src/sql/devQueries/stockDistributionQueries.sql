

-- all current stock holdings for a group
select symbol.symbol, holding.amount, price.price, price.price * holding.amount as total,
  iuser.name, account.name, industry.name
from inv_holding holding, inv_security security, inv_security_price price,
  inv_user iuser, inv_user_group_link link, inv_account account, inv_industry industry,
  inv_symbol symbol
where iuser.user_id = link.user_id
and iuser.user_id = account.user_id
and holding.account_id = account.account_id
and security.security_id = holding.security_id
and price.security_price_id = security.current_price_id
and security.industry_id = industry.industry_id
and symbol.security_id = security.security_id
and holding.amount > 0
and link.group_id = 3
order by industry.name, symbol.symbol
;




-- to quantify
-- group exposure to one security (distributed accross accounts)
-- group industry diversification in account or group of accounts
-- group diversification with stock vs etfs
