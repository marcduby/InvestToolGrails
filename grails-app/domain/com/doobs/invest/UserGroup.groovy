package com.doobs.invest

class UserGroup {
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
		table 'inv_user_group'
		id 				column: 'user_group_id'
		name 			column: 'name'
		fullName		column: 'description'
	}

}
