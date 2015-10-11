package com.doobs.invest

import org.springframework.dao.DataIntegrityViolationException

class AccountTypeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [accountTypeInstanceList: AccountType.list(params), accountTypeInstanceTotal: AccountType.count()]
    }

    def create() {
        [accountTypeInstance: new AccountType(params)]
    }

    def save() {
        def accountTypeInstance = new AccountType(params)
        if (!accountTypeInstance.save(flush: true)) {
            render(view: "create", model: [accountTypeInstance: accountTypeInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'accountType.label', default: 'AccountType'), accountTypeInstance.id])
        redirect(action: "show", id: accountTypeInstance.id)
    }

    def show(Long id) {
        def accountTypeInstance = AccountType.get(id)
        if (!accountTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'accountType.label', default: 'AccountType'), id])
            redirect(action: "list")
            return
        }

        [accountTypeInstance: accountTypeInstance]
    }

    def edit(Long id) {
        def accountTypeInstance = AccountType.get(id)
        if (!accountTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'accountType.label', default: 'AccountType'), id])
            redirect(action: "list")
            return
        }

        [accountTypeInstance: accountTypeInstance]
    }

    def update(Long id, Long version) {
        def accountTypeInstance = AccountType.get(id)
        if (!accountTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'accountType.label', default: 'AccountType'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (accountTypeInstance.version > version) {
                accountTypeInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'accountType.label', default: 'AccountType')] as Object[],
                          "Another user has updated this AccountType while you were editing")
                render(view: "edit", model: [accountTypeInstance: accountTypeInstance])
                return
            }
        }

        accountTypeInstance.properties = params

        if (!accountTypeInstance.save(flush: true)) {
            render(view: "edit", model: [accountTypeInstance: accountTypeInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'accountType.label', default: 'AccountType'), accountTypeInstance.id])
        redirect(action: "show", id: accountTypeInstance.id)
    }

    def delete(Long id) {
        def accountTypeInstance = AccountType.get(id)
        if (!accountTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'accountType.label', default: 'AccountType'), id])
            redirect(action: "list")
            return
        }

        try {
            accountTypeInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'accountType.label', default: 'AccountType'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'accountType.label', default: 'AccountType'), id])
            redirect(action: "show", id: id)
        }
    }
}
