package com.doobs.invest

class InvestModelIntegrationTests extends GroovyTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testSecurityModel() {
		List<Security> securityList = Security.findAll()
		assertEquals 136, securityList?.size()
    }
	
	void testSymbolModel() {
		List<Symbol> symbolList = Symbol.findAll()
		assertEquals 138, symbolList?.size()
	}
	
	void testTransactionModel() {
		List<Transaction> transactionList = Transaction.findAll()
		assertEquals transactionList?.size(), 0
	}
	
	void testTransactionTypeModel() {
		List<TransactionType> transactionTypeList = TransactionType.findAll()
		assertEquals transactionTypeList?.size(), 0
	}
	
	void testNewsModel() {
		List<News> newsList = News.findAll()
		assertEquals newsList?.size(), 0
	}

	void testNewsLinkModel() {
		List<NewsLink> newsLinkList = NewsLink.list()
		assertEquals newsLinkList?.size(), 0
	}
	
	void testHoldingModel() {
		List<Holding> holdingList = Holding.list()
		assertEquals 155, holdingList?.size()
	}
}
