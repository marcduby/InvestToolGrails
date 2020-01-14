package com.doobs.invest

class SecurityWeeklyPriceModelIntegrationTests extends GroovyTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

	void testLoadBySecurityIdNamedQuery() {
		List<SecurityWeeklyPrice> securityWeeklyPriceList = SecurityWeeklyPrice.loadBySecurityId(17).list();
		assertEquals 1043, securityWeeklyPriceList?.size()
	}

    void testLoadBySecurityIdNamedQuerySorted() {
        List<SecurityWeeklyPrice> securityWeeklyPriceList = SecurityWeeklyPrice.loadBySecurityId(17).order("weekDate", "desc").list();
        assertEquals 1043, securityWeeklyPriceList?.size()
    }
}
