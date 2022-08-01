package com.doobs.invest.bean.balansheet;

import java.math.BigDecimal;
import com.doobs.invest.AccountBalanceSheet;

/**
 * Reporting class for account yearly summaries
 *
 * Created by mduby on 03/23/21.
 */
public class AccountYearSummaryBean {
    // instance variables
    private AccountBalanceSheet balanceSheet;
    private Integer year;
    private BigDecimal totalIncome = new BigDecimal(0.0);
    private BigDecimal totalTransfer = new BigDecimal(0.0);
    private BigDecimal totalGain = new BigDecimal(0.0);

    // default constructor
    public AccountYearSummaryBean(AccountBalanceSheet sheet) {
        this.balanceSheet = sheet;
    }
    
    public AccountBalanceSheet getBalanceSheet() {
        return balanceSheet;
    }

    public void setBalanceSheet(AccountBalanceSheet balanceSheet) {
        this.balanceSheet = balanceSheet;
    }

    public String getName() {
        return this.balanceSheet.getAccount().getName();
    }

    public String getMonth() {
        return this.balanceSheet.getMonth().getMonthName();
    }

    public Integer getYear() {
        return this.year;
    }

    public BigDecimal getTotalIncome() {
        return this.totalIncome;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }

    public BigDecimal getTotalTransfer() {
        return this.totalTransfer;
    }

    public void setTotalTransfer(BigDecimal totalTransfer) {
        this.totalTransfer = totalTransfer;
    }

    public BigDecimal getTotalGain() {
        return this.totalGain;
    }

    public void setTotalGain(BigDecimal totalGain) {
        this.totalGain = totalGain;
    }
}