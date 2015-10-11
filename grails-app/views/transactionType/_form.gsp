<%@ page import="com.doobs.invest.TransactionType" %>



<div class="fieldcontain ${hasErrors(bean: transactionTypeInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="transactionType.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${transactionTypeInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: transactionTypeInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="transactionType.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${transactionTypeInstance?.name}"/>
</div>

