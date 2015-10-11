package com.doobs.invest



import org.junit.*
import grails.test.mixin.*

@TestFor(SecurityPriceController)
@Mock(SecurityPrice)
class SecurityPriceControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/securityPrice/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.securityPriceInstanceList.size() == 0
        assert model.securityPriceInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.securityPriceInstance != null
    }

    void testSave() {
        controller.save()

        assert model.securityPriceInstance != null
        assert view == '/securityPrice/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/securityPrice/show/1'
        assert controller.flash.message != null
        assert SecurityPrice.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/securityPrice/list'

        populateValidParams(params)
        def securityPrice = new SecurityPrice(params)

        assert securityPrice.save() != null

        params.id = securityPrice.id

        def model = controller.show()

        assert model.securityPriceInstance == securityPrice
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/securityPrice/list'

        populateValidParams(params)
        def securityPrice = new SecurityPrice(params)

        assert securityPrice.save() != null

        params.id = securityPrice.id

        def model = controller.edit()

        assert model.securityPriceInstance == securityPrice
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/securityPrice/list'

        response.reset()

        populateValidParams(params)
        def securityPrice = new SecurityPrice(params)

        assert securityPrice.save() != null

        // test invalid parameters in update
        params.id = securityPrice.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/securityPrice/edit"
        assert model.securityPriceInstance != null

        securityPrice.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/securityPrice/show/$securityPrice.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        securityPrice.clearErrors()

        populateValidParams(params)
        params.id = securityPrice.id
        params.version = -1
        controller.update()

        assert view == "/securityPrice/edit"
        assert model.securityPriceInstance != null
        assert model.securityPriceInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/securityPrice/list'

        response.reset()

        populateValidParams(params)
        def securityPrice = new SecurityPrice(params)

        assert securityPrice.save() != null
        assert SecurityPrice.count() == 1

        params.id = securityPrice.id

        controller.delete()

        assert SecurityPrice.count() == 0
        assert SecurityPrice.get(securityPrice.id) == null
        assert response.redirectedUrl == '/securityPrice/list'
    }
}
