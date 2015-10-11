package com.doobs.invest

import java.util.Date;

class AccountType {
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
		table 'inv_account_type'
		id 				column: 'account_type_id'
		name 			column: 'name'
		description		column: 'description'
	}

}
