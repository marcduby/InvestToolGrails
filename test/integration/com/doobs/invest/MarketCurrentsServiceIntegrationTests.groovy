package com.doobs.invest

import grails.test.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class MarketCurrentsServiceIntegrationTests extends GroovyTestCase {
	Logger log = LoggerFactory.getLogger(MarketCurrentsServiceIntegrationTests)
	MarketCurrentsService marketCurrentsService
	
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testMarketCurrentsParse() {
		List<MarketCurrentsNewsCommand> newsList = this.marketCurrentsService?.parseMarketCurrents()
		assertNotNull "the news list should not be null", newsList
		
		// print the results
		newsList.each { MarketCurrentsNewsCommand news ->
			log.info "\tGot news: " +  news
		}
		assertTrue "the news list should not be empty", newsList?.size() > 0
		assertEquals 2, newsList?.size()
		
    }
}
