

-- invest price table
drop table inv_security_price;
create table inv_security_price (
	security_price_id 	int not null auto_increment primary key,
	security_id 		int not null,
	symbol_id 			int not null,
	price 				float(10,2) not null,
	transaction_date	date not null,
	last_updated		datetime default null,
	date_created 		datetime default null,
	version 			bigint(20) default null
);

