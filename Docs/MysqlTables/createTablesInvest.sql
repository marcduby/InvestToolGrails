
-- $Revision: 1.7 $
-- $Id: createTablesInvest.sql,v 1.7 2015/02/08 04:17:46 mduby Exp $


-- drop database invest;

-- create database invest;

-- use database invest;

-- create invest users table
drop table inv_user;
create table inv_user (
	user_id 			int not null auto_increment primary key,
	name 				varchar(1000) not null,
	full_name			varchar(1000),
	last_updated		datetime default null,
	date_created 		datetime default null,
	version 			bigint(20) default null
);

-- create account table
drop table inv_account;
create table inv_account (
	account_id 			int not null auto_increment primary key,
	name 				varchar(1000) not null,
	description			varchar(1000),
	account_type_id		int not null,
	user_id				int not null,
	last_updated		datetime default null,
	date_created 		datetime default null,
	version 			bigint(20) default null
);

-- account type table
drop table inv_account_type;
create table inv_account_type (
	account_type_id int not null auto_increment primary key,
	name				varchar(100),
	description			varchar(1000),
	last_updated		datetime default null,
	date_created 		datetime default null,
	version 			bigint(20) default null
);

-- create invest security table
drop table inv_security;
create table inv_security (
	security_id 		int not null auto_increment primary key,
	name 				varchar(1000) not null,
	description			varchar(1000),
	current_symbol_id	int,
	last_updated		datetime default null,
	date_created 		datetime default null,
	version 			bigint(20) default null
);

-- create invest symbol table
drop table inv_symbol;
create table inv_symbol (
	symbol_id 			int not null auto_increment primary key,
	symbol				varchar(100),
	security_id			int not null,
	last_updated		datetime default null,
	date_created 		datetime default null,
	version 			bigint(20) default null
);

-- create invest symbol table
drop table inv_holding;
create table inv_holding (
	holding_id 			int not null auto_increment primary key,
	account_id			int not null,
	security_id			int not null,
	amount				float(10,2) not null,
	last_updated		datetime default null,
	date_created 		datetime default null,
	version 			bigint(20) default null
);

-- transaction table
drop table inv_transaction;
create table inv_transaction (
	transaction_id 		int not null auto_increment primary key,
	security_id 		int not null,
	symbol_id 			int not null,
	transaction_type_id int not null,
	account_id 			int not null,
	amount				float(10,2) not null,
	price 				float(10,2) not null,
	transaction_date	date not null,
	description			varchar(1000),
	last_updated		datetime default null,
	date_created 		datetime default null,
	version 			bigint(20) default null
);

-- transaction type table
drop table inv_transaction_type;
create table inv_transaction_type (
	transaction_type_id int not null auto_increment primary key,
	name				varchar(100),
	description			varchar(1000),
	last_updated		datetime default null,
	date_created 		datetime default null,
	version 			bigint(20) default null
);

-- create the news snippet tables
drop table inv_news;
create table inv_news (
	news_id 			int not null auto_increment primary key,
	text 				varchar(4000) not null,
	comment				varchar(1000),
	last_updated		datetime default null,
	date_created 		datetime default null,
	version 			bigint(20) default null
);

-- create the news snippet link table
drop table inv_news_link;
create table inv_news_link (
	news_link_id 		int not null auto_increment primary key,
	news_id				int,
	security_id			int,
	last_updated		datetime default null,
	date_created 		datetime default null,
	version 			bigint(20) default null
);

-- security risk table
drop table inv_risk;
create table inv_risk (
	risk_id				int not null primary key,
	name				varchar(400) not null,
	last_updated		datetime default null,
	date_created 		datetime default null,
	version 			bigint(20) default null
);

-- table for investment industry
create table inv_industry (
	industry_id 		int not null auto_increment primary key,
	name				varchar(4000) not null,
	description			varchar(4000),
	last_updated		datetime default null,
	date_created 		datetime default null,
	version 			bigint(20) default null
);






