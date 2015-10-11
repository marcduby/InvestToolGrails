package com.doobs.invest

import org.springframework.dao.DataIntegrityViolationException

class TransactionTypeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [transactionTypeInstanceList: TransactionType.list(params), transactionTypeInstanceTotal: TransactionType.count()]
    }

    def create() {
        [transactionTypeInstance: new TransactionType(params)]
    }

    def save() {
        def transactionTypeInstance = new TransactionType(params)
        if (!transactionTypeInstance.save(flush: true)) {
            render(view: "create", model: [transactionTypeInstance: transactionTypeInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'transactionType.label', default: 'TransactionType'), transactionTypeInstance.id])
        redirect(action: "show", id: transactionTypeInstance.id)
    }

    def show(Long id) {
        def transactionTypeInstance = TransactionType.get(id)
        if (!transactionTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'transactionType.label', default: 'TransactionType'), id])
            redirect(action: "list")
            return
        }

        [transactionTypeInstance: transactionTypeInstance]
    }

    def edit(Long id) {
        def transactionTypeInstance = TransactionType.get(id)
        if (!transactionTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'transactionType.label', default: 'TransactionType'), id])
            redirect(action: "list")
            return
        }

        [transactionTypeInstance: transactionTypeInstance]
    }

    def update(Long id, Long version) {
        def transactionTypeInstance = TransactionType.get(id)
        if (!transactionTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'transactionType.label', default: 'TransactionType'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (transactionTypeInstance.version > version) {
                transactionTypeInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'transactionType.label', default: 'TransactionType')] as Object[],
                          "Another user has updated this TransactionType while you were editing")
                render(view: "edit", model: [transactionTypeInstance: transactionTypeInstance])
                return
            }
        }

        transactionTypeInstance.properties = params

        if (!transactionTypeInstance.save(flush: true)) {
            render(view: "edit", model: [transactionTypeInstance: transactionTypeInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'transactionType.label', default: 'TransactionType'), transactionTypeInstance.id])
        redirect(action: "show", id: transactionTypeInstance.id)
    }

    def delete(Long id) {
        def transactionTypeInstance = TransactionType.get(id)
        if (!transactionTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'transactionType.label', default: 'TransactionType'), id])
            redirect(action: "list")
            return
        }

        try {
            transactionTypeInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'transactionType.label', default: 'TransactionType'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'transactionType.label', default: 'TransactionType'), id])
            redirect(action: "show", id: id)
        }
    }
}
