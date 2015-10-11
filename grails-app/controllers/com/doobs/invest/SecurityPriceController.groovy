package com.doobs.invest

import org.springframework.dao.DataIntegrityViolationException

class SecurityPriceController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [securityPriceInstanceList: SecurityPrice.list(params), securityPriceInstanceTotal: SecurityPrice.count()]
    }

    def create() {
        [securityPriceInstance: new SecurityPrice(params)]
    }

    def save() {
        def securityPriceInstance = new SecurityPrice(params)
        if (!securityPriceInstance.save(flush: true)) {
            render(view: "create", model: [securityPriceInstance: securityPriceInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'securityPrice.label', default: 'SecurityPrice'), securityPriceInstance.id])
        redirect(action: "show", id: securityPriceInstance.id)
    }

    def show(Long id) {
        def securityPriceInstance = SecurityPrice.get(id)
        if (!securityPriceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'securityPrice.label', default: 'SecurityPrice'), id])
            redirect(action: "list")
            return
        }

        [securityPriceInstance: securityPriceInstance]
    }

    def edit(Long id) {
        def securityPriceInstance = SecurityPrice.get(id)
        if (!securityPriceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'securityPrice.label', default: 'SecurityPrice'), id])
            redirect(action: "list")
            return
        }

        [securityPriceInstance: securityPriceInstance]
    }

    def update(Long id, Long version) {
        def securityPriceInstance = SecurityPrice.get(id)
        if (!securityPriceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'securityPrice.label', default: 'SecurityPrice'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (securityPriceInstance.version > version) {
                securityPriceInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'securityPrice.label', default: 'SecurityPrice')] as Object[],
                          "Another user has updated this SecurityPrice while you were editing")
                render(view: "edit", model: [securityPriceInstance: securityPriceInstance])
                return
            }
        }

        securityPriceInstance.properties = params

        if (!securityPriceInstance.save(flush: true)) {
            render(view: "edit", model: [securityPriceInstance: securityPriceInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'securityPrice.label', default: 'SecurityPrice'), securityPriceInstance.id])
        redirect(action: "show", id: securityPriceInstance.id)
    }

    def delete(Long id) {
        def securityPriceInstance = SecurityPrice.get(id)
        if (!securityPriceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'securityPrice.label', default: 'SecurityPrice'), id])
            redirect(action: "list")
            return
        }

        try {
            securityPriceInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'securityPrice.label', default: 'SecurityPrice'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'securityPrice.label', default: 'SecurityPrice'), id])
            redirect(action: "show", id: id)
        }
    }
}
