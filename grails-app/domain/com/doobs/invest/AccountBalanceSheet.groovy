package com.doobs.invest

class AccountBalanceSheet {
	Integer id
	Account account
	Month month
	Float totalBalance
	Float cashBalance
	Float income
	Float transfer

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
		totalBalance nullable:false
		cashBalance nullable:false
		income nullable:false
		transfer nullable:false
    }
	
	static transients = ['longName']

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
		income			column: 'income'
		transfer		column: 'transfer'
	}

}
