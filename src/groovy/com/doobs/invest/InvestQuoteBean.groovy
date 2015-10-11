package com.doobs.invest

class InvestQuoteBean {
	
	String symbol
	String date
	String yearlyDividend
	String price

	Date getTransactionDate() {
		Date transactionDate
		
		if (this.date) {
			transactionDate = Date.parse("MM/dd/yyyy", date)
		} else {
			transactionDate = new Date()
		}
		
		return transactionDate
	}
}
