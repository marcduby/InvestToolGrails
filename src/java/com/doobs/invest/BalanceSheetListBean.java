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
    private List<Integer> accountIdList = new ArrayList<Integer>();

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

    public List<Integer> getAccountIdList() {
        return accountIdList;
    }

    public void addToAccountIdList(Integer accountId) {
        if (!this.accountIdList.contains(accountId)) {
            this.accountIdList.add(accountId);
        }
    }

    /**
     * get the account balance sheet for the account id given and the year given
     *
     * @param monthId
     * @param accountId
     * @return
     */
    public AccountBalanceSheet getBalanceSheetByYearAndAccountId(Integer monthId, Integer accountId) {
        // local variables
        AccountBalanceSheet accountBalanceSheet = null;

        for (AccountBalanceSheet sheet : this.accountBalanceSheetList) {
            if ((monthId == sheet.getMonth().getId()) && (accountId == sheet.getAccount().getId())) {
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
    public BigDecimal getTotalBalance(Integer monthId) {
        // local variables
        BigDecimal total = new BigDecimal(0.0);

        // loop and get total
        for (AccountBalanceSheet sheet : this.accountBalanceSheetList) {
            if (sheet.getMonth().getId() == monthId) {
                total = total.add(sheet.getTotalBalance());
            }
        }

        // return
        return total;
    }

}
