package com.doobs.invest

import java.util.Date;

class News {
	Integer id
	String text
	String comment
	
	// automatic variables
	Date lastUpdated
	Date dateCreated
	
    static constraints = {
    }

	static mapping = {
		table 'inv_news'
		id 				column: 'news_id'
		text			column: 'text'
		comment			column: 'comment'
	}

}
