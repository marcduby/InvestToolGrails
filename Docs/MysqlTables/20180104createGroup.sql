

-- user group price table
drop table inv_user_group
create table inv_user_group (
	user_group_id 	int not null auto_increment primary key,
	name            varchar(1000) not null,
	description     varchar(1000) not null,
	last_updated		datetime default null,
	date_created 		datetime default null,
	version 			bigint(20) default null
);

-- add user group link
drop table inv_user_group_link
create table inv_user_group_link (
	user_group_link_id 	    int not null auto_increment primary key,
	user_id                 int not null,
	group_id                int not null,
	name                    varchar(1000) not null,
	last_updated		        datetime default null,
	date_created 		        datetime default null,
	version 			          bigint(20) default null
);


-- create group harold
insert into inv_user_group (name, description, last_updated, date_created, version) values('Bean', 'Bean', sysdate(), sysdate(), 0);

insert into inv_user_group_link (user_id, group_id, name, last_updated, date_created, version)
  values((select user_id from inv_user where name = 'Harold'), (select user_group_id from inv_user_group where name = 'Bean'), 'link', sysdate(), sysdate(), 0);

insert into inv_user_group_link (user_id, group_id, name, last_updated, date_created, version)
  values((select user_id from inv_user where name = 'Buttercup'), (select user_group_id from inv_user_group where name = 'Bean'), 'link', sysdate(), sysdate(), 0);

-- create group king
insert into inv_user_group (name, description, last_updated, date_created, version) values('Apple', 'Apple', sysdate(), sysdate(), 0);

insert into inv_user_group_link (user_id, group_id, name, last_updated, date_created, version)
  values((select user_id from inv_user where name = 'King'), (select user_group_id from inv_user_group where name = 'Apple'), 'link', sysdate(), sysdate(), 0);

insert into inv_user_group_link (user_id, group_id, name, last_updated, date_created, version)
  values((select user_id from inv_user where name = 'Queen'), (select user_group_id from inv_user_group where name = 'Apple'), 'link', sysdate(), sysdate(), 0);




-- test
select g.user_group_id, g.name, u.user_id, u.name
from inv_user u, inv_user_group g, inv_user_group_link link
where link.user_id = u.user_id
and link.group_id = g.user_group_id
order by g.name, u.name;



