package com.doobs.invest

import static org.junit.Assert.*
import org.junit.*

class AccountServiceIntegrationTests {
	AccountService accountService
	
    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testGetHoldingList() {
        List<Holding> holdingList
		Integer accountId = 1
		
		holdingList = this.accountService?.getAccountHoldingList(accountId)
		assertNotNull holdingList
		assertEquals 2, holdingList?.size()
    }
	
	@Test
	void testgetSecurityOrderedList() {
		List<Security> securityList
		
		securityList = this.accountService?.getSecurityOrderedList()
		assertNotNull securityList
		assertEquals 17, securityList.size()
	}
	
	@Test
	void testGetHoldingListOrderedByAccountIndustryAndName() {
		List<Holding> holdingList
		
		holdingList = this.accountService?.getHoldingListOrderedByAccountIndustryAndName()
		assertNotNull holdingList
		assertEquals 2, holdingList.size()
	}
}
