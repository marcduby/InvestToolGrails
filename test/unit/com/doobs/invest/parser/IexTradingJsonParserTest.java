package com.doobs.invest.parser;

import com.doobs.invest.InvestException;
import com.google.gson.JsonObject;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * Created by mduby on 7/17/18.
 */
public class IexTradingJsonParserTest extends TestCase {
    private final Logger testLogger = Logger.getLogger(this.getClass().getName());

    @Test
    public void testGetStockQuoteJson() {
        // local varables
        JsonObject resultObject = null;
        IexTradingJsonParser iexTradingJsonParser = new IexTradingJsonParser();
        String url = "https://api.iextrading.com/1.0/stock/" + "IBM" + "/quote/1y";

        // get the object
        resultObject = iexTradingJsonParser.getStockInformationJson(url);

        // test
        assertNotNull(resultObject);
    }

    @Test
    public void testGetStockQuote() {
        // local variables
        String symbol = "IBM";
        String quote = null;
        IexTradingJsonParser iexTradingJsonParser = new IexTradingJsonParser();

        // get the stock quote
        try {
            quote = iexTradingJsonParser.getStockQuote(symbol);

        } catch (InvestException exception) {
            fail("Got error retrieving stock quote: " + exception.getMessage());
        }

        // test
        assertNotNull(quote);
        assertTrue(quote.length() > 0);

        // log
        String message = "Got stock quote: " + quote;
        System.out.println(message);
    }

    @Test
    public void testGetStockDividend() {
        // local variables
        String symbol = "IBM";
        String yearlyDividend = null;
        IexTradingJsonParser iexTradingJsonParser = new IexTradingJsonParser();

        // get the stock quote
        try {
            yearlyDividend = iexTradingJsonParser.getStockYearlyDividend(symbol);

        } catch (InvestException exception) {
            fail("Got error retrieving yearly dividend: " + exception.getMessage());
        }

        // test
        assertNotNull(yearlyDividend);
        assertTrue(yearlyDividend.length() > 0);

        // log
        String message = "Got yearly dividend: " + yearlyDividend;
        System.out.println(message);
    }

}
