<%@ page import="com.doobs.invest.Account" %>



<div class="fieldcontain ${hasErrors(bean: accountInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="account.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${accountInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: accountInstance, field: 'type', 'error')} required">
	<label for="type">
		<g:message code="account.type.label" default="Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="type" name="type.id" from="${com.doobs.invest.AccountType.list()}" optionKey="id" required="" value="${accountInstance?.type?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: accountInstance, field: 'user', 'error')} required">
	<label for="type">
		<g:message code="account.user.label" default="User" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="type" name="user.id" from="${com.doobs.invest.User.list()}" optionKey="id" required="" value="${accountInstance?.user?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: accountInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="account.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${accountInstance?.description}"/>
</div>

