package com.doobs.invest

import org.junit.After
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.*

class OldRestInvestServiceIntegrationTests {
	RestInvestService restInvestService
	
	@Before
	void setUp() {
		// Setup logic here
	}

	@After
	void tearDown() {
		// Tear down logic here
	}

	@Test
	void testGetInvestQuoteString() {
		String symbol = "msft"
		String queryParameter = "*"
		String responseString = null
		
		// get the response
		responseString = this.restInvestService?.getInvestQuoteString(symbol, queryParameter)
		
		// test the response
		assertNotNull responseString
		
		log.info "got response: " + responseString
	}


	@Test
	void testGetInvestQuoteStringIexTrading() {
		String symbol = "msft"
		InvestQuoteBean investQuoteBean = null

		// get the response
		investQuoteBean = this.restInvestService?.getInvestQuoteStringIexTRading(symbol)

		// test the response
		assertNotNull investQuoteBean

		log.info "got response: " + investQuoteBean
	}


	@Test
	void testGetInvestDividendQuoteString() {
		String symbol = "msft"
		String queryParameter = "DividendShare"
		String responseString = null
		
		// get the response
		responseString = this.restInvestService?.getInvestQuoteString(symbol, queryParameter)
		
		// test the response
		assertNotNull responseString
		
		log.info "got response: " + responseString
	}
	
	@Test
	void testEncodeString() {
		String testString = "dude man"
		String resultString = this.restInvestService.urlEncodeString(testString)
		
		// test
		assertNotNull resultString
		assertEquals "dude+man", resultString
	}
	
	@Test
	void testParseQuoteBean() {
		def string = '''
			<query xmlns:yahoo="http://www.yahooapis.com/v1/base.rng" yahoo:count="1" yahoo:created="2015-02-09T19:09:25Z" yahoo:lang="en-US">
			<results><quote><DividendShare>1.15</DividendShare><LastTradeDate>2/9/2015</LastTradeDate><Symbol>FGP</Symbol></quote></results></query><!-- total: 797 -->
		'''
		InvestQuoteBean bean = null
		
		// parse the bean
		bean = this.restInvestService?.parseQuoteBean(string)
		
		// test
		assertNotNull bean
		assertEquals "1.15", bean.yearlyDividend
		assertEquals "FGP", bean.symbol
		assertEquals "2015-02-09", bean.transactionDate.format("yyyy-MM-dd")
		
		// log
		log.info "the symbol: " + bean?.symbol + " has dividend of: " + bean?.yearlyDividend
		
	}
	
	@Test
	void testGetInvestQuoteBean() {
		String symbol = "MSFT"
		InvestQuoteBean bean = null
		
		// parse the bean
		bean = this.restInvestService?.getInvestQuoteBean(symbol)
		
		// test
		assertNotNull bean
		assertEquals "1.68", bean.yearlyDividend
		assertEquals "MSFT", bean.symbol
		assertEquals "105.95", bean.price
		
		// log
		System.out.println("the symbol: " + bean?.symbol + " has dividend of: " + bean?.yearlyDividend + " and a price: " + bean?.price)
		
	}
	
	@Test
	void testgetAndSaveSecurityPrice() {
		Integer securityId = 17
		List<SecurityPrice> priceList = null
		Integer count = 0
		
		// get the security price list
		priceList = SecurityPrice.list()
		// test
		assertNotNull priceList
		count = priceList.size()
		
		// get and store the security price
		this.restInvestService?.getAndSaveSecurityPrice(securityId)
		
		// test count again
		priceList = SecurityPrice.list()
		// test
		assertNotNull priceList
		assertTrue priceList?.size() > count
	}
	
	@Test
	void testCreatePriceQuotesForToday() {
		try {
			this.restInvestService?.createPriceQuotesForToday();
			
		} catch (InvestException exception) {
			fail "daily proice quotes had error: " + exception.getMessage()
		}
	}

}
