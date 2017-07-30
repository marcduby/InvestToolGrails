package com.doobs.invest

import grails.transaction.Transactional

@Transactional
class AccountService {

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
}
