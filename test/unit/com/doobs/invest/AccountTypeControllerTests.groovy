package com.doobs.invest



import org.junit.*
import grails.test.mixin.*

@TestFor(AccountTypeController)
@Mock(AccountType)
class AccountTypeControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/accountType/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.accountTypeInstanceList.size() == 0
        assert model.accountTypeInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.accountTypeInstance != null
    }

    void testSave() {
        controller.save()

        assert model.accountTypeInstance != null
        assert view == '/accountType/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/accountType/show/1'
        assert controller.flash.message != null
        assert AccountType.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/accountType/list'

        populateValidParams(params)
        def accountType = new AccountType(params)

        assert accountType.save() != null

        params.id = accountType.id

        def model = controller.show()

        assert model.accountTypeInstance == accountType
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/accountType/list'

        populateValidParams(params)
        def accountType = new AccountType(params)

        assert accountType.save() != null

        params.id = accountType.id

        def model = controller.edit()

        assert model.accountTypeInstance == accountType
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/accountType/list'

        response.reset()

        populateValidParams(params)
        def accountType = new AccountType(params)

        assert accountType.save() != null

        // test invalid parameters in update
        params.id = accountType.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/accountType/edit"
        assert model.accountTypeInstance != null

        accountType.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/accountType/show/$accountType.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        accountType.clearErrors()

        populateValidParams(params)
        params.id = accountType.id
        params.version = -1
        controller.update()

        assert view == "/accountType/edit"
        assert model.accountTypeInstance != null
        assert model.accountTypeInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/accountType/list'

        response.reset()

        populateValidParams(params)
        def accountType = new AccountType(params)

        assert accountType.save() != null
        assert AccountType.count() == 1

        params.id = accountType.id

        controller.delete()

        assert AccountType.count() == 0
        assert AccountType.get(accountType.id) == null
        assert response.redirectedUrl == '/accountType/list'
    }
}
