
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

		<g:render template="familyListNavigation" model="[userGroupList: userGroupList, year: year, action: 'monthReport']"/>

		<div id="list-accountBalanceSheet" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>


<g:each in="${balanceSheetListBeanList}" status="jj" var="balanceSheetListBeanListInstance">
			<thead>
					<tr>
					
						<th>${balanceSheetListBeanListInstance?.accountType?.name}</th>

						<g:each in="${balanceSheetListBeanListInstance.getMonthList()}" status="i" var="monthInstance">

							<g:if test="${jj == 0}">
								<th class="currency">${monthInstance?.getShortMonthName()}</th>
							</g:if>
							<g:else>
								<th class="currency">&nbsp;</th>
							</g:else>

						</g:each>

					</tr>
				</thead>
				<tbody>
				<g:each in="${balanceSheetListBeanListInstance.getAccountIdList()}" status="j" var="accountId">

					<tr class="${((j % 2) == 0 ? 'even' : 'odd')}">
					
						<td>${com.doobs.invest.Account.get(accountId)?.user?.name.substring(0, 1)} - ${com.doobs.invest.Account.get(accountId)?.name}</td>

						<g:each in="${balanceSheetListBeanListInstance?.getMonthList()}" status="k" var="monthInstance">

							<td class="currency"><g:formatNumber number="${balanceSheetListBeanListInstance?.getBalanceSheetByYearAndAccountId(monthInstance?.getId(), accountId)?.totalBalance}" type="currency" currencyCode="USD" /></td>

						</g:each>

					</tr>
				</g:each>

				<tr class="totalYellow">

					<td><b>Total</b></td>

					<g:each in="${balanceSheetListBeanListInstance?.getMonthList()}" status="k" var="monthInstance">

						<td class="currency"><g:formatNumber number="${balanceSheetListBeanListInstance?.getTotalBalance(monthInstance?.getId())}" type="currency" currencyCode="USD" /></td>

					</g:each>

				</tr>

				</tbody>
				</g:each>


				<tbody>
					<tr class="totalGreen">

						<td><b>Total All</b></td>

						<g:each in="${monthList}" status="k" var="monthInstance">

							<td class="currency"><g:formatNumber number="${totalBalanceMap?.get(monthInstance?.getId())}" type="currency" currencyCode="USD" /></td>

						</g:each>

					</tr>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${accountBalanceSheetInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
