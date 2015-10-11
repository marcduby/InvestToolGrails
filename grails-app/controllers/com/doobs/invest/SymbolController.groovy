package com.doobs.invest

import org.springframework.dao.DataIntegrityViolationException

class SymbolController {
	AccountService accountService
	
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 500, 1000)
		params.sort = "symbol"
        [symbolInstanceList: Symbol.list(params), symbolInstanceTotal: Symbol.count()]
    }

    def create() {
		List<Security> securityList = this.accountService?.getSecurityOrderedList()
        [symbolInstance: new Symbol(params), securityList: securityList]
    }

    def save() {
        def symbolInstance = new Symbol(params)
        if (!symbolInstance.save(flush: true)) {
            render(view: "create", model: [symbolInstance: symbolInstance])
            return
        }
		
		// also add symbol to security
		Security security = symbolInstance?.security
		if (security) {
			security.currentSymbol = symbolInstance
			security.save(flush: true)
			log.info("added symbol: " + symbolInstance?.symbol + " to security: " + security?.name)
		}
		
		// send to view
        flash.message = message(code: 'default.created.message', args: [message(code: 'symbol.label', default: 'Symbol'), symbolInstance.id])
        redirect(action: "show", id: symbolInstance.id)
    }

    def show(Long id) {
        def symbolInstance = Symbol.get(id)
        if (!symbolInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'symbol.label', default: 'Symbol'), id])
            redirect(action: "list")
            return
        }

        [symbolInstance: symbolInstance]
    }

    def edit(Long id) {
        def symbolInstance = Symbol.get(id)
        if (!symbolInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'symbol.label', default: 'Symbol'), id])
            redirect(action: "list")
            return
        }

        [symbolInstance: symbolInstance]
    }

    def update(Long id, Long version) {
        def symbolInstance = Symbol.get(id)
        if (!symbolInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'symbol.label', default: 'Symbol'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (symbolInstance.version > version) {
                symbolInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'symbol.label', default: 'Symbol')] as Object[],
                          "Another user has updated this Symbol while you were editing")
                render(view: "edit", model: [symbolInstance: symbolInstance])
                return
            }
        }

        symbolInstance.properties = params

        if (!symbolInstance.save(flush: true)) {
            render(view: "edit", model: [symbolInstance: symbolInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'symbol.label', default: 'Symbol'), symbolInstance.id])
        redirect(action: "show", id: symbolInstance.id)
    }

    def delete(Long id) {
        def symbolInstance = Symbol.get(id)
        if (!symbolInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'symbol.label', default: 'Symbol'), id])
            redirect(action: "list")
            return
        }

        try {
            symbolInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'symbol.label', default: 'Symbol'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'symbol.label', default: 'Symbol'), id])
            redirect(action: "show", id: id)
        }
    }
}
