package com.doobs.invest.bean.distribution;

import com.doobs.invest.AccountType;
import com.doobs.invest.Month;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mduby on 8/11/18.
 */
public class DistributionTypeBean {
    // instance variables
    private List<DistributionMonthBean> accountBalanceSheetList = new ArrayList<DistributionMonthBean>();
    private AccountType accountType;
    private List<Month> monthList;
    private List<Integer> accountIdList = new ArrayList<Integer>();
    private String groupType;

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
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
    public DistributionMonthBean getBalanceSheetByYearAndAccountId(Integer monthId, Integer accountId) {
        // local variables
        DistributionMonthBean accountBalanceSheet = null;

        for (DistributionMonthBean sheet : this.accountBalanceSheetList) {
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
        for (DistributionMonthBean sheet : this.accountBalanceSheetList) {
            if (sheet.getMonth().getId() == monthId) {
                total = total.add(sheet.getTotalBalance());
            }
        }

        // return
        return total;
    }


    public List<DistributionMonthBean> getAccountBalanceSheetList() {
        return accountBalanceSheetList;
    }

    public void setAccountBalanceSheetList(List<DistributionMonthBean> accountBalanceSheetList) {
        this.accountBalanceSheetList = accountBalanceSheetList;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
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

    public void setAccountIdList(List<Integer> accountIdList) {
        this.accountIdList = accountIdList;
    }
}
