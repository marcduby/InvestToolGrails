package com.doobs.invest;

/**
 * Reporting class for accounts
 *
 * Created by mduby on 1/1/18.
 */
public class AccountBean {
    // instance variables
    private Integer accountId;
    private String name;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
