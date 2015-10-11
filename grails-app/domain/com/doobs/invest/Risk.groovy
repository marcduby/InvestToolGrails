package com.doobs.invest

import java.util.Date;

class Risk {
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
		table 'inv_risk'
		id 				column: 'risk_id'
		name 			column: 'name'
		description		column: 'description'
	}

}
