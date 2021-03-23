package com.doobs.invest

import com.doobs.invest.bean.distribution.DistributionMonthBean
import com.doobs.invest.bean.distribution.DistributionTypeBean
import com.doobs.invest.bean.income.IncomeBean
import com.doobs.invest.bean.income.YearlyReportBean
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
	public List<BalanceSheetListBean> getMonthlyBalanceSheetsReport(Integer year, Integer groupId) {
		// local variables
		List< BalanceSheetListBean> balanceSheetListBeanList = new ArrayList<Integer>();
		List<Month> monthList = new ArrayList<Month>();
		def monthIdList = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];

		// build the month list
		for (int quarterId : monthIdList) {
			Integer monthId = null;
			if (quarterId == 0) {
				monthId = ((year - 1) * 100) + 12;

			} else {
				monthId = (year * 100) + quarterId;
				// add
				monthList.add(Month.get(monthId));
			}

		}

		// get the list
		balanceSheetListBeanList = this.getAccountSheetListByMonthList(monthList, groupId);

		// return
		return balanceSheetListBeanList;
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
		def monthIdList = [0, 3, 6, 9, 12];
		List<Integer> accountIdList = null;

		// build the month list
		for (int quarterId : monthIdList) {
			Integer monthId = null;
			if (quarterId == 0) {
				monthId = ((year - 1) * 100) + 12;
				// add
				monthList.add(Month.get(monthId));

			} else {
				monthId = (year * 100) + quarterId;
				// add
				monthList.add(Month.get(monthId));
			}

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

			// add the account id list
			balanceSheetListBeanMap.get(account.type.id).addToAccountIdList(accountId)
		}

		// return
		return balanceSheetListBeanMap.values().toList()

	}

	/**
	 * get the quarter list report
	 *
	 * @param year
	 * @return
	 */
	public List<DistributionTypeBean> getDistributionBalanceSheetsReport(Integer year, Integer groupId) {
		// local variables
		Map<String, DistributionTypeBean> balanceSheetListBeanMap = new HashMap<String, DistributionTypeBean>();
		List<Month> monthList = new ArrayList<Month>();
		def monthIdList = [0, 3, 6, 9, 12];
		List<Integer> accountIdList = null;

		// build the month list
		for (int quarterId : monthIdList) {
			Integer monthId = null;
			if (quarterId == 0) {
				monthId = ((year - 1) * 100) + 12;
				// add
				monthList.add(Month.get(monthId));

			} else {
				monthId = (year * 100) + quarterId;
				// add
				monthList.add(Month.get(monthId));
			}

		}

		// get the account list
		accountIdList = this.getAccountIdListForGroup(groupId);

		// create the list of account group types
		List<String> accountGroups = ["CD", "Bank", "Invest", "Retire"];

		// create the 4 map type lists
		for (String groupType : accountGroups) {
			DistributionTypeBean distributionTypeBean = new DistributionTypeBean();
			distributionTypeBean.setGroupType(groupType);
			distributionTypeBean.setMonthList(monthList)
			balanceSheetListBeanMap.put(groupType, distributionTypeBean);
		}

		// build the balance sheet list beans
		for (Integer accountId : accountIdList) {
			// get the account balance sheet
			Account account = Account.get(accountId);

			// add the months needed
			for (Month month : monthList) {
				AccountBalanceSheet sheet = AccountBalanceSheet.loadByAccountIdAndMonthId(account.id, month.id).get();

				// create a distrib month bean if applicable
				if (sheet.account.type.id == 4) {
					// if 401k, only retire entry
					DistributionMonthBean distributionMonthBean = new DistributionMonthBean();
					distributionMonthBean.month = sheet.month;
					distributionMonthBean.account = account;
					distributionMonthBean.totalBalance = sheet.totalBalance;
					balanceSheetListBeanMap.get("Retire").accountBalanceSheetList.add(distributionMonthBean);
					// add the account id list
					balanceSheetListBeanMap.get("Retire").addToAccountIdList(accountId)

				} else {
					if (true) {
						// one CD entry
						DistributionMonthBean distributionMonthBean = new DistributionMonthBean();
						distributionMonthBean.month = sheet.month;
						distributionMonthBean.account = account;
						distributionMonthBean.totalBalance = sheet.cdBalance;
						balanceSheetListBeanMap.get("CD").accountBalanceSheetList.add(distributionMonthBean);
						// add the account id list
						balanceSheetListBeanMap.get("CD").addToAccountIdList(accountId)
					}

					if (sheet.account.type.id == 3) {
						// if bank, then cash and CD entry
						DistributionMonthBean distributionMonthBean = new DistributionMonthBean();
						distributionMonthBean.month = sheet.month;
						distributionMonthBean.account = account;
						distributionMonthBean.totalBalance = sheet.totalBalance - sheet.cdBalance;
						balanceSheetListBeanMap.get("Bank").accountBalanceSheetList.add(distributionMonthBean);
						// add the account id list
						balanceSheetListBeanMap.get("Bank").addToAccountIdList(accountId)

					} else if (sheet.account.type.id == 1) {
						// if invest, then invest and CD entry
						DistributionMonthBean distributionMonthBean = new DistributionMonthBean();
						distributionMonthBean.month = sheet.month;
						distributionMonthBean.account = account;
						distributionMonthBean.totalBalance = sheet.totalBalance - sheet.cdBalance;
						balanceSheetListBeanMap.get("Invest").accountBalanceSheetList.add(distributionMonthBean);
						// add the account id list
						balanceSheetListBeanMap.get("Invest").addToAccountIdList(accountId)

					} else {
						// for all other ira, one ira and one CD entry
						DistributionMonthBean distributionMonthBean = new DistributionMonthBean();
						distributionMonthBean.month = sheet.month;
						distributionMonthBean.account = account;
						distributionMonthBean.totalBalance = sheet.totalBalance - sheet.cdBalance;
						balanceSheetListBeanMap.get("Retire").accountBalanceSheetList.add(distributionMonthBean);
						// add the account id list
						balanceSheetListBeanMap.get("Retire").addToAccountIdList(accountId)
					}
				}
			}

		}

		// return
		return balanceSheetListBeanMap.values().toList()

	}

	/**
	 * get the account sheet list by group and month list
	 *
	 * @param monthList
	 * @param groupId
     * @return
     */
	private List<BalanceSheetListBean> getAccountSheetListByMonthList(List<Month> monthList, Integer groupId) {
		// local variables
		Map<Integer, BalanceSheetListBean> balanceSheetListBeanMap = new HashMap<Integer, BalanceSheetListBean>();
		List<Integer> accountIdList = null;

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

			// add the account id list
			balanceSheetListBeanMap.get(account.type.id).addToAccountIdList(accountId)
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

	/**
	 * get the yearly income report
	 *
	 * @return
     */
	public YearlyReportBean getYearlyIncomeReport(Integer groupId) {
		// local variabls
		YearlyReportBean yearlyReportBean = new YearlyReportBean();

		// log
		log.info("looking for yearly income report for all accounts")

		// build the sql
		def sqlString = """
			select sum(balance.income) as income_total, account.account_id, account.name account_name,
			account_type.account_type_id, account_type.name account_type_name, balance.month_id div 100 as year_id, link.group_id
			from inv_balance_sheet balance, inv_account account, inv_month imonth, inv_account_type account_type, inv_user_group_link link
			where balance.account_id = account.account_id
			and balance.month_id = imonth.month_id
			and account.account_type_id = account_type.account_type_id
			and account.user_id = link.user_id
			and balance.month_id div 100 > 2007
			group by account.account_id, account_type.account_type_id, account.name, account_type.name, year_id, link.group_id
			order by account_type.name, account.name, year_id, link.group_id
		"""

		// log the sql string
		log.info("the sql is: " + sqlString)

		// execute the sql
		def sql = new Sql(dataSource)
		sql.eachRow(sqlString) { row ->
			if (row.group_id == groupId) {
				IncomeBean incomeBean = new IncomeBean();
				incomeBean.setAccountId(row.account_id);
				incomeBean.setAccountTypeId(row.account_type_id);
				incomeBean.setAccountName(row.account_name);
				incomeBean.setAccountTypeName(row.account_type_name)
				incomeBean.setYear(row.year_id);
				incomeBean.setIncomeTotal(row.income_total);

				// add to the yearly report
				yearlyReportBean.addIncomeBean(incomeBean);
			}
		}

		// return
		return yearlyReportBean;
	}

	/**
	 * get the yearly income report
	 *
	 * @return
	 */
	public YearlyReportBean getYearlyBalanceReport(Integer groupId) {
		// local variabls
		YearlyReportBean yearlyReportBean = new YearlyReportBean();

		// log
		log.info("looking for yearly income report for all accounts")

		// build the sql
		def sqlString = """
select balance.total_balance as total_balance, account.account_id, account.name account_name,
account_type.account_type_id, account_type.name account_type_name, balance.month_id, (balance.month_id div 100) as year_id,
link.group_id
from inv_balance_sheet balance, inv_account account, inv_month imonth, inv_account_type account_type, inv_user_group_link link
where balance.account_id = account.account_id
and balance.month_id = imonth.month_id
and account.account_type_id = account_type.account_type_id
and account.user_id = link.user_id
and balance.month_id div 100 > 2007
and balance.month_id div 100 < year(sysdate())
and balance.month_id % 100 = 12
order by link.group_id, account_type.name, account.name, year_id
		"""

		// log the sql string
		log.info("the sql is: " + sqlString)

		// execute the sql
		def sql = new Sql(dataSource)
		sql.eachRow(sqlString) { row ->
			if (row.group_id == groupId) {
				IncomeBean incomeBean = new IncomeBean();
				incomeBean.setAccountId(row.account_id);
				incomeBean.setAccountTypeId(row.account_type_id);
				incomeBean.setAccountName(row.account_name);
				incomeBean.setAccountTypeName(row.account_type_name)
				incomeBean.setYear(row.year_id);
				incomeBean.setBalanceTotal(row.total_balance);

				// add to the yearly report
				yearlyReportBean.addIncomeBean(incomeBean);
			}
		}

		// return
		return yearlyReportBean;
	}

	/**
	 * get the total transfer for an account for a year
	 *
	 * @return
	 */
	public BigDecimal getTotalTransfer(Integer accountId, Integer yearId) {
		// local variabls
		BigDecimal totalTransfer = null;

		// log
		log.info("looking for total transfer for an account " + accountId + " for a year " + yearId)

		// build the sql
		def sqlString = """
select sum(transfer) as total_transfer from inv_balance_sheet where account_id = :accountId and floor(month_id /100) = :yearId
		"""

		// log the sql string
		log.info("the sql is: " + sqlString)

		// execute the sql
		def sql = new Sql(dataSource)
		sql.eachRow(sqlString, [accountId: accountId, yearId: yearId]) { row ->
			totalTransfer = row.total_transfer
		}

		// return
		return totalTransfer;
	}

	/**
	 * get the total income for an account for a year
	 *
	 * @return
	 */
	public BigDecimal getTotalIncome(Integer accountId, Integer yearId) {
		// local variabls
		BigDecimal totalIncome = null;

		// log
		log.info("looking for total income for an account " + accountId + " for a year " + yearId)

		// build the sql
		def sqlString = """
select sum(income) as total_income from inv_balance_sheet where account_id = :accountId and floor(month_id /100) = :yearId
		"""

		// log the sql string
		log.info("the sql is: " + sqlString)

		// execute the sql
		def sql = new Sql(dataSource)
		sql.eachRow(sqlString, [accountId: accountId, yearId: yearId]) { row ->
			totalIncome = row.total_income
		}

		// return
		return totalIncome;
	}
}
