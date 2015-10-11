<%@ page import="com.doobs.invest.Holding" %>



<div class="fieldcontain ${hasErrors(bean: holdingInstance, field: 'security', 'error')} required">
	<label for="security">
		<g:message code="holding.security.label" default="Security" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="security" name="security.id" from="${securityList}" optionKey="id" required="" value="${holdingInstance?.security?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: holdingInstance, field: 'account', 'error')} required">
	<label for="account">
		<g:message code="holding.account.label" default="Account" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="account" name="account.id" from="${com.doobs.invest.Account.list()}" optionKey="id" required="" value="${holdingInstance?.account?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: holdingInstance, field: 'amount', 'error')} required">
	<label for="amount">
		<g:message code="holding.amount.label" default="Amount" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="amount" value="${fieldValue(bean: holdingInstance, field: 'amount')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: holdingInstance, field: 'purchaseAmount', 'error')} required">
	<label for="purchaseAmount">
		<g:message code="holding.purchaseAmount.label" default="Purchase Amount" />
	</label>
	<g:field name="purchaseAmount" value="${fieldValue(bean: holdingInstance, field: 'purchaseAmount')}" required=""/>
</div>
