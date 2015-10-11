package com.doobs.invest



import org.junit.*
import grails.test.mixin.*

@TestFor(SymbolController)
@Mock(Symbol)
class SymbolControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/symbol/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.symbolInstanceList.size() == 0
        assert model.symbolInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.symbolInstance != null
    }

    void testSave() {
        controller.save()

        assert model.symbolInstance != null
        assert view == '/symbol/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/symbol/show/1'
        assert controller.flash.message != null
        assert Symbol.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/symbol/list'

        populateValidParams(params)
        def symbol = new Symbol(params)

        assert symbol.save() != null

        params.id = symbol.id

        def model = controller.show()

        assert model.symbolInstance == symbol
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/symbol/list'

        populateValidParams(params)
        def symbol = new Symbol(params)

        assert symbol.save() != null

        params.id = symbol.id

        def model = controller.edit()

        assert model.symbolInstance == symbol
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/symbol/list'

        response.reset()

        populateValidParams(params)
        def symbol = new Symbol(params)

        assert symbol.save() != null

        // test invalid parameters in update
        params.id = symbol.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/symbol/edit"
        assert model.symbolInstance != null

        symbol.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/symbol/show/$symbol.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        symbol.clearErrors()

        populateValidParams(params)
        params.id = symbol.id
        params.version = -1
        controller.update()

        assert view == "/symbol/edit"
        assert model.symbolInstance != null
        assert model.symbolInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/symbol/list'

        response.reset()

        populateValidParams(params)
        def symbol = new Symbol(params)

        assert symbol.save() != null
        assert Symbol.count() == 1

        params.id = symbol.id

        controller.delete()

        assert Symbol.count() == 0
        assert Symbol.get(symbol.id) == null
        assert response.redirectedUrl == '/symbol/list'
    }
}
