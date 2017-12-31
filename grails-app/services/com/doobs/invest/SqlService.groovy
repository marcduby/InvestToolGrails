package com.doobs.invest

import groovy.sql.Sql
import javax.sql.DataSource
import grails.transaction.Transactional

@Transactional
class SqlService {
	DataSource dataSource
	
	/**
	 * get the list of securities needing a price for the given date
	 * 
	 * @param date
	 * @return
	 */
	List<Security> getSecurityListNeedingQuoteForDate(Date date) {
		// format the date
		String formattedDate = date.format("yyyy-MM-dd")
		List<Security> securityList = new ArrayList<Security>()
		
		// log
		log.info("looking for prices for stocks on date: " + formattedDate)
		
		// build the sql
		def sqlString = """
			select sec.security_id
			from inv_security sec where sec.security_id not in (
				select pri.security_id
				from inv_security_price pri where date(pri.transaction_date) = '${formattedDate}'
			)
		"""
		
		// log the sql string
		log.info("the sql is: " + sqlString)
		
		// execute the sql
		def sql = new Sql(dataSource)
		sql.eachRow(sqlString) { row ->
			securityList.add(Security.get(row.security_id))
		}
		
		// return
		return securityList
	}

	List<InvestDiversificationBean> getIndustryDiversificationListByAccount(String accountId) {
		// format the date
		List<InvestDiversificationBean> diversificationList = new ArrayList<InvestDiversificationBean>()
		
		// log
		log.info("looking for diversification report for account: " + accountId)
		
		// build the sql
		def sqlString = """
			select sum(hold.amount * sec_pri.price) as total, 
				sum(hold.amount * sec_pri.yearly_dividend) as dividend, indu.name
			from inv_holding hold, inv_security sec, inv_industry indu, inv_security_price sec_pri,
			inv_symbol sym
			where hold.security_id = sec.security_id
			and sec.industry_id = indu.industry_id
			and sec.current_price_id = sec_pri.security_price_id
			and sec.current_symbol_id = sym.symbol_id
			and hold.account_id = ${accountId}
			group by indu.name
		"""
		
		// log the sql string
		log.info("the sql is: " + sqlString)
		
		// execute the sql
		def sql = new Sql(dataSource)
		sql.eachRow(sqlString) { row ->
			InvestDiversificationBean bean = new InvestDiversificationBean(name: row.name, amountTotal: row.total, dividendTotal: row.dividend)
			diversificationList.add(bean)
		}
		
		// return
		return diversificationList
	}

	/**
	 * returns the month list for a year
	 *
	 * @param yearId
	 * @return
	 */
	public List<Month> getMonthsForYear(int yearId) {
		// local variables
		List<Month> monthList = new ArrayList<Month>();

		// create the sql string
		String sqlString = "select month_id as id from inv_month where floor(month_id / 100) = ${yearId}";

		// log
		log.info("The sql string for the month lookup is: " + sqlString);

		// execute the sql
		def sql = new Sql(this.dataSource);
		sql.eachRow(sqlString) { row ->
			Month month = Month.get(row.id);
			monthList.add(month);
		}

		// return
		return monthList;
	}


	List<InvestDiversificationBean> getIndustryDiversificationList() {
		// format the date
		List<InvestDiversificationBean> diversificationList = new ArrayList<InvestDiversificationBean>()
		
		// log
		log.info("looking for diversification report for all accounts")
		
		// build the sql
		def sqlString = """
			select sum(hold.amount * sec_pri.price) as total, 
				sum(hold.amount * sec_pri.yearly_dividend) as dividend, indu.name
			from inv_holding hold, inv_security sec, inv_industry indu, inv_security_price sec_pri,
			inv_symbol sym
			where hold.security_id = sec.security_id
			and sec.industry_id = indu.industry_id
			and sec.current_price_id = sec_pri.security_price_id
			and sec.current_symbol_id = sym.symbol_id
			group by indu.name
		"""
		
		// log the sql string
		log.info("the sql is: " + sqlString)
		
		// execute the sql
		def sql = new Sql(dataSource)
		sql.eachRow(sqlString) { row ->
			InvestDiversificationBean bean = new InvestDiversificationBean(name: row.name, amountTotal: row.total, dividendTotal: row.dividend)
			diversificationList.add(bean)
		}
		
		// return
		return diversificationList
	}

}
