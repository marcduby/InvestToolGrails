package com.doobs.invest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mduby on 1/4/18.
 */
public class FinancialPeriodBean {
    // instance avriables
    private Month month;
    private List<BalanceSheetListBean> balanceSheetListBeanList = new ArrayList<BalanceSheetListBean>();

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }


}
