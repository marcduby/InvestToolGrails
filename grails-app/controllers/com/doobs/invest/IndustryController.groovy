package com.doobs.invest

import org.springframework.dao.DataIntegrityViolationException

class IndustryController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 50, 100)
        [industryInstanceList: Industry.list(sort: "name"), industryInstanceTotal: Industry.count()]
    }

    def create() {
        [industryInstance: new Industry(params)]
    }

    def save() {
        def industryInstance = new Industry(params)
        if (!industryInstance.save(flush: true)) {
            render(view: "create", model: [industryInstance: industryInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'industry.label', default: 'Industry'), industryInstance.id])
        redirect(action: "show", id: industryInstance.id)
    }

    def show(Long id) {
        def industryInstance = Industry.get(id)
        if (!industryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'industry.label', default: 'Industry'), id])
            redirect(action: "list")
            return
        }

        [industryInstance: industryInstance]
    }

    def edit(Long id) {
        def industryInstance = Industry.get(id)
        if (!industryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'industry.label', default: 'Industry'), id])
            redirect(action: "list")
            return
        }

        [industryInstance: industryInstance]
    }

    def update(Long id, Long version) {
        def industryInstance = Industry.get(id)
        if (!industryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'industry.label', default: 'Industry'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (industryInstance.version > version) {
                industryInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'industry.label', default: 'Industry')] as Object[],
                          "Another user has updated this Industry while you were editing")
                render(view: "edit", model: [industryInstance: industryInstance])
                return
            }
        }

        industryInstance.properties = params

        if (!industryInstance.save(flush: true)) {
            render(view: "edit", model: [industryInstance: industryInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'industry.label', default: 'Industry'), industryInstance.id])
        redirect(action: "show", id: industryInstance.id)
    }

    def delete(Long id) {
        def industryInstance = Industry.get(id)
        if (!industryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'industry.label', default: 'Industry'), id])
            redirect(action: "list")
            return
        }

        try {
            industryInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'industry.label', default: 'Industry'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'industry.label', default: 'Industry'), id])
            redirect(action: "show", id: id)
        }
    }
}
