package com.doobs.invest

import org.apache.commons.logging.LogFactory
import org.junit.After
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull


class SqlServiceIntegrationTests {
	SqlService sqlService
	def log = LogFactory.getLog(getClass())

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testGetSecurityListNeedingQuoteForDate() {
		List<Security> securityList = this.sqlService?.getSecurityListNeedingQuoteForDate(new Date())
		
		// test results
		assertNotNull securityList
		assertEquals 136, securityList.size()
    }
	
	@Test
	void testGetIndustryDiversificationList() {
		String accountId = "1"
		List<InvestDiversificationBean> beanList = this.sqlService?.getIndustryDiversificationList()
		
		// test results
		assertNotNull beanList
		assertEquals 12, beanList.size()
		
		// log
		beanList.each { InvestDiversificationBean bean ->
			log.info("got industry: " + bean?.name + " with total: " + bean?.amountTotal)
		}
	}

	@Test
	void testGetMonthsForYear() {
		List<Month> monthList

		monthList = this.sqlService?.getMonthsForYear(2017)
		assertNotNull monthList
		assertEquals 12, monthList.size()
	}

}
