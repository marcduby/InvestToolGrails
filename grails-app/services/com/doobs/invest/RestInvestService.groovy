package com.doobs.invest

import com.doobs.invest.parser.IexTradingJsonParser
import grails.transaction.Transactional
import groovy.text.SimpleTemplateEngine

@Transactional
class RestInvestService {
	static transactional = true
		
	SqlService sqlService
	
	/**
	 * returns a rest call to the yahoo finance api
	 * 
	 * @param symbol
	 * @param queryParameter
	 * @return
	 */
    String getInvestQuoteString(String symbol, String queryParameter) {
		String URL = "http://query.yahooapis.com/v1/public/yql?q="
		String query = "select%20$queryParameter%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(%22$symbol%22)&env=store://datatables.org/alltableswithkeys"

		// make the rest call
		def oracle = (URL + query).toURL().text
		
		return oracle
    }

//	public InvestQuoteBean getInvestQuoteStringIexTRading(String symbol) {
//		String quoteURL = "http://api.iextrading.com/1.0/stock/" + symbol + "/quote";
//		def restBuilder = new RestBuilder();
//		InvestQuoteBean investQuoteBean = new InvestQuoteBean();
//
//		// make the call
//		def resp = restBuilder.get(quoteURL)
//
//		// get the json
//		JSONObject jsonObject = resp.json;
//
//		// build the bean
//		investQuoteBean.price = jsonObject.get("latestPrice");
//		investQuoteBean.symbol = symbol
//
//
//		// return
//		return investQuoteBean;
//	}

	/**
	 * get the security quote of a stock symbol
	 * 
	 * @param symbol
	 * @return
	 */
	InvestQuoteBean getInvestQuoteBeanOld(String symbol) {
		InvestQuoteBean bean = null
		String responseString = null
		
		// get the rest service string response
		responseString = this.getInvestQuoteString(symbol, "*")
		
		// parse the response and add to bean
		bean = this.parseQuoteBean(responseString)
		
		// return
		return bean
	}

	/**
	 * get the security quote of a stock symbol
	 *
	 * @param symbol
	 * @return
	 */
	InvestQuoteBean getInvestQuoteBean(String symbol) {
		InvestQuoteBean bean = new InvestQuoteBean();
		bean.symbol = symbol;
		IexTradingJsonParser iexTradingJsonParser = new IexTradingJsonParser();
		String tempString = null;

		// get the stock quote
		tempString = iexTradingJsonParser.getStockQuote(symbol);
		bean.price = tempString;

		// get the yearly dividend
		tempString = iexTradingJsonParser.getStockYearlyDividend(symbol)
		bean.yearlyDividend = tempString;

		// return
		return bean
	}

	/**
	 * parse a rest call response string
	 * 
	 * @param responseString
	 * @return
	 */
	InvestQuoteBean parseQuoteBean(String responseString) {
		InvestQuoteBean bean = new InvestQuoteBean()
		
		// parse the response and add to bean
		def query = new XmlSlurper().parseText(responseString)
		def quote = query.results.quote
		bean.yearlyDividend = quote.DividendShare.text()
		bean.symbol = quote.Symbol.text()
		bean.price = quote.LastTradePriceOnly.text()
		bean.date = quote.LastTradeDate.text()
		
		// return
		return bean
	}
	
	/**
	 * encode a string
	 * 
	 * @param inputString
	 * @return
	 */
	String urlEncodeString(String inputString) {
		String resultString = inputString.encodeAsURL()
		
		log.info "encoded string is: " + resultString
		
		return resultString
	}
	
	/**
	 * rest call with groovy template 
	 * 
	 * @param symbol
	 * @param queryParameter
	 * @return
	 */
    String getInvestQuoteStringWithTemplate(String symbol, String queryParameter) {
		String URL = "http://query.yahooapis.com/v1/public/yql?q="
		String query = "select%20$queryParameter%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(%22$symbol%22)&env=store://datatables.org/alltableswithkeys"

		def binding = ["symbol": symbol, "queryParameter": queryParameter]
		def engine = new SimpleTemplateEngine()
		log.info "binding map is: " + binding
		
		// make the binding
		String result = engine.createTemplate(URL + query).make(binding)
		log.info "query string is: " + result
		
		// make the rest call
		def oracle = (result).toURL().text
		
		return oracle
    }

	/**
	 * creates price quotes for the day's date for all securities that do not have any
	 * 
	 */
	void createPriceQuotesForToday() throws InvestException {
		List<Security> securityList = null
		
		// get the security id
		securityList = this.sqlService?.getSecurityListNeedingQuoteForDate(new Date())
		
		// for each security, get the price
		securityList.each { Security security ->
			try {
				this.getAndSaveSecurityPrice(security?.id)

			} catch (InvestException exception) {
				log.info("Got error for symbol: " + security?.currentSymbol + " so skipping: " + exception.getMessage())
			}
		}
	}
	
	/**
	 * gets the security proce from the rest service and saves it to the database
	 * 	
	 * @param securityId
	 * @return
	 * @throws InvestException
	 */
	Security getAndSaveSecurityPrice(Integer securityId) throws InvestException {
		Security security = Security.get(securityId)
		InvestQuoteBean bean = null

		// log
		log.info "looking for security price for symbol: " + security?.currentSymbol

		// get the invest bean for the security
		bean = this.getInvestQuoteBean(security?.currentSymbol?.symbol)
		
		// save the bean to the database
		SecurityPrice securityPrice = new SecurityPrice(price: (bean?.price ? bean?.price : 0.0), security: security, symbol: security?.currentSymbol, 
				yearlyDividend: (bean?.yearlyDividend ? bean?.yearlyDividend : 0.0), transactionDate: bean?.transactionDate)
		if (!securityPrice.save(flush: true)) {
			throw new InvestException("cound not save security price for security id: " + securityId + " with errors: " + securityPrice.errors)
		} 
		
		// modify the security as well
		security.currentPrice = securityPrice
		if (!security.save(flush: true)) {
			throw new InvestException("cound not save security for id: " + securityId)
		}
		
		// log
		log.info "saved security price : " + securityPrice.price + " for symbol: " + securityPrice?.symbol
		
		// return
		return security
	}
}
