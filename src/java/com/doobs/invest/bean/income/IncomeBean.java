package com.doobs.invest.bean.income;

import java.math.BigDecimal;

/**
 * Bean for yearly reports
 *
 * Created by mduby on 3/4/19.
 */
public class IncomeBean {
    // instance variables
    private BigDecimal incomeTotal;
    private BigDecimal balanceTotal;
    private BigDecimal cashTotal;
    private BigDecimal transferTotal;
    private Long year;
    private Integer accountId;
    private Integer accountTypeId;
    private String accountName;
    private String accountTypeName;

    public BigDecimal getIncomeTotal() {
        return incomeTotal;
    }

    public void setIncomeTotal(BigDecimal incomeTotal) {
        this.incomeTotal = incomeTotal;
    }

    public BigDecimal getBalanceTotal() {
        return balanceTotal;
    }

    public void setBalanceTotal(BigDecimal balanceTotal) {
        this.balanceTotal = balanceTotal;
    }

    public BigDecimal getCashTotal() {
        return cashTotal;
    }

    public void setCashTotal(BigDecimal cashTotal) {
        this.cashTotal = cashTotal;
    }

    public BigDecimal getTransferTotal() {
        return transferTotal;
    }

    public void setTransferTotal(BigDecimal transferTotal) {
        this.transferTotal = transferTotal;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(Integer accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountTypeName() {
        return accountTypeName;
    }

    public void setAccountTypeName(String accountTypeName) {
        this.accountTypeName = accountTypeName;
    }
}
