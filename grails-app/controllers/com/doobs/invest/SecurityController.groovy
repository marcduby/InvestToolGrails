package com.doobs.invest

import org.springframework.dao.DataIntegrityViolationException

class SecurityController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
		log.info("in security index")
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 500, 1000)
		params.sort = "name"
        [securityInstanceList: Security.list(params), securityInstanceTotal: Security.count()]
    }

    def create() {
		List<Industry> industryList = Industry.list(sort:"name")
		
        [securityInstance: new Security(params), industryList: industryList]
    }

    def save() {
        def securityInstance = new Security(params)
        if (!securityInstance.save(flush: true)) {
            render(view: "create", model: [securityInstance: securityInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'security.label', default: 'Security'), securityInstance.id])
        redirect(action: "show", id: securityInstance.id)
    }

    def show(Long id) {
        def securityInstance = Security.get(id)
        if (!securityInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'security.label', default: 'Security'), id])
            redirect(action: "list")
            return
        }

        [securityInstance: securityInstance]
    }

    def edit(Long id) {
        def securityInstance = Security.get(id)
		List<Symbol> symbolList = Symbol.list(sort: "symbol")
		List<Industry> industryList = Industry.list(sort:"name")
		
        if (!securityInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'security.label', default: 'Security'), id])
            redirect(action: "list")
            return
        }

        [securityInstance: securityInstance, symbolList: symbolList, industryList: industryList]
    }

    def update(Long id, Long version) {
        def securityInstance = Security.get(id)
        if (!securityInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'security.label', default: 'Security'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (securityInstance.version > version) {
                securityInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'security.label', default: 'Security')] as Object[],
                          "Another user has updated this Security while you were editing")
                render(view: "edit", model: [securityInstance: securityInstance])
                return
            }
        }

        securityInstance.properties = params

        if (!securityInstance.save(flush: true)) {
            render(view: "edit", model: [securityInstance: securityInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'security.label', default: 'Security'), securityInstance.id])
        redirect(action: "show", id: securityInstance.id)
    }

    def delete(Long id) {
        def securityInstance = Security.get(id)
        if (!securityInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'security.label', default: 'Security'), id])
            redirect(action: "list")
            return
        }

        try {
            securityInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'security.label', default: 'Security'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'security.label', default: 'Security'), id])
            redirect(action: "show", id: id)
        }
    }
}
