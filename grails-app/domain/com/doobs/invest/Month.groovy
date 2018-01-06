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

	public String getMonthName() {
		Integer year = (int)(this.id / 100);
		Integer month = this.id % 100;
		String monthName = new java.text.DateFormatSymbols().months[month - 1] + " " + year.toString()
		return monthName
	}

	static constraints = {
		lastDayOfMonth nullable:false
    }

	static namedQueries = {
		loadByYear { Integer year ->
			eq 'id' / 100, year
		}
	}

	static transients = ['year', 'monthNumber', 'monthName']
	
	static mapping = {
		table 'inv_month'
		id				column: 'month_id'
		lastDayOfMonth	column: 'lastMonthDay'
	}

}
