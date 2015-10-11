package com.doobs.invest

import java.util.Date;

class User {
	Integer id
	String name
	String fullName
	
	// automatic variables
	Date lastUpdated
	Date dateCreated
	
	public String toString() {
		return name
	}
	
    static constraints = {
		name nullable:false
		fullName nullable:true
    }
	
	static mapping = {
		table 'inv_user'
		id 				column: 'user_id'
		name 			column: 'name'
		fullName		column: 'full_name'
	}

}
