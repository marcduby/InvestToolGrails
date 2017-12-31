package com.doobs.invest

class Month {
	Integer id
	Date lastDayOfMonth

	// automatic variables
	Date lastUpdated
	Date dateCreated
	
	public Integer getMonthNumber() {
		return (id % 100)
	}

	public Integer getYear() {
		return (id / 100)
	}

	public String toString() {
		return id.toString()
	}
	
    static constraints = {
		lastDayOfMonth nullable:false
    }

	static namedQueries = {
		loadByYear { Integer year ->
			eq 'id' / 100, year
		}
	}

	static transients = ['year', 'monthNumber']
	
	static mapping = {
		table 'inv_month'
		id				column: 'month_id'
		lastDayOfMonth	column: 'lastMonthDay'
	}

}
