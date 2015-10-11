package com.doobs.invest

import java.util.Date;

class Industry {
	Integer id
	String name
	String description
	
	// automatic variables
	Date lastUpdated
	Date dateCreated
	
	public String toString() {
		return this.name;
	}
	
    static constraints = {
		name nullable:false
    }
	
	static mapping = {
		table 'inv_industry'
		id 				column: 'industry_id'
		name 			column: 'name'
		description		column: 'description'
	}

}
