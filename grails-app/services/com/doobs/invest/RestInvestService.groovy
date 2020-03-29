package com.doobs.invest

import com.doobs.invest.bean.stock.AlphaAdvantageStockBean
import com.doobs.invest.parser.AlpahAdvantageJsonParser
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
	void createPriceQuotesForToday(Integer securityId) throws InvestException {
		// for each security, get the price
		if (securityId == null) {
			List<Security> securityList = null

			// get the security id
			securityList = this.sqlService?.getSecurityListNeedingQuoteForDate(new Date())

			securityList.each { Security security ->
				try {
					this.getAndSaveWeeklySecurityPrice(security?.id)

				} catch (InvestException exception) {
					log.info("Got error for symbol: " + security?.currentSymbol + " so skipping: " + exception.getMessage())
				}
			}

		} else {
			Security security = Security.get(securityId)

			try {
				this.getAndSaveWeeklySecurityPrice(security?.id)

			} catch (InvestException exception) {
				log.info("Got error for symbol: " + security?.currentSymbol + " so skipping: " + exception.getMessage())
			}
		}


		// TODO - old and remove
//		securityList.each { Security security ->
//			try {
//				this.getAndSaveSecurityPrice(security?.id)
//
//			} catch (InvestException exception) {
//				log.info("Got error for symbol: " + security?.currentSymbol + " so skipping: " + exception.getMessage())
//			}
//		}


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

	/**
	 * gets the security weekly price from the rest service and saves it to the database
	 *
	 * @param securityId
	 * @return
	 * @throws InvestException
	 */
	Security getAndSaveWeeklySecurityPrice(Integer securityId) throws InvestException {
		Security security = Security.get(securityId)
		List<AlphaAdvantageStockBean> alphaAdvantageStockBeanList = null;
		AlpahAdvantageJsonParser alpahAdvantageJsonParser = new AlpahAdvantageJsonParser();
		String symbol = security?.currentSymbol

		// log
		log.info "looking for weekly security price for symbol: " + symbol

		// get the invest bean for the security
		alphaAdvantageStockBeanList = alpahAdvantageJsonParser.getWeeklyStockQuoteList(symbol)

		// log
		log.info("got ORIGINAL security weekly price list of size: " + alphaAdvantageStockBeanList.size())

		// trim, taking out this week
		alphaAdvantageStockBeanList = alpahAdvantageJsonParser.returnOnlyPreviousWeeklyPrices(alphaAdvantageStockBeanList)

		// log
		log.info("got TRIMMED security weekly price list of size: " + alphaAdvantageStockBeanList.size())

		// save the bean to the database
		int logInterval = 20;
		int count = 0;
		for (AlphaAdvantageStockBean bean : alphaAdvantageStockBeanList) {
			// find or create the bean
			SecurityWeeklyPrice securityWeeklyPrice = SecurityWeeklyPrice.findOrCreateBySecurityAndDateString(security, bean.dateString);

			if (securityWeeklyPrice.closePrice == null && securityWeeklyPrice.closePrice != bean.closePrice) {
				// update the bean
				securityWeeklyPrice.closePrice = bean.closePrice
				securityWeeklyPrice.openPrice = bean.openPrice
				securityWeeklyPrice.highPrice = bean.highPrice
				securityWeeklyPrice.lowPrice = bean.lowPrice
				securityWeeklyPrice.symbol = symbol
				securityWeeklyPrice.volume = bean.volume
				securityWeeklyPrice.adjustedClosePrice = bean.adjustedClosePrice
				securityWeeklyPrice.dividendAmount = bean.dividendAmount
				securityWeeklyPrice.weekDate = bean.date

				// save the bean
				if (!securityWeeklyPrice.save()) {
					throw new InvestException("could not save security weekly price for security id: " + securityId + " and date: " + bean.dateString + " with errors: " + securityPrice.errors)
				}
			}

			// log if needed
			count++;
			if (count % logInterval == 0) {
				log.info("Added " + count + " weekly price for symbol: " + symbol)
			}


		}
//		SecurityPrice securityPrice = new SecurityPrice(price: (bean?.price ? bean?.price : 0.0), security: security, symbol: security?.currentSymbol,
//				yearlyDividend: (bean?.yearlyDividend ? bean?.yearlyDividend : 0.0), transactionDate: bean?.transactionDate)

//		if (!securityPrice.save(flush: true)) {
//			throw new InvestException("cound not save security price for security id: " + securityId + " with errors: " + securityPrice.errors)
//		}

		// modify the security as well
//		security.currentPrice = securityPrice
//		if (!security.save(flush: true)) {
//			throw new InvestException("cound not save security for id: " + securityId)
//		}

		// log
		log.info "saved security weekly price list of size: " + alphaAdvantageStockBeanList.size() + " for symbol: " + symbol

		// get the latest 52 rows of weekly prices
		Collections.sort(alphaAdvantageStockBeanList);
		Float currentPrice = null;
		Float totalDividend = 0.0
		Date lastDate = null

		if (alphaAdvantageStockBeanList != null) {
			if (alphaAdvantageStockBeanList.size() > 0) {
				// set the current price as the most recent
				currentPrice = alphaAdvantageStockBeanList.get(0).closePrice
				lastDate = alphaAdvantageStockBeanList.get(0).date
			}

			// set the dividend to the sum of the last 52 entries
			int dividendCount = (alphaAdvantageStockBeanList.size() > 52 ? 52 : alphaAdvantageStockBeanList.size())

			for (int i = 0; i < dividendCount; i++) {
				if (alphaAdvantageStockBeanList.get(i).dividendAmount != null && alphaAdvantageStockBeanList.get(i).dividendAmount > 0) {
					Float beanDividend = (alphaAdvantageStockBeanList.get(i).dividendAmount == null ? 0 : alphaAdvantageStockBeanList.get(i).dividendAmount)
					totalDividend = totalDividend + beanDividend
				}
			}
		}

		// log
		log.info "setting security price : " + currentPrice + " for symbol: " + security?.currentSymbol + " with dividend: " + totalDividend + " and date: " + lastDate

		// save the bean to the database
		SecurityPrice securityPrice = new SecurityPrice(price: (currentPrice ? currentPrice : 0.0), security: security, symbol: security?.currentSymbol,
				yearlyDividend: (totalDividend ? totalDividend : 0.0), transactionDate: (lastDate ? lastDate : new Date()))

		if (!securityPrice.save(flush: true)) {
			throw new InvestException("could not save security price for security id: " + securityId + " with errors: " + securityPrice.errors)
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
