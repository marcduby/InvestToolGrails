
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
			<g:each in="${accountUserBeanList}" status="i" var="accountUserBeanInstance">
				<ul>
					<g:each in="${accountUserBeanInstance?.accountBeanList}" status="j" var="accountBeanInstance">
						<g:if test="${listType != null && listType.equals('decade')}">
							<li><g:link class="create" action="indexByDecade" params="[accountId: accountBeanInstance?.accountId, year: year]">${accountUserBeanInstance?.initial} - ${accountBeanInstance?.name}</g:link></li>
						</g:if>
						<g:else>
							<li><g:link class="create" action="indexByYear" params="[accountId: accountBeanInstance?.accountId, year: year]">${accountUserBeanInstance?.initial} - ${accountBeanInstance?.name}</g:link></li>
						</g:else>
					</g:each>
				</ul>
			</g:each>
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
					
						<th style="text-align:right"><g:message code="accountBalanceSheet.month.label" default="Year" /></th>
					
						<th style="text-align:right"><g:message code="accountBalanceSheet.month.label" default="Month" /></th>
					
						<th style="text-align:right"><g:message code="accountBalanceSheet.gain.label" default="Gain" /></th>
					
						<th style="text-align:right"><g:message code="accountBalanceSheet.gain.label" default="Gain %" /></th>
					
						<g:sortableColumn class="currency" property="totalBalance" title="${message(code: 'accountBalanceSheet.totalBalance.label', default: 'Total Balance')}" />
					
						<g:sortableColumn class="currency" property="income" title="${message(code: 'accountBalanceSheet.income.label', default: 'Income')}" />
					
						<g:sortableColumn class="currency" property="transfer" title="${message(code: 'accountBalanceSheet.transfer.label', default: 'Transfer')}" />

					</tr>
				</thead>
				<tbody>
				<g:each in="${accountBalanceSheetInstanceList}" status="i" var="accountBalanceSheetInstance">
					<tr class="${(!accountBalanceSheetInstance?.skip && (accountBalanceSheetInstance?.version == 0)) ? 'totalYellow' : ((i % 2) == 0 ? 'even' : 'odd')}">
					
						<td><g:link action="show" id="${accountBalanceSheetInstance.id}">${fieldValue(bean: accountBalanceSheetInstance, field: "account")}</g:link></td>
					
						<td class="currency">${fieldValue(bean: accountBalanceSheetInstance, field: "monthName")}</td>
					
						<td class="currency"><g:formatNumber number="${accountBalanceSheetInstance?.totalGain}" type="currency" currencyCode="USD" /></td>

						<td class="currency"><g:formatNumber number="${accountBalanceSheetInstance?.totalGainPercent}" type="percent" maxFractionDigits="2"/></td>
					
						<td class="currency"><g:formatNumber number="${accountBalanceSheetInstance?.totalBalance}" type="currency" currencyCode="USD" /></td>

						<g:if test="${listType != null && listType.equals('decade')}">
							<td class="currency"><g:formatNumber number="${accountBalanceSheetInstance?.incomeTotalCummulatative}" type="currency" currencyCode="USD" /></td>
						</g:if>
						<g:else>
							<td class="currency"><g:formatNumber number="${accountBalanceSheetInstance?.income}" type="currency" currencyCode="USD" /></td>
						</g:else>

						<g:if test="${listType != null && listType.equals('decade')}">
							<td class="currency"><g:formatNumber number="${accountBalanceSheetInstance?.transferTotalCummulatative}" type="currency" currencyCode="USD" /></td>
						</g:if>
						<g:else>
							<td class="currency"><g:formatNumber number="${accountBalanceSheetInstance?.transfer}" type="currency" currencyCode="USD" /></td>
						</g:else>

					</tr>
				</g:each>

				<tr class="totalGreen">
					<td><b>Total</b></td>
					<td>&nbsp;</td>
					<td class="currency"><g:formatNumber number="${totalGain}" type="currency" currencyCode="USD" /></td>
					<td class="currency"><g:formatNumber number="${totalGainPercent}" type="percent" maxFractionDigits="2"/></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td class="currency"><g:formatNumber number="${totalIncome}" type="currency" currencyCode="USD" /></td>
					<td class="currency"><g:formatNumber number="${totalTransfer}" type="currency" currencyCode="USD" /></td>
					<td>&nbsp;</td>
				</tr>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${accountBalanceSheetInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
