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

	@Test
	void testGetUserAndAccountsList() {
		List<AccountUserBean> accountUserBeanList = null;

		accountUserBeanList = this.sqlService?.getUserAndAccountsList()
		assertNotNull accountUserBeanList
		assertEquals 4, accountUserBeanList.size()
	}

	@Test
	void testGetAccountIdListForGroup() {
		// local variables
		Integer groupId = 1;
		List<Integer> accountIdList = null;

		// get the account id list
		accountIdList = this.sqlService.getAccountIdListForGroup(groupId);

		// test
		assertNotNull(accountIdList)
		assertEquals(15, accountIdList.size())
	}

	@Test
	void testGetQuarterlyBalanceSheetsReport() {
		// local variables
		List<BalanceSheetListBean> balanceSheetListBeanList = null;
		Integer year = 2017;
		Integer groupId = 1;

		// get the list
		balanceSheetListBeanList = this.sqlService.getQuarterlyBalanceSheetsReport(year, groupId);

		// return
		assertNotNull(balanceSheetListBeanList);
		assertEquals(4, balanceSheetListBeanList.size())
	}

	@Test
	void testGetMonthlyBalanceSheetsReport() {
		// local variables
		List<BalanceSheetListBean> balanceSheetListBeanList = null;
		Integer year = 2017;
		Integer groupId = 1;

		// get the list
		balanceSheetListBeanList = this.sqlService.getMonthlyBalanceSheetsReport(year, groupId);

		// return
		assertNotNull(balanceSheetListBeanList);
		assertEquals(4, balanceSheetListBeanList.size())
	}
}
