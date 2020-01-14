package com.doobs.invest.parser;

import com.doobs.invest.InvestException;
import com.doobs.invest.bean.stock.AlphaAdvantageStockBean;
import com.google.gson.JsonObject;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mduby on 7/17/18.
 */
public class AlphaAdvantageJsonParserTest extends TestCase {
    // instance variables
    private final Logger testLogger = Logger.getLogger(this.getClass().getName());

    @Test
    public void testGetStockQuoteJson() {
        // local varables
        JsonObject resultObject = null;
        AlpahAdvantageJsonParser alpahAdvantageJsonParser = new AlpahAdvantageJsonParser();

        // call for data
        try {
            resultObject = alpahAdvantageJsonParser.getStockInformationJson("IBM", true);

        } catch (InvestException exception) {
            fail("Got REST error retrieving stock quote: " + exception.getMessage());
        }

        // log
        this.testLogger.info(resultObject == null ? null : resultObject.toString());

        // test
        assertNotNull(resultObject);
    }

    @Test
    public void testGetDailyStockQuoteList() {
        // local variables
        List<AlphaAdvantageStockBean> alphaAdvantageStockBeanList = null;
        AlpahAdvantageJsonParser alpahAdvantageJsonParser = new AlpahAdvantageJsonParser();

        // get the list
        try {
            alphaAdvantageStockBeanList = alpahAdvantageJsonParser.getDailyStockQuoteList("AAPL");

        } catch (InvestException exception) {
            fail("Got REST error retrieving stock quote: " + exception.getMessage());
        }

        // test
        assertNotNull(alphaAdvantageStockBeanList);
        assertTrue(alphaAdvantageStockBeanList.size() > 0);

        // list
        for (AlphaAdvantageStockBean bean : alphaAdvantageStockBeanList) {
            System.out.println(bean);
        }
    }

    @Test
    public void testGetWeeklyStockQuoteList() {
        // local variables
        List<AlphaAdvantageStockBean> alphaAdvantageStockBeanList = null;
        AlpahAdvantageJsonParser alpahAdvantageJsonParser = new AlpahAdvantageJsonParser();

        // get the list
        try {
            alphaAdvantageStockBeanList = alpahAdvantageJsonParser.getWeeklyStockQuoteList("AAPL");

        } catch (InvestException exception) {
            fail("Got REST error retrieving stock quote: " + exception.getMessage());
        }

        // test
        assertNotNull(alphaAdvantageStockBeanList);
        assertTrue(alphaAdvantageStockBeanList.size() > 0);

        // list
        for (AlphaAdvantageStockBean bean : alphaAdvantageStockBeanList) {
            System.out.println(bean);
        }
    }

    @Test
    public void testGetFridayStockQuoteList() {
        // local variables
        List<AlphaAdvantageStockBean> alphaAdvantageStockBeanList = new ArrayList<AlphaAdvantageStockBean>();
        List<AlphaAdvantageStockBean> resultList = null;
        AlpahAdvantageJsonParser alpahAdvantageJsonParser = new AlpahAdvantageJsonParser();

        // create beans for the list
        AlphaAdvantageStockBean bean = new AlphaAdvantageStockBean("2000-04-20", "T");
        alphaAdvantageStockBeanList.add(bean);
        bean = new AlphaAdvantageStockBean("2002-08-09", "T");
        alphaAdvantageStockBeanList.add(bean);
        bean = new AlphaAdvantageStockBean("2005-10-07", "T");
        alphaAdvantageStockBeanList.add(bean);
        bean = new AlphaAdvantageStockBean("2020-01-13", "T");
        alphaAdvantageStockBeanList.add(bean);
        bean = new AlphaAdvantageStockBean("2019-11-22", "T");
        alphaAdvantageStockBeanList.add(bean);


        // get the list
        try {
            resultList = alpahAdvantageJsonParser.returnOnlyPreviousWeeklyPrices(alphaAdvantageStockBeanList);

        } catch (InvestException exception) {
            fail("Got REST error retrieving Friday stock quote: " + exception.getMessage());
        }

        // list
        for (AlphaAdvantageStockBean otherBean : resultList) {
            System.out.println(otherBean);
        }
        // test
        assertNotNull(resultList);
        assertEquals(5, alphaAdvantageStockBeanList.size());
        assertEquals(4, resultList.size());

    }

}
