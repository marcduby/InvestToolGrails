package com.doobs.invest

import java.util.Date;

class Transaction {
	Integer id
	String description
	Date transactionDate
	Float amount
	Float price
	Security security
	Symbol symbol
	TransactionType type
	Account account
	
	// automatic variables
	Date lastUpdated
	Date dateCreated
	
    static constraints = {
		price nullable:false
		amount nullable:false
    }
	
	static mapping = {
		table 'inv_transaction'
		id 				column: 'transaction_id'
		description		column: 'description'
		transactionDate	column: 'transaction_date'
		amount			column: 'amount'
		price			column: 'price'
		symbol 			column: 'symbol_id', fetch: 'join'
		security 		column: 'security_id', fetch: 'join'
		account			column: 'account_id', fetch: 'join'
		type			column: 'transaction_type_id', fetch: 'join'
	}

}
