package com.doobs.invest

import java.util.Date;

class Account {
	Integer id
	String name
	String description
	AccountType type
	User user
	
	// automatic variables
	Date lastUpdated
	Date dateCreated
	
	// transients
	String longName
	
	public String toString() {
		return this.getLongName()
	}
	
	public String getLongName() {
		return this.user?.name + " - " + this.name
	}
	
	public String getListName() {
		return this.user?.name?.substring(0, 1) + " - " + this.name
	}
	
    static constraints = {
		name nullable:false
		type nullable:false
		user nullable:false
    }
	
	static transients = ['longName']
	
	static mapping = {
		table 'inv_account'
		id 				column: 'account_id'
		name			column: 'name'
		description		column: 'description'
		user 			column: 'user_id', fetch: 'join'
		type 			column: 'account_type_id', fetch: 'join'
	}

}
