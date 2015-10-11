<%@ page import="com.doobs.invest.AccountType" %>



<div class="fieldcontain ${hasErrors(bean: accountTypeInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="accountType.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${accountTypeInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: accountTypeInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="accountType.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${accountTypeInstance?.description}"/>
</div>

