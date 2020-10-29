package com.doobs.invest

import grails.transaction.Transactional

@Transactional
class AccountService {
	SqlService sqlService

	/**
	 * get the holding list for the account
	 * 
	 * @param accountId
	 * @return
	 */
    List<Holding> getAccountHoldingList(Integer accountId) {
		List<Holding> holdingList = null
		
		// get the holding for the account id
		holdingList = Holding.loadByAccountId(accountId).list()
		log.info "found ${holdingList?.size()} holdings for account ${accountId}"
		
		// sort
		Collections.sort(holdingList)
		
		// return
		return holdingList
    }
	
	/**
	 * return list of securities ordered by name
	 * 
	 * @return
	 */
	List<Security> getSecurityOrderedList() {
		return Security.listOrderedByName().list()
	}
	
	/**
	 * return a sorted list of holdings
	 * 
	 * @return
	 */
	List<Holding> getHoldingListOrderedByAccountIndustryAndName() {
		List<Holding> holdingList = Holding.list()
		Collections.sort(holdingList)
		return holdingList
	}

	/**
	 * return a sorted list of balance sheet ordered by month
	 *
	 * @return
	 */
	List<Holding> getBalanceSheetListOrderedByMonth(Integer accountId) {
		List<AccountBalanceSheet> balanceSheetList = AccountBalanceSheet.loadByAccountId(accountId).list()
		return balanceSheetList
	}

	/**
	 * get the balance sheet rows for an account and year
	 *
	 * @param accountId
	 * @param year
     * @return
     */
	List<AccountBalanceSheet> getOrCreateAccountBalanceSheetList(int accountId, int year) {
		// local variables
		List<AccountBalanceSheet> accountBalanceSheetList = new ArrayList<AccountBalanceSheet>();
		List<Month> monthList = null;

		// get the list of months for the year
		monthList = this.sqlService.getMonthsForYear(year);

		// get the account
		Account account = Account.get(accountId);

		// get each balance sheet for the month for the account
		for (Month month : monthList) {
			AccountBalanceSheet accountBalanceSheet = AccountBalanceSheet.loadByAccountIdAndMonthId(accountId, month?.id)?.get();

			if (accountBalanceSheet == null) {
				// if not exists, create and save
				accountBalanceSheet = new AccountBalanceSheet();
				accountBalanceSheet.setAccount(account);
				accountBalanceSheet.setMonth(month);
				accountBalanceSheet.setCashBalance(0.0);
				accountBalanceSheet.setCdBalance(0.0);
				accountBalanceSheet.setMoneyMarket(0.0);
				accountBalanceSheet.setTotalBalance(0.0);
				accountBalanceSheet.setIncome(0.0);
				accountBalanceSheet.setTransfer(0.0);
				accountBalanceSheet.setSkip(false);

				// log
				log.info("Create account balance sheet for account: " + accountId + " and month: " + month.id)

				// save
				accountBalanceSheet.save(flush: true, failOnError: true)

			} else {
				// log
				log.info("Load existing account balance sheet for account: " + accountId + " and month: " + month.id)
			}


			// add to list
			accountBalanceSheetList.add(accountBalanceSheet);
		}

		// return
		return accountBalanceSheetList;
	}
}
