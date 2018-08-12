package com.doobs.invest.bean.distribution;

import com.doobs.invest.Account;
import com.doobs.invest.Month;

import java.math.BigDecimal;

/**
 * Created by mduby on 8/11/18.
 */
public class DistributionMonthBean {
    // instance variables
    Account account;
    Month month;
    BigDecimal totalBalance;

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }
}
