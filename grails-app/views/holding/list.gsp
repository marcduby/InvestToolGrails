
<%@ page import="com.doobs.invest.Holding" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'holding.label', default: 'Holding')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-holding" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="refreshPrices">Refresh Prices</g:link></li>
			</ul>
		</div>
		<div class="nav">
			<ul>
			<g:each in="${accountList}" status="i" var="accountInstance">
				<li><g:link class="home" action="list" params="[accountId: accountInstance?.id]">${accountInstance?.listName}</g:link></li>
			</g:each>
			</ul>
		</div>
		<div id="list-holding" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /> - ${holdingInstanceList?.size()} items</h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<th><g:message code="holding.account.label" default="Account" /></th>
					
						<th><g:message code="holding.security.label" default="Security" /></th>
					
						<th><g:message code="holding.symbol.label" default="Symbol" /></th>
					
						<th><g:message code="holding.industry.label" default="Industry" /></th>
					
						<g:sortableColumn class="alignRight" property="amount" title="${message(code: 'holding.amount.label', default: 'Amount')}" />
					
						<th class="alignRight"><g:message code="holding.industry.label" default="Price" /></th>
					
						<th class="alignRight"><g:message code="holding.industry.label" default="Price Paid" /></th>
						
						<th class="alignRight"><g:message code="holding.industry.label" default="Dividend" /></th>
					
						<th class="alignRight"><g:message code="holding.industry.label" default="Percent" /></th>
					
						<th class="alignRight"><g:message code="holding.industry.label" default="Purchase %" /></th>
					
						<th class="alignRight"><g:message code="holding.industry.label" default="Purchase Total" /></th>
					
						<th class="alignRight"><g:message code="holding.industry.label" default="Total" /></th>
					
						<th class="alignRight"><g:message code="holding.industry.label" default="Yearly Dividend" /></th>

						<th class="alignRight"><g:message code="holding.industry.label" default="Refresh" /></th>

					</tr>
				</thead>
				<tbody>
				<g:each in="${holdingInstanceList}" status="i" var="holdingInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td>${fieldValue(bean: holdingInstance, field: "account")}</td>
					
						<td><g:link action="show" id="${holdingInstance.id}">${fieldValue(bean: holdingInstance, field: "security")}</g:link></td>
					
						<td>${holdingInstance?.security?.currentSymbol?.symbol}</td>
					
						<td>${holdingInstance?.security?.industry?.name}</td>
					
						<td class="alignRight">${fieldValue(bean: holdingInstance, field: "amount")}</td>
					
						<td class="alignRight"><g:formatNumber number="${holdingInstance?.security?.currentPrice?.price}" type="currency" currencyCode="USD" /></td>
					
						<td class="alignRight"><g:formatNumber number="${holdingInstance?.purchaseAmount}" type="currency" currencyCode="USD" /></td>

						<td class="alignRight"><g:formatNumber number="${holdingInstance?.security?.currentPrice?.yearlyDividend}" type="currency" currencyCode="USD" /></td>
					
						<td class="alignRight"><g:formatNumber number="${holdingInstance?.totalDividend / holdingInstance?.totalAmount}" type="percent" maxFractionDigits="2"/></td>
					
						<td class="alignRight"><g:formatNumber number="${holdingInstance?.totalDividend / holdingInstance?.totalPurchaseAmount}" type="percent" maxFractionDigits="2"/></td>
					
						<td class="alignRight"><g:formatNumber number="${holdingInstance?.totalPurchaseAmount}" type="currency" currencyCode="USD" /></td>
					
						<td class="alignRight"><g:formatNumber number="${holdingInstance?.totalAmount}" type="currency" currencyCode="USD" /></td>
					
						<td class="alignRight"><g:formatNumber number="${holdingInstance?.totalDividend}" type="currency" currencyCode="USD" /></td>

						<td><g:link action="refreshPrices" id="${holdingInstance?.security?.id}"><g:formatDate format="yyyy-MM-dd" date="${holdingInstance?.security?.currentPrice?.transactionDate}"/></g:link></td>

					</tr>
				</g:each>
				
					<tr class="total">
					
						<td>Total</td>
					
						<td></td>
					
						<td></td>
					
						<td></td>
					
						<td></td>
					
						<td></td>
					
						<td class="alignRight"></td>
					
						<td class="alignRight"></td>
					
						<td class="alignRight"><g:formatNumber number="${percent}" type="percent" maxFractionDigits="2"/></td>
					
						<td class="alignRight"><g:formatNumber number="${purchasePercent}" type="percent" maxFractionDigits="2"/></td>
					
						<td class="alignRight"><g:formatNumber number="${totalPurchaseAmount}" type="currency" currencyCode="USD" /></td>
					
						<td class="alignRight"><g:formatNumber number="${totalAmount}" type="currency" currencyCode="USD" /></td>
					
						<td class="alignRight"><g:formatNumber number="${totalDividend}" type="currency" currencyCode="USD" /></td>
					
					</tr>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${holdingInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
