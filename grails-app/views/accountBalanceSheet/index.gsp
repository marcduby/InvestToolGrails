
<%@ page import="com.doobs.invest.AccountBalanceSheet" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'accountBalanceSheet.label', default: 'AccountBalanceSheet')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-accountBalanceSheet" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-accountBalanceSheet" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<th><g:message code="accountBalanceSheet.account.label" default="Account" /></th>
					
						<th><g:message code="accountBalanceSheet.month.label" default="Month" /></th>
					
						<g:sortableColumn class="currency" property="totalBalance" title="${message(code: 'accountBalanceSheet.totalBalance.label', default: 'Total Balance')}" />
					
						<g:sortableColumn class="currency" property="cashBalance" title="${message(code: 'accountBalanceSheet.cashBalance.label', default: 'Cash Balance')}" />
					
						<g:sortableColumn class="currency" property="income" title="${message(code: 'accountBalanceSheet.income.label', default: 'Income')}" />
					
						<g:sortableColumn class="currency" property="transfer" title="${message(code: 'accountBalanceSheet.transfer.label', default: 'Transfer')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${accountBalanceSheetInstanceList}" status="i" var="accountBalanceSheetInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${accountBalanceSheetInstance.id}">${fieldValue(bean: accountBalanceSheetInstance, field: "account")}</g:link></td>
					
						<td>${fieldValue(bean: accountBalanceSheetInstance, field: "month")}</td>
					
						<td class="currency"><g:formatNumber number="${accountBalanceSheetInstance?.totalBalance}" type="currency" currencyCode="USD" /></td>

						<td class="currency"><g:formatNumber number="${accountBalanceSheetInstance?.cashBalance}" type="currency" currencyCode="USD" /></td>

						<td class="currency"><g:formatNumber number="${accountBalanceSheetInstance?.income}" type="currency" currencyCode="USD" /></td>

						<td class="currency"><g:formatNumber number="${accountBalanceSheetInstance?.transfer}" type="currency" currencyCode="USD" /></td>

					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${accountBalanceSheetInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
