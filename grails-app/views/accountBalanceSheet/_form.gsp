<%@ page import="com.doobs.invest.AccountBalanceSheet" %>



<div class="fieldcontain ${hasErrors(bean: accountBalanceSheetInstance, field: 'account', 'error')} required">
	<label for="account">
		<g:message code="accountBalanceSheet.account.label" default="Account" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="account" name="account.id" from="${com.doobs.invest.Account.list()}" optionKey="id" required="" value="${accountBalanceSheetInstance?.account?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: accountBalanceSheetInstance, field: 'month', 'error')} required">
	<label for="month">
		<g:message code="accountBalanceSheet.month.label" default="Month" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="month" name="month.id" from="${com.doobs.invest.Month.list()}" optionKey="id" required="" value="${accountBalanceSheetInstance?.month?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: accountBalanceSheetInstance, field: 'totalBalance', 'error')} required">
	<label for="totalBalance">
		<g:message code="accountBalanceSheet.totalBalance.label" default="Total Balance" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="totalBalance" value="${fieldValue(bean: accountBalanceSheetInstance, field: 'totalBalance')}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: accountBalanceSheetInstance, field: 'cashBalance', 'error')} required">
	<label for="cashBalance">
		<g:message code="accountBalanceSheet.cashBalance.label" default="Cash Balance" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="cashBalance" value="${fieldValue(bean: accountBalanceSheetInstance, field: 'cashBalance')}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: accountBalanceSheetInstance, field: 'income', 'error')} required">
	<label for="income">
		<g:message code="accountBalanceSheet.income.label" default="Income" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="income" value="${fieldValue(bean: accountBalanceSheetInstance, field: 'income')}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: accountBalanceSheetInstance, field: 'transfer', 'error')} required">
	<label for="transfer">
		<g:message code="accountBalanceSheet.transfer.label" default="Transfer" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="transfer" value="${fieldValue(bean: accountBalanceSheetInstance, field: 'transfer')}" required=""/>

</div>

