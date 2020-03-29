package com.doobs.invest.bean.stock;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mduby on 1/3/20.
 */
public class AlphaAdvantageStockBean implements Comparable<AlphaAdvantageStockBean> {
    // instance variables
    private String dateString;
    private Float openPrice;
    private Float closePrice;
    private Float highPrice;
    private Float lowPrice;
    private Float adjustedClosePrice;
    private Long volume;
    private Float dividendAmount;
    private Date date;
    private String symbol;

    public AlphaAdvantageStockBean() {
        super();
    }

    public AlphaAdvantageStockBean(String dateString, String symbol) {
        this.dateString = dateString;
        this.symbol = symbol;
    }

    public Date getDate() {
        Date tempDate = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        try {
            if (dateString != null) {
                tempDate = formatter.parse(this.dateString);
            }
        } catch (ParseException exception) {
            // do nothing
        }

        return tempDate;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public Float getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(Float openPrice) {
        this.openPrice = openPrice;
    }

    public Float getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(Float closePrice) {
        this.closePrice = closePrice;
    }

    public Float getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(Float highPrice) {
        this.highPrice = highPrice;
    }

    public Float getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(Float lowPrice) {
        this.lowPrice = lowPrice;
    }

    public Float getAdjustedClosePrice() {
        return adjustedClosePrice;
    }

    public void setAdjustedClosePrice(Float adjustedClosePrice) {
        this.adjustedClosePrice = adjustedClosePrice;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public Float getDividendAmount() {
        return dividendAmount;
    }

    public void setDividendAmount(Float dividendAmount) {
        this.dividendAmount = dividendAmount;
    }

    public String toString() {
        return this.symbol + " - " + this.closePrice + " - " + this.dateString;
    }

    public int compareTo(AlphaAdvantageStockBean other) {
        if (this.getDate() == null) {
            return 1;

        } else if (other.getDate() == null) {
            return -1;

        } else {
            return -1 * this.getDate().compareTo(other.getDate());
        }

    }
}
