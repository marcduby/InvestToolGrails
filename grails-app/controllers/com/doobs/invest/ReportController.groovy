package com.doobs.invest

class ReportController {
	SqlService sqlService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def industryReport() {
		String accountId = params.accountId
		List<InvestDiversificationBean> beanList
		
		if (accountId) {
			beanList = this.sqlService?.getIndustryDiversificationListByAccount(accountId)
			
		} else {
			beanList = this.sqlService?.getIndustryDiversificationList()
		}
		
		// get account list
		List<Account> accountList = Account.list()
		
		// totals
		Float totalAmount = 0.0
		Float totalDividend = 0.0
		beanList.each { InvestDiversificationBean bean ->
			totalAmount = totalAmount + bean?.amountTotal
			totalDividend = totalDividend + bean?.dividendTotal
		}
		
		// render
		render(view: "list", model: [diversificationType: "Industry", beanList: beanList, accountList: accountList, totalAmount: totalAmount, totalDividend: totalDividend])
	}
}
