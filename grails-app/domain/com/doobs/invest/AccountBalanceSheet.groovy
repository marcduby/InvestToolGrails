package com.doobs.invest

class AccountBalanceSheet {
	Integer id
	Account account
	Month month
	BigDecimal totalBalance
	BigDecimal cashBalance
	BigDecimal income
	BigDecimal transfer
	BigDecimal cdBalance
	BigDecimal moneyMarket
	Boolean skip;

	// automatic variables
	Date lastUpdated
	Date dateCreated
	
	public String toString() {
		return this.getListName()
	}
	
	public String getListName() {
		return this.account?.name + " - " + this.month?.id
	}
	
    static constraints = {
		account nullable:false
		month nullable:false
		totalBalance nullable:false, scale: 2
		cashBalance nullable:false, scale: 2
		cdBalance nullable:false, scale: 2
		moneyMarket nullable:false, scale: 2
		income nullable:false, scale: 2
		transfer nullable:false, scale: 2
    }

	public String getMonthName() {
		Integer year = (int)(this.month?.id / 100);
		Integer month = this.month?.id % 100;
		String monthName = new java.text.DateFormatSymbols().months[month - 1] + " " + year.toString()
		return monthName
	}
	
	static transients = ['longName', 'monthName']

	static namedQueries = {
		loadByAccountId { Integer accountId ->
			account {
				eq 'id', accountId
			}
			month {
				order("id")
			}
		}

		loadByAccountIdAndMonthId { Integer accountId, Integer monthId ->
			account {
				eq 'id', accountId
			}
			month {
				eq 'id', monthId
			}
		}
	}

	static mapping = {
		table 'inv_balance_sheet'
		id 				column: 'balance_sheet_id'
		account 		column: 'account_id', fetch: 'join'
		month 			column: 'month_id', fetch: 'join'
		totalBalance	column: 'total_balance'
		cashBalance		column: 'cash_balance'
		cdBalance		column: 'cd_balance'
		moneyMarket		column: 'money_market'
		income			column: 'income'
		transfer		column: 'transfer'
		skip			column: 'skip'
	}

}
