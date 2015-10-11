package com.doobs.invest

import static org.junit.Assert.*
import org.junit.*

class SqlServiceIntegrationTests {
	SqlService sqlService
	
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
		assertEquals 32, securityList.size()
    }
	
	@Test
	void testGetIndustryDiversificationList() {
		String accountId = "1"
		List<InvestDiversificationBean> beanList = this.sqlService?.getIndustryDiversificationList(accountId)
		
		// test results
		assertNotNull beanList
		assertEquals 11, beanList.size()
		
		// log
		beanList.each { InvestDiversificationBean bean ->
			log.info("got industry: " + bean?.name + " with total: " + bean?.amountTotal)
		}
	}
}
