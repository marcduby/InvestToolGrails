package com.doobs.invest

class MonthModelIntegrationTests extends GroovyTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testLoadByYear() {
		Integer yearId = 2017;
		List<Month> monthList = Month.loadByYear(yearId).list()
		assertEquals 12, monthList?.size()
    }
	
}
