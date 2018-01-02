

select u.user_id, u.name, a.account_id, a.name, t.account_type_id, t.name
from inv_user u, inv_account a, inv_account_type t
where u.user_id = a.user_id
  and a.account_type_id = t.account_type_id
order by u.user_id, t.account_type_id;

