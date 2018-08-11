

-- balance sheet price table
drop table if exists inv_balance_sheet;
create table inv_balance_sheet (
	balance_sheet_id 	int not null auto_increment primary key,
	account_id 		    int not null,
	month_id int not null,
	total_balance 		float(10,2) default 0.0,
	cash_balance 			float(10,2) default 0.0,
	income 				    float(10,2) default 0.0,
	transfer			    float(10,2) default 0.0,
	last_updated		datetime default null,
	date_created 		datetime default null,
	version 			bigint(20) default null
);


drop table if exists inv_month;
create table inv_month (
	month_id  int not null auto_increment primary key,
	month_number int not null,
	last_updated datetime default null,
	date_created datetime default null,
	version bigint(20) default null
);


-- modifications for CD balance
alter table inv_balance_sheet add cd_balance float(10,2) default 0.0;

-- update accounts
update inv_balance_sheet set cd_balance = total_balance - cash_balance where account_id = 29 and cash_balance > 0;
update inv_balance_sheet set cd_balance = total_balance - cash_balance where account_id = 27 and cash_balance > 0;


-- sample data

