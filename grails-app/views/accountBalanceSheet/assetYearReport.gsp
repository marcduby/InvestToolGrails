
<%@ page import="com.doobs.invest.Account; com.doobs.invest.AccountBalanceSheet" %>
<%@ page import="com.doobs.invest.BalanceSheetListBean" %>
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
				<g:each in="${(2008..2020)}" var="yearId">
					<li><g:link class="${request.forwardURI.contains('/accountBalanceSheet') ? 'current' : ''}" controller="accountBalanceSheet" action="monthReport" params="[year:yearId]">Month ${yearId}</g:link></li>
				</g:each>
			</ul>
		</div>

		<g:render template="familyListNavigation" model="[userGroupList: userGroupList, year: year, action: 'assetYearReport']"/>

		<div id="list-accountBalanceSheet" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>

			<thead>
					<tr>
					
						<th>Year</th>

						<th class="currency">Bank</th>

						<th class="currency">Invest</th>

						<th class="currency">IRA</th>

						<th class="currency">Retirement</th>

						<th class="currency">Total</th>

					</tr>
				</thead>
				<tbody>
				<g:each in="${yearList}" status="j" var="year">

					<tr class="${((j % 2) == 0 ? 'even' : 'odd')}">
					
						<td>${year}</td>

						<td class="currency"><g:formatNumber number="${yearBean?.getAccountTotal('bank', year)}" type="currency" currencyCode="USD" /></td>

						<td class="currency"><g:formatNumber number="${yearBean?.getAccountTotal('invest', year)}" type="currency" currencyCode="USD" /></td>

						<td class="currency"><g:formatNumber number="${yearBean?.getAccountTotal('ira', year)}" type="currency" currencyCode="USD" /></td>

						<td class="currency"><g:formatNumber number="${yearBean?.getAccountTotal('retirement', year)}" type="currency" currencyCode="USD" /></td>

						<td class="currency"><g:formatNumber number="${yearBean?.getAccountTotal('all', year)}" type="currency" currencyCode="USD" /></td>

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
