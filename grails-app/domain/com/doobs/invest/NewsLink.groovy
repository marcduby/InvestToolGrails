package com.doobs.invest

import java.util.Date;

class NewsLink {
	Integer id
	News news
	Security security

	// automatic variables
	Date lastUpdated
	Date dateCreated
	
    static constraints = {
    }
	
	static mapping = {
		table 'inv_news_link'
		id 				column: 'news_link_id'
		news	 		column: 'news_id', fetch: 'join'
		security 		column: 'security_id', fetch: 'join'
	}
}
