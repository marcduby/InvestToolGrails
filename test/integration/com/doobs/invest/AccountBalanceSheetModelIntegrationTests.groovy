package com.doobs.invest

class AccountBalanceSheetModelIntegrationTests extends GroovyTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

	void testLoadByAccountIdAndYearNamedQuery() {
		List<AccountBalanceSheet> accountBalanceSheetList = AccountBalanceSheet.loadByAccountIdAndMonthId(12, 201702).list();
		assertEquals 1, accountBalanceSheetList?.size()
	}
}
