package com.doobs.invest



import org.junit.*
import grails.test.mixin.*

@TestFor(SecurityController)
@Mock(Security)
class SecurityControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/security/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.securityInstanceList.size() == 0
        assert model.securityInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.securityInstance != null
    }

    void testSave() {
        controller.save()

        assert model.securityInstance != null
        assert view == '/security/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/security/show/1'
        assert controller.flash.message != null
        assert Security.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/security/list'

        populateValidParams(params)
        def security = new Security(params)

        assert security.save() != null

        params.id = security.id

        def model = controller.show()

        assert model.securityInstance == security
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/security/list'

        populateValidParams(params)
        def security = new Security(params)

        assert security.save() != null

        params.id = security.id

        def model = controller.edit()

        assert model.securityInstance == security
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/security/list'

        response.reset()

        populateValidParams(params)
        def security = new Security(params)

        assert security.save() != null

        // test invalid parameters in update
        params.id = security.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/security/edit"
        assert model.securityInstance != null

        security.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/security/show/$security.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        security.clearErrors()

        populateValidParams(params)
        params.id = security.id
        params.version = -1
        controller.update()

        assert view == "/security/edit"
        assert model.securityInstance != null
        assert model.securityInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/security/list'

        response.reset()

        populateValidParams(params)
        def security = new Security(params)

        assert security.save() != null
        assert Security.count() == 1

        params.id = security.id

        controller.delete()

        assert Security.count() == 0
        assert Security.get(security.id) == null
        assert response.redirectedUrl == '/security/list'
    }
}
