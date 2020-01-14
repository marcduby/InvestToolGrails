package com.doobs.invest

class SecurityWeeklyPrice {
	Integer id
	String symbol
	String dateString
	Float openPrice
	Float closePrice
	Float lowPrice
	Float highPrice
	Float adjustedClosePrice
	Integer volume
	Float dividendAmount
	Date weekDate
	Security security

	// automatic variables
	Date lastUpdated
	Date dateCreated
	
    static constraints = {
		dateString nullable:false
		security nullable: false
		symbol nullable: false
    }

	static namedQueries = {
		loadBySecurityId { Integer securityId ->
			security {
				eq 'id', securityId
			}
		}

	}

	static mapping = {
		table 'inv_security_weekly_price'
		id 							column: 'security_weekly_id'
		dateString					column: 'date_string'
		symbol						column: 'symbol'
		openPrice					column: 'open_price'
		closePrice					column: 'close_price'
		lowPrice					column: 'low_price'
		highPrice					column: 'high_price'
		adjustedClosePrice			column: 'adjusted_close_price'
		volume						column: 'volume'
		dividendAmount				column: 'dividend_amount'
		weekDate					column: 'week_date'
		security 					column: 'security_id', fetch: 'join'
	}

}
