
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

		<g:render template="familyListNavigation" model="[userGroupList: userGroupList, year: year, action: 'incomeReport']"/>

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

						<th>Income</th>

						<th>Transfer</th>

						<th>% Return</th>

					</tr>
				</thead>
				<tbody>
				<g:each in="${balanceSheetListBeanListInstance.getAccountIdList()}" status="j" var="accountId">

					<tr class="${((j % 2) == 0 ? 'even' : 'odd')}">
					
						<td>${com.doobs.invest.Account.get(accountId)?.user?.name.substring(0, 1)} - ${com.doobs.invest.Account.get(accountId)?.name}</td>

						<td><g:formatNumber number="${balanceSheetListBeanListInstance.getTotalIncomeByAccount(accountId)}" type="currency" currencyCode="USD" /></td>

						<td><g:formatNumber number="${balanceSheetListBeanListInstance.getTotalTransferByAccount(accountId)}" type="currency" currencyCode="USD" /></td>

						<td>&nbsp;</td>

					</tr>
				</g:each>

				<tr class="totalYellow">

					<td><b>Total</b></td>

					<td><g:formatNumber number="${balanceSheetListBeanListInstance.getTotalIncome()}" type="currency" currencyCode="USD" /></td>

					<td><g:formatNumber number="${balanceSheetListBeanListInstance.getTotalTransfer()}" type="currency" currencyCode="USD" /></td>

					<td>&nbsp;</td>

				</tr>

				</tbody>
				</g:each>


				<tbody>
					<tr class="totalGreen">

						<td><b>Total All</b></td>

						<td><b><g:formatNumber number="${incomeTotal}" type="currency" currencyCode="USD" /></b></td>

						<td><b><g:formatNumber number="${transferTotal}" type="currency" currencyCode="USD" /></b></td>

						<td><b>&nbsp;</b></td>

					</tr>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${accountBalanceSheetInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
