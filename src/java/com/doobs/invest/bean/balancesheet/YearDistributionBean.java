
package com.doobs.invest.bean.balancesheet;

import java.math.BigDecimal;
import com.doobs.invest.AccountBalanceSheet;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;



/**
 * class to get yearly totals for invest, bank, IRA and 401k totals
 *
 */
public class YearDistributionBean {
    // instance variables
    private Map<Integer, Map<String, BigDecimal>> yearTotalMap = new HashMap<Integer, Map<String, BigDecimal>>();
    private List<Integer> yearList = new ArrayList<Integer>();

    // constants
    public static final String BANK = "bank";
    public static final String IRA = "ira";
    public static final String INVEST = "invest";
    public static final String RETIREMENT = "retirement";
    public static final String ALL = "all";
    // public static final Integer START_YEAR = 1990;
    public static final Integer START_YEAR = 2000;

    public YearDistributionBean() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        // System.out.println("end year " + year);

        // build the map for each year
        for (int i = START_YEAR; i <= year; i++) {
            // add the year
            // System.out.println("adding year " + i);
            if (i < year) {
                this.yearList.add(i);
            }

            // create a total map entry
            Map<String, BigDecimal> assetMap = new HashMap<String, BigDecimal>();
            assetMap.put(BANK, new BigDecimal(0));
            assetMap.put(IRA, new BigDecimal(0));
            assetMap.put(RETIREMENT, new BigDecimal(0));
            assetMap.put(INVEST, new BigDecimal(0));
            assetMap.put(ALL, new BigDecimal(0));

            this.yearTotalMap.put(new Integer(i), assetMap);
        }
    }


    public void addToAccount(String type, int year, BigDecimal amount) {
        this.yearTotalMap.get(year).put(type, this.yearTotalMap.get(year).get(type).add(amount));
        this.yearTotalMap.get(year).put(ALL, this.yearTotalMap.get(year).get(ALL).add(amount));
    }

    public BigDecimal getAccountTotal(String type, int year) {
        return this.yearTotalMap.get(year).get(type);
    }

    public List<Integer> getYearList() {
        return this.yearList;
    }
}