package com.doobs.invest

import java.util.Date;

class TransactionType {
	Integer id
	String name
	String description
	
	// automatic variables
	Date lastUpdated
	Date dateCreated
	
    static constraints = {
    }
	
	static mapping = {
		table 'inv_transaction_type'
		id 				column: 'transaction_type_id'
		name 			column: 'name'
		description		column: 'description'
	}

}
