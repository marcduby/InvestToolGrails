

-- add in the version table
drop table if exists inv_security_weekly_price;
create table inv_security_weekly_price (
security_weekly_id 	         int not null auto_increment primary key,
security_id                   int(11),
date_string                   varchar(10),
symbol                   varchar(10),
open_price                    float(10, 2),
close_price                    float(10, 2),
high_price                    float(10, 2),
low_price                    float(10, 2),
adjusted_close_price                    float(10, 2),
volume                    int(15),
dividend_amount                    float(10, 2),
week_date                   date,
last_updated    datetime,
date_created    datetime default CURRENT_TIMESTAMP,
version         bigint(20) default 0
);

alter table inv_security_weekly_price add foreign key (security_id) references inv_security(security_id);

