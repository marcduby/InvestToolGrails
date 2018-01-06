package com.doobs.invest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mduby on 1/4/18.
 */
public class BalanceSheetListBean {
    // instance variables
    private List<AccountBalanceSheet> accountBalanceSheetList = new ArrayList<AccountBalanceSheet>();
    private AccountType accountType;
    private List<Month> monthList;

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public void addToAccountBalanceSheetList(AccountBalanceSheet accountBalanceSheet) {
        this.accountBalanceSheetList.add(accountBalanceSheet);
    }

    public List<AccountBalanceSheet> getAccountBalanceSheetList() {
        return accountBalanceSheetList;
    }

    public List<Month> getMonthList() {
        return monthList;
    }

    public void setMonthList(List<Month> monthList) {
        this.monthList = monthList;
    }

    /**
     * get the account balance sheet for the account id given and the year given
     *
     * @param year
     * @param accountId
     * @return
     */
    public AccountBalanceSheet getBalanceSheetByYearAndAccountId(Integer year, Integer accountId) {
        // local variables
        AccountBalanceSheet accountBalanceSheet = null;

        for (AccountBalanceSheet sheet : this.accountBalanceSheetList) {
            if ((year == sheet.getMonth().getYear()) && (accountId == sheet.getId())) {
                accountBalanceSheet = sheet;
                break;
            }
        }

        // return
        return accountBalanceSheet;
    }

    /**
     * return the total balance of all the accounts
     *
     * @return
     */
    public BigDecimal getTotalBalance() {
        // local variables
        BigDecimal total = new BigDecimal(0.0);

        // loop and get total
        for (AccountBalanceSheet sheet : this.accountBalanceSheetList) {
            total = total.add(sheet.getTotalBalance());
        }

        // return
        return total;
    }

}
