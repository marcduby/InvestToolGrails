
<%@ page import="com.doobs.invest.AccountBalanceSheet" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'accountBalanceSheet.label', default: 'AccountBalanceSheet')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-accountBalanceSheet" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-accountBalanceSheet" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list accountBalanceSheet">
			
				<g:if test="${accountBalanceSheetInstance?.account}">
				<li class="fieldcontain">
					<span id="account-label" class="property-label"><g:message code="accountBalanceSheet.account.label" default="Account" /></span>
					
						<span class="property-value" aria-labelledby="account-label"><g:link controller="account" action="show" id="${accountBalanceSheetInstance?.account?.id}">${accountBalanceSheetInstance?.account?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${accountBalanceSheetInstance?.month}">
				<li class="fieldcontain">
					<span id="month-label" class="property-label"><g:message code="accountBalanceSheet.month.label" default="Month" /></span>
					
						<span class="property-value" aria-labelledby="month-label"><g:link controller="month" action="show" id="${accountBalanceSheetInstance?.month?.id}">${accountBalanceSheetInstance?.month?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<li class="fieldcontain">
					<span id="totalBalance-label" class="property-label"><g:message code="accountBalanceSheet.totalBalance.label" default="Total Balance" /></span>
					
						<span class="property-value" aria-labelledby="totalBalance-label"><g:formatNumber number="${accountBalanceSheetInstance?.totalBalance}" type="currency" currencyCode="USD" /></span>
					
				</li>

				<li class="fieldcontain">
					<span id="cashBalance-label" class="property-label"><g:message code="accountBalanceSheet.cashBalance.label" default="Cash Balance" /></span>
					
						<span class="property-value" aria-labelledby="cashBalance-label"><g:formatNumber number="${accountBalanceSheetInstance?.cashBalance}" type="currency" currencyCode="USD" /></span>
					
				</li>

				<li class="fieldcontain">
					<span id="cdBalance-label" class="property-label"><g:message code="accountBalanceSheet.cdBalance.label" default="CD Balance" /></span>

					<span class="property-value" aria-labelledby="cdBalance-label"><g:formatNumber number="${accountBalanceSheetInstance?.cdBalance}" type="currency" currencyCode="USD" /></span>

				</li>

				<li class="fieldcontain">
					<span id="income-label" class="property-label"><g:message code="accountBalanceSheet.income.label" default="Income" /></span>
					
						<span class="property-value" aria-labelledby="income-label"><g:formatNumber number="${accountBalanceSheetInstance?.income}" type="currency" currencyCode="USD" /></span>
					
				</li>

				<li class="fieldcontain">
					<span id="transfer-label" class="property-label"><g:message code="accountBalanceSheet.transfer.label" default="Transfer" /></span>
					
						<span class="property-value" aria-labelledby="transfer-label"><g:formatNumber number="${accountBalanceSheetInstance?.transfer}" type="currency" currencyCode="USD" /></span>
					
				</li>

				<g:if test="${accountBalanceSheetInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="accountBalanceSheet.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${accountBalanceSheetInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${accountBalanceSheetInstance?.lastUpdated}">
				<li class="fieldcontain">
					<span id="lastUpdated-label" class="property-label"><g:message code="accountBalanceSheet.lastUpdated.label" default="Last Updated" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${accountBalanceSheetInstance?.lastUpdated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${accountBalanceSheetInstance?.skip}">
				<li class="fieldcontain">
					<span id="skip-label" class="property-label"><g:message code="accountBalanceSheet.skip.label" default="Skip" /></span>
					
						<span class="property-value" aria-labelledby="skip-label"><g:formatBoolean boolean="${accountBalanceSheetInstance?.skip}" /></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:accountBalanceSheetInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${accountBalanceSheetInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
