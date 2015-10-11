<%@ page import="com.doobs.invest.Transaction" %>



<div class="fieldcontain ${hasErrors(bean: transactionInstance, field: 'account', 'error')} required">
	<label for="account">
		<g:message code="transaction.account.label" default="Account" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="account" name="account.id" from="${com.doobs.invest.Account.list()}" optionKey="id" required="" value="${transactionInstance?.account?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: transactionInstance, field: 'amount', 'error')} required">
	<label for="amount">
		<g:message code="transaction.amount.label" default="Amount" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="amount" value="${fieldValue(bean: transactionInstance, field: 'amount')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: transactionInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="transaction.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${transactionInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: transactionInstance, field: 'price', 'error')} required">
	<label for="price">
		<g:message code="transaction.price.label" default="Price" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="price" value="${fieldValue(bean: transactionInstance, field: 'price')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: transactionInstance, field: 'security', 'error')} required">
	<label for="security">
		<g:message code="transaction.security.label" default="Security" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="security" name="security.id" from="${com.doobs.invest.Security.list()}" optionKey="id" required="" value="${transactionInstance?.security?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: transactionInstance, field: 'symbol', 'error')} required">
	<label for="symbol">
		<g:message code="transaction.symbol.label" default="Symbol" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="symbol" name="symbol.id" from="${com.doobs.invest.Symbol.list()}" optionKey="id" required="" value="${transactionInstance?.symbol?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: transactionInstance, field: 'transactionDate', 'error')} required">
	<label for="transactionDate">
		<g:message code="transaction.transactionDate.label" default="Transaction Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="transactionDate" precision="day"  value="${transactionInstance?.transactionDate}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: transactionInstance, field: 'type', 'error')} required">
	<label for="type">
		<g:message code="transaction.type.label" default="Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="type" name="type.id" from="${com.doobs.invest.TransactionType.list()}" optionKey="id" required="" value="${transactionInstance?.type?.id}" class="many-to-one"/>
</div>

