package com.doobs.invest

class Security {
	Integer id
	String name
	String description
	Symbol currentSymbol
	Industry industry
	SecurityPrice currentPrice
	
	// automatic variables
	Date lastUpdated
	Date dateCreated
	
	public String toString() {
		return name
	}
	
	static namedQueries = {
		listOrderedByName {
			order("name")
		}
	}
	
    static constraints = {
		currentSymbol nullable: true
		industry nullable: true
		currentPrice nullable: true
    }
	
	static mapping = {
//		autoTimestamp true
		table 'inv_security'
		id 				column: 'security_id'
		name 			column: 'name'
		description 	column: 'description'
		currentSymbol	column: 'current_symbol_id', fetch: 'join'
		industry		column: 'industry_id', fetch: 'join'
		currentPrice	column: 'current_price_id', fetch: 'join'
	}
}
