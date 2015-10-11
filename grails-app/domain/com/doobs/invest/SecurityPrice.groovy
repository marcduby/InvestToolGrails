package com.doobs.invest

import java.util.Date;

class SecurityPrice {
	Integer id
	Date transactionDate
	Float price
	Float yearlyDividend
	Security security
	Symbol symbol
	
	// automatic variables
	Date lastUpdated
	Date dateCreated
	
    static constraints = {
		price nullable:false
		security nullable: false
		symbol nullable: false
    }
	
	static mapping = {
		table 'inv_security_price'
		id 				column: 'security_price_id'
		transactionDate	column: 'transaction_date'
		price			column: 'price'
		yearlyDividend	column: 'yearly_dividend'
		symbol 			column: 'symbol_id', fetch: 'join'
		security 		column: 'security_id', fetch: 'join'
	}

}
