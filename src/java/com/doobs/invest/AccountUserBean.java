package com.doobs.invest;

import java.util.ArrayList;
import java.util.List;

/**
 * Reporting class for users
 *
 * Created by mduby on 1/1/18.
 */
public class AccountUserBean {
    // instance variables
    private List<AccountBean> accountBeanList = new ArrayList<AccountBean>();
    private String name;
    private String initial;
    private Integer userId;

    public List<AccountBean> getAccountBeanList() {
        return accountBeanList;
    }

    public void setAccountBeanList(List<AccountBean> accountBeanList) {
        this.accountBeanList = accountBeanList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
