
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

		<g:render template="familyListNavigation" model="[userGroupList: userGroupList, year: year, action: 'yearlyBalanceReport']"/>

		<div id="list-accountBalanceSheet" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>


<g:each in="${yearlyReportBeanInstance.getAccountTypeList()}" status="jj" var="accountTypeId">
			<thead>
					<tr>
					
						<th>${yearlyReportBeanInstance.getAccountTypeName(accountTypeId)}</th>

	<g:each in="${yearlyReportBeanInstance.getYearList()}" status="ii" var="yearId">
						<th>${yearId}</th>
	</g:each>

					</tr>
				</thead>
				<tbody>
				<g:each in="${yearlyReportBeanInstance.getAccountListByType(accountTypeId)}" status="j" var="accountId">

					<tr class="${((j % 2) == 0 ? 'even' : 'odd')}">

						<td>${yearlyReportBeanInstance.getAccountName(accountId)}</td>

					<g:each in="${yearlyReportBeanInstance.getYearList()}" status="ii" var="yearId">
						<g:if test="${yearlyReportBeanInstance.getIncomeBeanByAccountYear(accountId, yearId)?.getBalanceTotal() > 0}">
							<td><g:formatNumber number="${yearlyReportBeanInstance?.getIncomeBeanByAccountYear(accountId, yearId)?.getBalanceTotal()}" type="currency" currencyCode="USD" /></td>
						</g:if>
						<g:else>
							<td>&nbsp;</td>
						</g:else>
					</g:each>
					</tr>
				</g:each>

				<tr class="totalYellow">

					<td><b>Total</b></td>

					<g:each in="${yearlyReportBeanInstance.getYearList()}" status="ii" var="yearId">
						<g:if test="${yearlyReportBeanInstance?.getTotalBalanceByYearAndAcountType(yearId, accountTypeId) != null}">
							<td><g:formatNumber number="${yearlyReportBeanInstance?.getTotalBalanceByYearAndAcountType(yearId, accountTypeId)}" type="currency" currencyCode="USD" /></td>
						</g:if>
						<g:else>
							<td>&nbsp;</td>
						</g:else>
					</g:each>

				</tr>

				</tbody>
</g:each>


				<tbody>
					<tr class="totalGreen">

						<td><b>Total All</b></td>

						<g:each in="${yearlyReportBeanInstance.getYearList()}" status="jj" var="yearId">
							<td><g:formatNumber number="${yearlyReportBeanInstance.getTotalBalanceByYearAndAcountType(yearId, null)}" type="currency" currencyCode="USD" /></td>
						</g:each>

					</tr>
				<tr class="totalGreen">

					<td><b>Diff Percent</b></td>

					<g:each in="${yearlyReportBeanInstance.getYearList()}" status="jj" var="yearId">
						<g:if test="${yearlyReportBeanInstance.getYearList().contains(yearId - 1) && (yearlyReportBeanInstance?.getTotalBalanceByYearAndAcountType(yearId-1, null) > 0)}">
							<td><g:formatNumber number="${(yearlyReportBeanInstance.getTotalBalanceByYearAndAcountType(yearId, null) - yearlyReportBeanInstance.getTotalBalanceByYearAndAcountType(yearId-1, null)) / yearlyReportBeanInstance.getTotalBalanceByYearAndAcountType(yearId-1, null)}" type="percent"/></td>
						</g:if>
						<g:else>
							<td>&nbsp;</td>
						</g:else>
					</g:each>

				</tr>
				<tr class="totalGreen">

					<td><b>Diff Amount</b></td>

					<g:each in="${yearlyReportBeanInstance.getYearList()}" status="jj" var="yearId">
						<g:if test="${yearlyReportBeanInstance.getYearList().contains(yearId - 1) && (yearlyReportBeanInstance?.getTotalBalanceByYearAndAcountType(yearId-1, null) > 0)}">
							<td><g:formatNumber number="${yearlyReportBeanInstance.getTotalBalanceByYearAndAcountType(yearId, null) - yearlyReportBeanInstance.getTotalBalanceByYearAndAcountType(yearId-1, null)}" type="currency" currencyCode="USD"/></td>
						</g:if>
						<g:else>
							<td>&nbsp;</td>
						</g:else>
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
