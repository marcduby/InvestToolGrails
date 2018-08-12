package com.doobs.invest

import com.doobs.invest.bean.distribution.DistributionMonthBean
import com.doobs.invest.bean.distribution.DistributionTypeBean

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

            // loop to get all the totals
            for (BalanceSheetListBean bean : balanceSheetListBeanList) {
                for (AccountBalanceSheet sheet : bean.accountBalanceSheetList) {
                    if (totalBalanceMap.get(sheet.month.id) == null) {
                        totalBalanceMap.put(sheet.month.id, new BigDecimal(0))
                    }

                    // add
                    totalBalanceMap.put(sheet.month.id, totalBalanceMap.get(sheet.month.id).add(sheet.totalBalance))
                }
            }

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

            // loop to get all the totals
            for (BalanceSheetListBean bean : balanceSheetListBeanList) {
                for (AccountBalanceSheet sheet : bean.accountBalanceSheetList) {
                    if (totalBalanceMap.get(sheet.month.id) == null) {
                        totalBalanceMap.put(sheet.month.id, new BigDecimal(0))
                    }

                    // add
                    totalBalanceMap.put(sheet.month.id, totalBalanceMap.get(sheet.month.id).add(sheet.totalBalance))
                }
            }

        }

        // return
        render model:[balanceSheetListBeanList: balanceSheetListBeanList, totalBalanceMap: totalBalanceMap, monthList: monthList, userGroupList: userGroupList, year: year], view: "monthReport"
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
        for (AccountBalanceSheet accountBalanceSheet : accountBalanceSheetList) {
            totalIncome += accountBalanceSheet?.income;
            totalTransfer += accountBalanceSheet?.transfer;
        }

        respond accountBalanceSheetList, model:[accountUserBeanList: accountUserBeanList, accountBalanceSheetInstanceCount: accountBalanceSheetList?.size(), totalIncome: totalIncome, totalTransfer: totalTransfer, year: year], view: "index"
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
