package com.doobs.invest

import com.doobs.invest.bean.distribution.DistributionMonthBean
import com.doobs.invest.bean.distribution.DistributionTypeBean
import com.doobs.invest.bean.income.YearlyReportBean
import com.doobs.invest.bean.balancesheet.YearDistributionBean

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class AccountBalanceSheetController {
    // instance variables
    AccountService accountService;
    SqlService sqlService;

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 25, 100)
        respond AccountBalanceSheet.list(params), model:[accountBalanceSheetInstanceCount: AccountBalanceSheet.count()]
    }

    @Transactional
    def quarterReport(Integer max) {
        // local variables
        Integer year = params.year ? Integer.parseInt(params?.year) : 2017
        List<BalanceSheetListBean> balanceSheetListBeanList = null;
        Map<Integer, BigDecimal> totalBalanceMap = new Hashtable<Integer, BigDecimal>();
        List<Month> monthList = null;

        // get the group id
        Integer groupId = 3;
        if (params.groupId) {
            groupId = Integer.valueOf(params.groupId)
        }

        // get the group list
        List<UserGroup> userGroupList = UserGroup.list()

        // get the balance sheet bean list
        balanceSheetListBeanList = this.sqlService?.getQuarterlyBalanceSheetsReport(year, groupId);

        // get the month list and totals
        if ((balanceSheetListBeanList != null) && (balanceSheetListBeanList.size() > 0)) {
            monthList = balanceSheetListBeanList.get(0).monthList;

            // add 0 values to map
            for (Month month: monthList) {
                totalBalanceMap.put(month.id, new BigDecimal(0))
            }
            // loop to get all the totals
            for (BalanceSheetListBean bean : balanceSheetListBeanList) {
                for (AccountBalanceSheet sheet : bean.accountBalanceSheetList) {
                    if (sheet != null && totalBalanceMap.get(sheet.month.id) != null) {
                        // add
                        totalBalanceMap.put(sheet.month.id, totalBalanceMap.get(sheet.month.id).add(sheet.totalBalance))
                    }
                }
            }

            // // loop to get all the totals
            // for (BalanceSheetListBean bean : balanceSheetListBeanList) {
            //     for (AccountBalanceSheet sheet : bean.accountBalanceSheetList) {
            //         if (totalBalanceMap.get(sheet.month.id) == null) {
            //             totalBalanceMap.put(sheet.month.id, new BigDecimal(0))
            //         }

            //         // add
            //         totalBalanceMap.put(sheet.month.id, totalBalanceMap.get(sheet.month.id).add(sheet.totalBalance))
            //     }
            // }
        }

        // return
        render model:[balanceSheetListBeanList: balanceSheetListBeanList, totalBalanceMap: totalBalanceMap, monthList: monthList, userGroupList: userGroupList, year: year], view: "quarterReport"
    }

    @Transactional
    def distributionReport(Integer max) {
        // local variables
        Integer year = params.year ? Integer.parseInt(params?.year) : 2018
        List<DistributionTypeBean> distributionTypeBeanList = null;
        Map<Integer, BigDecimal> totalBalanceMap = new Hashtable<Integer, BigDecimal>();
        List<Month> monthList = null;

        // get the group id
        Integer groupId = 3;
        if (params.groupId) {
            groupId = Integer.valueOf(params.groupId)
        }

        // get the group list
        List<UserGroup> userGroupList = UserGroup.list()

        // get the balance sheet bean list
        distributionTypeBeanList = this.sqlService?.getDistributionBalanceSheetsReport(year, groupId);

        // get the month list and totals
        if ((distributionTypeBeanList != null) && (distributionTypeBeanList.size() > 0)) {
            monthList = distributionTypeBeanList.get(0).monthList;

            // loop to get all the totals
            for (DistributionTypeBean bean : distributionTypeBeanList) {
                for (DistributionMonthBean sheet : bean.accountBalanceSheetList) {
                    if (totalBalanceMap.get(sheet.month.id) == null) {
                        totalBalanceMap.put(sheet.month.id, new BigDecimal(0))
                    }

                    // add
                    totalBalanceMap.put(sheet.month.id, totalBalanceMap.get(sheet.month.id).add(sheet.totalBalance))
                }
            }

        }

        // return
        render model:[balanceSheetListBeanList: distributionTypeBeanList, totalBalanceMap: totalBalanceMap, monthList: monthList, userGroupList: userGroupList, year: year], view: "distribReport"
    }

    def incomeReport(Integer max) {
        // local variables
        Integer year = params.year ? Integer.parseInt(params?.year) : 2017
        List<BalanceSheetListBean> balanceSheetListBeanList = null;
        Map<Integer, BigDecimal> totalBalanceMap = new Hashtable<Integer, BigDecimal>();
        List<Month> monthList = null;
        BigDecimal incomeTotal = new BigDecimal(0)
        BigDecimal transferTotal = new BigDecimal(0)

        // get the group id
        Integer groupId = 3;
        if (params.groupId) {
            groupId = Integer.valueOf(params.groupId)
        }

        // get the group list
        List<UserGroup> userGroupList = UserGroup.list()

        // get the balance sheet bean list
        balanceSheetListBeanList = this.sqlService?.getMonthlyBalanceSheetsReport(year, groupId);

        // get the income and transfer totals
        for (BalanceSheetListBean balanceSheetListBean : balanceSheetListBeanList) {
            incomeTotal = incomeTotal.add(balanceSheetListBean.getTotalIncome());
            transferTotal = transferTotal.add(balanceSheetListBean.getTotalTransfer());
        }

        // return
        render model:[balanceSheetListBeanList: balanceSheetListBeanList, incomeTotal: incomeTotal, transferTotal: transferTotal, userGroupList: userGroupList, year: year], view: "incomeReport"
    }

    /**
     * get the income yearly report
     *
     * @param max
     * @return
     */
    def yearlyIncomeReport(Integer max) {
        // local variables
        YearlyReportBean yearlyReportBean = null;

        // get the group id
        Integer groupId = 3;
        if (params.groupId) {
            groupId = Integer.valueOf(params.groupId)
        }

        // get the group list
        List<UserGroup> userGroupList = UserGroup.list()

        // get the balance sheet bean list
        yearlyReportBean = this.sqlService?.getYearlyIncomeReport(groupId)

        // return
        render model:[yearlyReportBeanInstance: yearlyReportBean, userGroupList: userGroupList], view: "yearlyIncomeReport"
    }

    /**
     * get the net worth by year report
     *
     * @param max
     * @return
     */
    def yearlyBalanceReport(Integer max) {
        // local variables
        YearlyReportBean yearlyReportBean = null;

        // get the group id
        Integer groupId = 3;
        if (params.groupId) {
            groupId = Integer.valueOf(params.groupId)
        }

        // get the group list
        List<UserGroup> userGroupList = UserGroup.list()

        // get the balance sheet bean list
        yearlyReportBean = this.sqlService?.getYearlyBalanceReport(groupId)

        // return
        render model:[yearlyReportBeanInstance: yearlyReportBean, userGroupList: userGroupList], view: "yearlyBalanceReport"
    }

    @Transactional
    def monthReport(Integer max) {
        // local variables
        Integer year = params.year ? Integer.parseInt(params?.year) : 2018
        List<BalanceSheetListBean> balanceSheetListBeanList = null;
        Map<Integer, BigDecimal> totalBalanceMap = new Hashtable<Integer, BigDecimal>();
        List<Month> monthList = null;

        // get the group id
        Integer groupId = 3;
        if (params.groupId) {
            groupId = Integer.valueOf(params.groupId)
        }

        // get the list
        // goupings of balance sheet
        balanceSheetListBeanList = this.sqlService?.getMonthlyBalanceSheetsReport(year, groupId);

        // get the group list
        List<UserGroup> userGroupList = UserGroup.list()

        // get the month list and totals
        if ((balanceSheetListBeanList != null) && (balanceSheetListBeanList.size() > 0)) {
            monthList = balanceSheetListBeanList.get(0).monthList;

            // add a total balance object
            for (Month month : monthList) {
                totalBalanceMap.put(month.id, new BigDecimal(0))
            }

            // loop to get all the totals
            // loop through the balance sheet list beans (by account type)
            // for (BalanceSheetListBean bean : balanceSheetListBeanList) {
            //     for (AccountBalanceSheet sheet : bean.accountBalanceSheetList) {
            //         if (sheet == null || totalBalanceMap.get(sheet.month.id) == null) {
            //             totalBalanceMap.put(sheet.month.id, new BigDecimal(0))
            //         }

            //         // add
            //         totalBalanceMap.put(sheet.month.id, totalBalanceMap.get(sheet.month.id).add(sheet.totalBalance))
            //     }
            // }

            for (BalanceSheetListBean bean : balanceSheetListBeanList) {
                for (AccountBalanceSheet sheet : bean.accountBalanceSheetList) {
                    if (sheet != null && totalBalanceMap.get(sheet.month.id) != null) {
                        // add
                        totalBalanceMap.put(sheet.month.id, totalBalanceMap.get(sheet.month.id).add(sheet.totalBalance))
                    }
                }
            }
        }

        // return
        render model:[balanceSheetListBeanList: balanceSheetListBeanList, totalBalanceMap: totalBalanceMap, monthList: monthList, userGroupList: userGroupList, year: year], view: "monthReport"
    }

    @Transactional
    def assetYearReport(Integer max) {
        // local variables
        YearDistributionBean yearBean = null;
        List<Integer> yearList = null;

        // get the group id
        Integer groupId = 3;
        if (params.groupId) {
            groupId = Integer.valueOf(params.groupId)
        }

        // get the list
        // goupings of balance sheet
        yearBean = this.sqlService?.getYearDistributionBeanForUser(groupId);
        yearList = yearBean.getYearList()

        // get the group list
        List<UserGroup> userGroupList = UserGroup.list()

        // return
        render model:[yearBean: yearBean, yearList: yearList, userGroupList: userGroupList], view: "assetYearReport"
    }

    @Transactional
    def indexByYear(Integer max) {
        // get the year and account
        Integer year = params.year ? Integer.parseInt(params?.year) : 2017
        Integer accountId = params.accountId ? Integer.parseInt(params?.accountId) : 1

        // get the account cach flow list
        List<AccountBalanceSheet> accountBalanceSheetList = this.accountService?.getOrCreateAccountBalanceSheetList(accountId, year);

        // get the user list
        List<AccountUserBean> accountUserBeanList = this.sqlService?.getUserAndAccountsList();

        // add up the income and transfers
        Float totalIncome = 0.0;
        Float totalTransfer = 0.0;
        Float totalGain = 0.0;
        Float totalGainPercent = 0.0;
        for (int index = 0; index < accountBalanceSheetList.size(); index++) {
            AccountBalanceSheet accountBalanceSheet = accountBalanceSheetList.get(index)

            // only add if balance sheet from this year
            if (accountBalanceSheet?.month?.year == year) {
                totalIncome += accountBalanceSheet?.income;
                totalTransfer += accountBalanceSheet?.transfer;
            }

            // calculate transient values for the balance sheets
            if (index > 0 && accountBalanceSheetList.get(index - 1)?.totalBalance != null) {
                accountBalanceSheet.totalGain = accountBalanceSheet?.totalBalance - accountBalanceSheetList.get(index - 1)?.totalBalance - accountBalanceSheet?.transfer

                if (accountBalanceSheetList.get(index - 1)?.totalBalance > 0) {
                    accountBalanceSheet.totalGainPercent = accountBalanceSheet.totalGain / accountBalanceSheetList.get(index - 1)?.totalBalance
                } else {
                    accountBalanceSheet.totalGainPercent = 0.0
                }

            } else {
                accountBalanceSheet.totalGain = 0.0
                accountBalanceSheet.totalGainPercent = 0.0
            }

            // set the total percent gain
            if (index > 0 && accountBalanceSheetList.get(0)?.totalBalance != null && accountBalanceSheetList.get(0)?.totalBalance > 0) {
                if (accountBalanceSheet?.totalBalance > 0) {
                    totalGain = (accountBalanceSheet?.totalBalance - accountBalanceSheetList.get(0)?.totalBalance - totalTransfer)
                    totalGainPercent = totalGain / accountBalanceSheetList.get(0)?.totalBalance;
                }
            }
        }

        respond accountBalanceSheetList, model:[accountUserBeanList: accountUserBeanList, accountBalanceSheetInstanceCount: accountBalanceSheetList?.size(), totalIncome: totalIncome, totalTransfer: totalTransfer, totalGain: totalGain, totalGainPercent: totalGainPercent, year: year], view: "index"
    }

    @Transactional
    def indexByDecade(Integer max) {
        // get the year and account
        Integer year = params.year ? Integer.parseInt(params?.year) : 2017
        Integer accountId = params.accountId ? Integer.parseInt(params?.accountId) : 1

        // get the account cach flow list
        List<AccountBalanceSheet> accountBalanceSheetList = this.accountService?.getOrCreateDecadeAccountBalanceSheetList(accountId);

        // get the user list
        List<AccountUserBean> accountUserBeanList = this.sqlService?.getUserAndAccountsList();

		// AccountBalanceSheet earliest = AccountBalanceSheet.findEarliest(accountId).first()

        // add up the income and transfers
        Float totalIncome = 0.0;
        Float totalTransfer = 0.0;
        Float totalGain = 0.0;
        Float totalGainPercent = 0.0;
        for (int index = 0; index < accountBalanceSheetList.size(); index++) {
            AccountBalanceSheet accountBalanceSheet = accountBalanceSheetList.get(index)
            totalIncome = totalIncome + (accountBalanceSheet?.incomeTotalCummulatative ? accountBalanceSheet?.incomeTotalCummulatative : 0.0);
            totalTransfer = totalTransfer + (accountBalanceSheet?.transferTotalCummulatative ? accountBalanceSheet?.transferTotalCummulatative : 0.0);
            if (index > 0 && accountBalanceSheetList.get(index - 1)?.totalBalance != null) {
                accountBalanceSheet.totalGain = accountBalanceSheet?.totalBalance - accountBalanceSheetList.get(index - 1)?.totalBalance - (accountBalanceSheet?.transferTotalCummulatative ? accountBalanceSheet?.transferTotalCummulatative : 0.0)

                if (accountBalanceSheetList.get(index - 1)?.totalBalance > 0) {
                    accountBalanceSheet.totalGainPercent = accountBalanceSheet.totalGain / accountBalanceSheetList.get(index - 1)?.totalBalance
                } else {
                    accountBalanceSheet.totalGainPercent = 0.0
                }

            } else {
                accountBalanceSheet.totalGain = 0.0
                accountBalanceSheet.totalGainPercent = 0.0
            }

            // set the total percent gain
            if (index > 0 && accountBalanceSheetList.get(0)?.totalBalance != null && accountBalanceSheetList.get(0)?.totalBalance > 0) {
                if (accountBalanceSheet?.totalBalance > 0) {
                    totalGain = (accountBalanceSheet?.totalBalance - accountBalanceSheetList.get(0)?.totalBalance - totalTransfer)
                    totalGainPercent = totalGain / accountBalanceSheetList.get(0)?.totalBalance;
                }
            }
        }

        respond accountBalanceSheetList, model:[accountUserBeanList: accountUserBeanList, accountBalanceSheetInstanceCount: accountBalanceSheetList?.size(), totalIncome: totalIncome, totalTransfer: totalTransfer, totalGain: totalGain, totalGainPercent: totalGainPercent, year: year, listType: "decade"], view: "index"
    }

    def show(AccountBalanceSheet accountBalanceSheetInstance) {
        respond accountBalanceSheetInstance
    }

    def create() {
        respond new AccountBalanceSheet(params)
    }

    @Transactional
    def save(AccountBalanceSheet accountBalanceSheetInstance) {
        if (accountBalanceSheetInstance == null) {
            notFound()
            return
        }

        if (accountBalanceSheetInstance.hasErrors()) {
            respond accountBalanceSheetInstance.errors, view:'create'
            return
        }

        accountBalanceSheetInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'accountBalanceSheet.label', default: 'AccountBalanceSheet'), accountBalanceSheetInstance.id])
                redirect accountBalanceSheetInstance
            }
            '*' { respond accountBalanceSheetInstance, [status: CREATED] }
        }
    }

    def edit(AccountBalanceSheet accountBalanceSheetInstance) {
        respond accountBalanceSheetInstance
    }

    @Transactional
    def update(AccountBalanceSheet accountBalanceSheetInstance) {
        if (accountBalanceSheetInstance == null) {
            notFound()
            return
        }

        if (accountBalanceSheetInstance.hasErrors()) {
            respond accountBalanceSheetInstance.errors, view:'edit'
            return
        }

        accountBalanceSheetInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'AccountBalanceSheet.label', default: 'AccountBalanceSheet'), accountBalanceSheetInstance.id])
                redirect accountBalanceSheetInstance
            }
            '*'{ respond accountBalanceSheetInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(AccountBalanceSheet accountBalanceSheetInstance) {

        if (accountBalanceSheetInstance == null) {
            notFound()
            return
        }

        accountBalanceSheetInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'AccountBalanceSheet.label', default: 'AccountBalanceSheet'), accountBalanceSheetInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'accountBalanceSheet.label', default: 'AccountBalanceSheet'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
