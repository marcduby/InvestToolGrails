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

	/**
	 * returns the month list for a year
	 *
	 * @param yearId
	 * @return
	 */
	public List<AccountUserBean> getUserAndAccountsList() {
		// local variables
		List<AccountUserBean> accountUserBeanList = new ArrayList<AccountUserBean>();
		AccountUserBean accountUserBean = null;

		// create the sql string
		String sqlString = """
			select u.user_id, u.name as user_name, a.account_id, a.name as acct_name, t.account_type_id, t.name as type_name
			from inv_user u, inv_account a, inv_account_type t
			where u.user_id = a.user_id
			  and a.account_type_id = t.account_type_id
			order by u.user_id, t.account_type_id;
		"""

		// log
		log.info("The sql string for the user accounts lookup is: " + sqlString);

		// execute the sql
		def sql = new Sql(this.dataSource);
		sql.eachRow(sqlString) { row ->
			int userId = row.user_id;
			String userName = row.user_name
			String accountName = row.acct_name;
			int accountId = row.account_id

			// create a user object if needed
			if ((accountUserBean == null) || (accountUserBean.userId != userId)) {
				accountUserBean = new AccountUserBean();
				accountUserBean.setName(userName);
				accountUserBean.setUserId(userId);
				accountUserBean.setInitial(userName?.substring(0, 1))
				accountUserBeanList.add(accountUserBean);
			}

			// add the account
			AccountBean accountBean = new AccountBean();
			accountBean.setName(accountName);
			accountBean.setAccountId(accountId);
			accountUserBean.accountBeanList.add(accountBean);
		}

		// return
		return accountUserBeanList;
	}

	/**
	 * get the quarter list report
	 *
	 * @param year
	 * @return
     */
	public List<BalanceSheetListBean> getQuarterlyBalanceSheetsReport(Integer year, Integer groupId) {
		// local variables
		Map<Integer, BalanceSheetListBean> balanceSheetListBeanMap = new HashMap<Integer, BalanceSheetListBean>();
		List<Month> monthList = new ArrayList<Month>();
		def monthIdList = [3, 6, 9, 12];
		List<Integer> accountIdList = null;

		// build the month list
		for (int quarterId : monthIdList) {
			Integer monthId = (year * 100) + quarterId;
			monthList.add(Month.get(monthId));
		}

		// get the account list
		accountIdList = this.getAccountIdListForGroup(groupId);

		// build the balance sheet list beans
		for (Integer accountId : accountIdList) {
			// get the account balance sheet
			Account account = Account.get(accountId);

			// add it to the appropriate sheet bean
			if (balanceSheetListBeanMap.get(account.type.id) == null) {
				BalanceSheetListBean balanceSheetListBean = new BalanceSheetListBean();
				balanceSheetListBean.setMonthList(monthList);
				balanceSheetListBean.setAccountType(account.type)
				balanceSheetListBeanMap.put(account.type.id, balanceSheetListBean)
			}

			// add the months needed
			for (Month month : monthList) {
				AccountBalanceSheet sheet = AccountBalanceSheet.loadByAccountIdAndMonthId(account.id, month.id).get();
				balanceSheetListBeanMap.get(account.type.id).accountBalanceSheetList.add(sheet);
			}
		}

		// return
		return balanceSheetListBeanMap.values().toList()

	}

	/**
	 * returns all the account ids that are linked to a user part of a group
	 *
	 * @param group_id
	 * @return
     */
	public List<Integer> getAccountIdListForGroup(Integer group_id) {
		// local variables
		List<Integer> accountIdList = new ArrayList<Integer>();

		// create the sql string
		String sqlString = """
			select u.user_id, u.name, a.account_id, a.name, t.account_type_id, t.name, group_link.group_id
			from inv_user u, inv_account a, inv_account_type t, inv_user_group_link group_link
			where u.user_id = a.user_id
			  and a.account_type_id = t.account_type_id
			  and u.user_id = group_link.user_id
			order by t.account_type_id, u.user_id, group_link.group_id;
		"""

		// execute the sql
		def sql = new Sql(this.dataSource);
		sql.eachRow(sqlString) { row ->
			if (row.group_id == group_id) {
				accountIdList.add(row.account_id);
			}

		}

		// return
		return accountIdList;
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
