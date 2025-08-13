

-- bank table
drop table if exists inv_financial_institution;
create table inv_financial_institution (
	institution_id 	int not null auto_increment primary key,
    institution_name            varchar(100) not null,
	last_updated		datetime default null,
	date_created 		datetime default null,
	version 			bigint(20) default null
);

-- institution data
insert into inv_financial_institution (institution_name) values('UBS');
insert into inv_financial_institution (institution_name) values('Citizens');
insert into inv_financial_institution (institution_name) values('HU');
insert into inv_financial_institution (institution_name) values('TD bank');
insert into inv_financial_institution (institution_name) values('Wells Fargo');
insert into inv_financial_institution (institution_name) values('Goldman Sachs');


-- CD table
drop table if exists inv_certificate_deposit;
create table inv_certificate_deposit (
	certificate_id      int not null auto_increment primary key,
	account_id          int not null,
	institution_id      int not null,
    cd_name             varchar(100) not null,
	total_balance       float(10,2) default 0.0,
	interest_rate       float(10,2) default 0.0,
	maturity            date default '1970-01-01',
	auto_renew          tinyint(1) default 0,
	expired             tinyint(1) default 0,
	last_updated        datetime default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	date_created        datetime default CURRENT_TIMESTAMP,
	version             bigint(20) default null
);

-- test data
insert into inv_certificate_deposit (account_id, institution_id, cd_name, total_balance, interest_rate, maturity, auto_renew)
values (29, 2, 'CIT-9849', 1421.48, 3.45, '2024-02-01', 1);

insert into inv_certificate_deposit (account_id, institution_id, cd_name, total_balance, interest_rate, maturity, auto_renew)
values (29, 2, 'CIT-9857', 54943.11, 3.45, '2024-02-29', 1);
insert into inv_certificate_deposit (account_id, institution_id, cd_name, total_balance, interest_rate, maturity, auto_renew)
values (29, 2, 'CIT-6263', 11108.66, 3.45, '2024-02-01', 1);
insert into inv_certificate_deposit (account_id, institution_id, cd_name, total_balance, interest_rate, maturity, auto_renew)
values (29, 2, 'CIT-9385', 12585.39, 1.74, '2024-04-29', 1);

insert into inv_certificate_deposit (account_id, institution_id, cd_name, total_balance, interest_rate, maturity, auto_renew)
values (27, 3, 'HU-0963', 10900, 2.33, '2024-08-16', 1);
insert into inv_certificate_deposit (account_id, institution_id, cd_name, total_balance, interest_rate, maturity, auto_renew)
values (27, 3, 'HU-0965', 54700, 4.32, '2024-08-14', 1);
insert into inv_certificate_deposit (account_id, institution_id, cd_name, total_balance, interest_rate, maturity, auto_renew)
values (27, 3, 'HU-0966', 54700, 4.32, '2024-08-14', 1);
insert into inv_certificate_deposit (account_id, institution_id, cd_name, total_balance, interest_rate, maturity, auto_renew)
values (27, 3, 'HU-0967', 40800, 4.42, '2024-10-05', 1);
insert into inv_certificate_deposit (account_id, institution_id, cd_name, total_balance, interest_rate, maturity, auto_renew)
values (27, 3, 'HU-0968', 19009.37, 2.58, '2026-12-19', 1);
insert into inv_certificate_deposit (account_id, institution_id, cd_name, total_balance, interest_rate, maturity, auto_renew)
values (27, 3, 'HU-0969', 30008.22, 2.58, '2026-12-11', 1);
insert into inv_certificate_deposit (account_id, institution_id, cd_name, total_balance, interest_rate, maturity, auto_renew)
values (27, 3, 'HU-9720', 30012.03, 4.42, '2024-06-17', 1);
insert into inv_certificate_deposit (account_id, institution_id, cd_name, total_balance, interest_rate, maturity, auto_renew)
values (27, 3, 'HU-1155', 50028.64, 4.42, '2024-06-17', 1);
insert into inv_certificate_deposit (account_id, institution_id, cd_name, total_balance, interest_rate, maturity, auto_renew)
values (27, 3, 'HU-1163', 16002.37, 2.33, '2024-06-10', 1);






-- scratch
select dep.certificate_id, fin.institution_name, dep.cd_name, dep.total_balance, dep.interest_rate, dep.maturity,
dep.total_balance * dep.interest_rate / 100 as yearly_interest
from inv_certificate_deposit dep, inv_financial_institution fin 
where dep.institution_id = fin.institution_id
order by dep.maturity;


select fin.institution_name, sum(dep.total_balance) as total, sum(dep.total_balance * dep.interest_rate / 100) as interest
from inv_certificate_deposit dep, inv_financial_institution fin 
where dep.institution_id = fin.institution_id
group by fin.institution_name;


drop table if exists inv_certificate_deposit;
create table inv_certificate_deposit (
	certificate_id      int not null auto_increment primary key,
	account_id          int not null,
	institution_id      int not null,
    cd_name             varchar(100) not null,
	total_balance       float(10,2) default 0.0,
	interest_rate       float(10,2) default 0.0,
	maturity            date default '1970-01-01',
	auto_renew          tinyint(1) default 0,
	last_updated        datetime default null,
	date_created        datetime default null,
	version             bigint(20) default null
);
