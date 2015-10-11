package com.doobs.invest



import org.junit.*
import grails.test.mixin.*

@TestFor(IndustryController)
@Mock(Industry)
class IndustryControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/industry/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.industryInstanceList.size() == 0
        assert model.industryInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.industryInstance != null
    }

    void testSave() {
        controller.save()

        assert model.industryInstance != null
        assert view == '/industry/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/industry/show/1'
        assert controller.flash.message != null
        assert Industry.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/industry/list'

        populateValidParams(params)
        def industry = new Industry(params)

        assert industry.save() != null

        params.id = industry.id

        def model = controller.show()

        assert model.industryInstance == industry
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/industry/list'

        populateValidParams(params)
        def industry = new Industry(params)

        assert industry.save() != null

        params.id = industry.id

        def model = controller.edit()

        assert model.industryInstance == industry
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/industry/list'

        response.reset()

        populateValidParams(params)
        def industry = new Industry(params)

        assert industry.save() != null

        // test invalid parameters in update
        params.id = industry.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/industry/edit"
        assert model.industryInstance != null

        industry.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/industry/show/$industry.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        industry.clearErrors()

        populateValidParams(params)
        params.id = industry.id
        params.version = -1
        controller.update()

        assert view == "/industry/edit"
        assert model.industryInstance != null
        assert model.industryInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/industry/list'

        response.reset()

        populateValidParams(params)
        def industry = new Industry(params)

        assert industry.save() != null
        assert Industry.count() == 1

        params.id = industry.id

        controller.delete()

        assert Industry.count() == 0
        assert Industry.get(industry.id) == null
        assert response.redirectedUrl == '/industry/list'
    }
}
