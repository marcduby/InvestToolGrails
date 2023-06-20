
-- create the inv_month table
drop table if exists inv_month;
create table inv_month (
	month_id  int not null,
    last_month_day date not null,
	last_updated datetime default null,
	date_created datetime default null,
	version bigint(20) default null
);


-- create procedure for month table population
DELIMITER $$
 DROP PROCEDURE IF EXISTS create_month_data_while_loop$$
 CREATE PROCEDURE create_month_data_while_loop()
 BEGIN
 DECLARE month_number  INT;
 DECLARE year_number  INT;
 DECLARE day_string varchar(20);

 SET month_number = 1;
 SET year_number = 1996;

WHILE year_number <= 2070 DO
  -- create the day string
  SET day_string = concat(year_number, '-');
  SET day_string = concat(day_string, month_number);
  SET day_string = concat(day_string, '-01');

  -- insert the month
  insert into inv_month (month_id, last_month_day, last_updated, date_created, version)
    values(year_number * 100 + month_number, last_day(day_string), sysdate(), sysdate(), 0);

   IF (month_number < 12) THEN
      SET month_number = month_number + 1;

   ELSE
      SET month_number = 1;
      SET year_number = year_number + 1;
   END IF;

END WHILE;

END$$
DELIMITER ;

-- call the procedure
call create_month_data_while_loop;

-- test
select * from inv_month where month_id div 100 = 2017;

-- manual creation

