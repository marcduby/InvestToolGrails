package com.doobs.invest

import java.util.Date;

class Symbol {
	Integer id
	String symbol
	Security security
	
	// automatic variables
	Date lastUpdated
	Date dateCreated
	
	public String toString() {
		return symbol
	}
	
    static constraints = {
    }
	
	static mapping = {
		table 'inv_symbol'
		id 				column: 'symbol_id'
		symbol 			column: 'symbol'
		security 		column: 'security_id', fetch: 'join'
	}

}
