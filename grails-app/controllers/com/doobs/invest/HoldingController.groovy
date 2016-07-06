package com.doobs.invest

import org.springframework.dao.DataIntegrityViolationException

class HoldingController {
	AccountService accountService
	RestInvestService restInvestService
	
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
		log.info("in holding controller index")
        redirect(action: "list", params: params)
    }
	
	def refreshPrices() {
		log.info("in refresh controller service method call")
		this.restInvestService?.createPriceQuotesForToday()
		
		// redirect to the list
		redirect(action: "list")
	}

    def list(Integer max) {
		List<Holding> holdingList
		if (params.accountId) {
			holdingList = this.accountService?.getAccountHoldingList(Integer.valueOf(params.accountId))
		} else {
			holdingList = this.accountService?.getHoldingListOrderedByAccountIndustryAndName()
		}
		
		// get account list
		List<Account> accountList = Account.list()
		
		// totals
		Float totalAmount = 0.0
		Float totalDividend = 0.0
		Float totalPurchaseAmount = 0.0
		holdingList.each { Holding holding ->
			totalAmount = totalAmount + holding?.totalAmount
			totalPurchaseAmount = totalPurchaseAmount + holding?.totalPurchaseAmount
			totalDividend = totalDividend + holding?.totalDividend
		}
		Float percent = totalDividend / totalAmount
		Float purchasePercent = totalDividend / totalPurchaseAmount
		
        [holdingInstanceList: holdingList, holdingInstanceTotal: holdingList.size(), totalAmount: totalAmount, totalPurchaseAmount: totalPurchaseAmount, totalDividend: totalDividend, purchasePercent: purchasePercent, percent: percent, accountList: accountList]
    }

    def create() {
		List<Security> securityList = this.accountService?.getSecurityOrderedList()
		
        [holdingInstance: new Holding(params), securityList: securityList]
    }

    def save() {
        def holdingInstance = new Holding(params)
        if (!holdingInstance.save(flush: true)) {
            render(view: "create", model: [holdingInstance: holdingInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'holding.label', default: 'Holding'), holdingInstance.id])
        redirect(action: "show", id: holdingInstance.id)
    }

    def show(Long id) {
        def holdingInstance = Holding.get(id)
        if (!holdingInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'holding.label', default: 'Holding'), id])
            redirect(action: "list")
            return
        }

        [holdingInstance: holdingInstance]
    }

    def edit(Long id) {
		List<Security> securityList = this.accountService?.getSecurityOrderedList()
        def holdingInstance = Holding.get(id)
        if (!holdingInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'holding.label', default: 'Holding'), id])
            redirect(action: "list")
            return
        }

        [holdingInstance: holdingInstance, securityList: securityList]
    }

    def update(Long id, Long version) {
        def holdingInstance = Holding.get(id)
        if (!holdingInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'holding.label', default: 'Holding'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (holdingInstance.version > version) {
                holdingInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'holding.label', default: 'Holding')] as Object[],
                          "Another user has updated this Holding while you were editing")
                render(view: "edit", model: [holdingInstance: holdingInstance])
                return
            }
        }

        holdingInstance.properties = params

        if (!holdingInstance.save(flush: true)) {
            render(view: "edit", model: [holdingInstance: holdingInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'holding.label', default: 'Holding'), holdingInstance.id])
        redirect(action: "show", id: holdingInstance.id)
    }

    def delete(Long id) {
        def holdingInstance = Holding.get(id)
        if (!holdingInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'holding.label', default: 'Holding'), id])
            redirect(action: "list")
            return
        }

        try {
            holdingInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'holding.label', default: 'Holding'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'holding.label', default: 'Holding'), id])
            redirect(action: "show", id: id)
        }
    }
}
