package com.doobs.invest



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class AccountBalanceSheetController {
    // instance variables
    AccountService accountService;

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 25, 100)
        respond AccountBalanceSheet.list(params), model:[accountBalanceSheetInstanceCount: AccountBalanceSheet.count()]
    }

    @Transactional
    def indexByYear(Integer max) {
        // get the year and account
        Integer year = Integer.parseInt(params?.year)
        Integer accountId = Integer.parseInt(params?.accountId)

        // get the account list
        List<AccountBalanceSheet> accountBalanceSheetList = this.accountService?.getOrCreateAccountBalanceSheetList(accountId, year);

        respond accountBalanceSheetList, model:[accountBalanceSheetInstanceCount: accountBalanceSheetList?.size()], view: "index"
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
