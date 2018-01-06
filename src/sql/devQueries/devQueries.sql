

-- select all accounts grouped by users
select u.user_id, u.name, a.account_id, a.name, t.account_type_id, t.name
from inv_user u, inv_account a, inv_account_type t
where u.user_id = a.user_id
  and a.account_type_id = t.account_type_id
order by u.user_id, t.account_type_id;


-- select accounts by user groups
select u.user_id, u.name, a.account_id, a.name, t.account_type_id, t.name, group_link.group_id
from inv_user u, inv_account a, inv_account_type t, inv_user_group_link group_link
where u.user_id = a.user_id
  and a.account_type_id = t.account_type_id
  and u.user_id = group_link.user_id
order by t.account_type_id, u.user_id, group_link.group_id;



select u.user_id, u.name, a.account_id, a.name, t.account_type_id, t.name, group_link.group_id
from inv_user u, inv_account a, inv_account_type t, inv_user_group_link group_link
where u.user_id = a.user_id
  and a.account_type_id = t.account_type_id
  and u.user_id = group_link.user_id
  and group_link.group_id = 1
order by t.account_type_id, u.user_id, group_link.group_id;

