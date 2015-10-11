package com.doobs.invest



import org.junit.*
import grails.test.mixin.*

@TestFor(TransactionTypeController)
@Mock(TransactionType)
class TransactionTypeControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/transactionType/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.transactionTypeInstanceList.size() == 0
        assert model.transactionTypeInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.transactionTypeInstance != null
    }

    void testSave() {
        controller.save()

        assert model.transactionTypeInstance != null
        assert view == '/transactionType/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/transactionType/show/1'
        assert controller.flash.message != null
        assert TransactionType.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/transactionType/list'

        populateValidParams(params)
        def transactionType = new TransactionType(params)

        assert transactionType.save() != null

        params.id = transactionType.id

        def model = controller.show()

        assert model.transactionTypeInstance == transactionType
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/transactionType/list'

        populateValidParams(params)
        def transactionType = new TransactionType(params)

        assert transactionType.save() != null

        params.id = transactionType.id

        def model = controller.edit()

        assert model.transactionTypeInstance == transactionType
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/transactionType/list'

        response.reset()

        populateValidParams(params)
        def transactionType = new TransactionType(params)

        assert transactionType.save() != null

        // test invalid parameters in update
        params.id = transactionType.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/transactionType/edit"
        assert model.transactionTypeInstance != null

        transactionType.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/transactionType/show/$transactionType.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        transactionType.clearErrors()

        populateValidParams(params)
        params.id = transactionType.id
        params.version = -1
        controller.update()

        assert view == "/transactionType/edit"
        assert model.transactionTypeInstance != null
        assert model.transactionTypeInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/transactionType/list'

        response.reset()

        populateValidParams(params)
        def transactionType = new TransactionType(params)

        assert transactionType.save() != null
        assert TransactionType.count() == 1

        params.id = transactionType.id

        controller.delete()

        assert TransactionType.count() == 0
        assert TransactionType.get(transactionType.id) == null
        assert response.redirectedUrl == '/transactionType/list'
    }
}
