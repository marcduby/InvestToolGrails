package com.doobs.invest.bean.income;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Bean to help with yearly reports
 *
 * Created by mduby on 3/4/19.
 */
public class YearlyReportBean {
    // instance variables
    Map<Integer, Map<Long, IncomeBean>> mapYearlyBeanListByAccount = new HashMap<Integer, Map<Long, IncomeBean>>();
    Set<Long> yearSet = new HashSet<Long>();
    Map<Integer, String> accountTypeMap = new HashMap<Integer, String>();
    Map<Integer, List<Integer>> accountMapByAccountType = new HashMap<Integer, List<Integer>>();
    Map<Integer, String> accountNameMap = new HashMap<Integer, String>();

    /**
     * add an income bean
     *
     * @param incomeBean
     */
    public void addIncomeBean(IncomeBean incomeBean) {
        if (this.mapYearlyBeanListByAccount.get(incomeBean.getAccountId()) == null) {
            this.mapYearlyBeanListByAccount.put(incomeBean.getAccountId(), new HashMap<Long, IncomeBean>());
        }

        // add the bean
        this.mapYearlyBeanListByAccount.get(incomeBean.getAccountId()).put(incomeBean.getYear(), incomeBean);

        // add year as well
        this.yearSet.add(incomeBean.getYear());

        // add account type
        this.accountTypeMap.put(incomeBean.getAccountTypeId(), incomeBean.getAccountTypeName());

        // add the account to the account type map list
        if (this.accountMapByAccountType.get(incomeBean.getAccountTypeId()) == null) {
            this.accountMapByAccountType.put(incomeBean.getAccountTypeId(), new ArrayList<Integer>());
        }
        if (!this.accountMapByAccountType.get(incomeBean.getAccountTypeId()).contains(incomeBean.getAccountId())) {
            this.accountMapByAccountType.get(incomeBean.getAccountTypeId()).add(incomeBean.getAccountId());
        }

        // add the account name
        this.accountNameMap.put(incomeBean.getAccountId(), incomeBean.getAccountName());
    }

    /**
     * get the income bean for the year and account
     *
     * @param accountId
     * @param year
     * @return
     */
    public IncomeBean getIncomeBeanByAccountYear(Integer accountId, Long year) {
        // local variable
        IncomeBean incomeBean = null;

        if (this.mapYearlyBeanListByAccount.get(accountId) != null) {
            incomeBean = this.mapYearlyBeanListByAccount.get(accountId).get(year);
        }

        // return
        return incomeBean;
    }

    /**
     * return the account type id list
     *
     * @return
     */
    public List<Integer> getAccountTypeList() {
        return new ArrayList<Integer>(this.accountTypeMap.keySet());
    }

    /**
     * get the accpunt type name
     *
     * @param accountTypeId
     * @return
     */
    public String getAccountTypeName(Integer accountTypeId) {
        return this.accountTypeMap.get(accountTypeId);
    }

    /**
     * get the account name
     *
     * @param accountId
     * @return
     */
    public String getAccountName(Integer accountId) {
        return this.accountNameMap.get(accountId);
    }

    /**
     * return the year list
     *
     * @return
     */
    public List<Long> getYearList() {
        List<Long> yearList = new ArrayList<Long>(this.yearSet);
        Collections.sort(yearList);
        return yearList;
    }

    /**
     * return the account list by type
     *
     * @param accountTypeId
     * @return
     */
    public List<Integer> getAccountListByType(Integer accountTypeId) {
        return this.accountMapByAccountType.get(accountTypeId);
    }

    /**
     * get the income total by year
     *
     * @param yearId
     * @param accountTypeId
     * @return
     */
    public BigDecimal getTotalIncomeByYearAndAcountType(Long yearId, Integer accountTypeId) {
        BigDecimal total = new BigDecimal(0);

        // iterate through the map values
        Iterator<Map<Long, IncomeBean>> mapIterator = this.mapYearlyBeanListByAccount.values().iterator();
        while (mapIterator.hasNext()) {
            Map<Long, IncomeBean> item = mapIterator.next();

            // add if account type matches
            // TODO - must have added this to troubleshoot null issue; can remve later?
            if (accountTypeId != null) {
                if (item.get(yearId) == null) {
                    String dude = "test";
                }

                // adding check that accoynt has rows for that year
                IncomeBean bean = item.get(yearId);
                if (bean != null) {
                    if (bean.getAccountTypeId().equals(accountTypeId)) {
                        total = total.add(bean.getIncomeTotal());

                    }
                }

            } else {
                IncomeBean bean = item.get(yearId);
                if (bean != null) {
                    total = total.add(bean.getIncomeTotal());
                }
            }
        }

        // return
        return total;
    }

    /**
     * get the balance total by year
     *
     * @param yearId
     * @param accountTypeId
     * @return
     */
    public BigDecimal getTotalBalanceByYearAndAcountType(Long yearId, Integer accountTypeId) {
        BigDecimal total = new BigDecimal(0);

        // iterate through the map values
        Iterator<Map<Long, IncomeBean>> mapIterator = this.mapYearlyBeanListByAccount.values().iterator();
        while (mapIterator.hasNext()) {
            Map<Long, IncomeBean> item = mapIterator.next();

            // add if account type matches
            if (accountTypeId != null) {
                if (item.get(yearId) == null) {
                    String dude = "test";
                } else {

                if (item.get(yearId).getAccountTypeId().equals(accountTypeId)) {
			if (item.get(yearId) != null) {
                    total = total.add(item.get(yearId).getBalanceTotal());
			}
                }
		}

            } else {
			if (item.get(yearId) != null) {
                total = total.add(item.get(yearId).getBalanceTotal());
			}
            }
        }

        // return
        return total;
    }
}
