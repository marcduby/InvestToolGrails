
<%@ page import="com.doobs.invest.Holding" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'holding.label', default: 'Diversification')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-holding" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
			</ul>
		</div>
		<div class="nav">
			<ul>
			<g:each in="${accountList}" status="i" var="accountInstance">
				<li><g:link class="home" action="industryReport" params="[accountId: accountInstance?.id]">${accountInstance?.listName}</g:link></li>
			</g:each>
			</ul>
		</div>
		<div id="list-holding" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<th>${diversificationType }</th>
					
						<th class="alignRight">Total</th>
					
						<th class="alignRight">Percent</th>
					
						<th class="alignRight">Yearly Dividend</th>
					
						<th class="alignRight">Percent Dividend</th>
					
						<th class="alignRight">Percent</th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${beanList}" status="i" var="beanInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td>${fieldValue(bean: beanInstance, field: "name")}</td>
					
						<td class="alignRight"><g:formatNumber number="${beanInstance?.amountTotal}" type="currency" currencyCode="USD" /></td>
					
						<td class="alignRight"><g:formatNumber number="${beanInstance?.amountTotal / totalAmount}" type="percent" maxFractionDigits="2"/></td>
					
						<td class="alignRight"><g:formatNumber number="${beanInstance?.dividendTotal}" type="currency" currencyCode="USD" /></td>
					
						<td class="alignRight"><g:formatNumber number="${beanInstance?.dividendTotal / beanInstance?.amountTotal}" type="percent" maxFractionDigits="2"/></td>
						
						<td class="alignRight"><g:formatNumber number="${beanInstance?.dividendTotal / totalDividend}" type="percent" maxFractionDigits="2"/></td>
						
					</tr>
				</g:each>
				
					<tr class="total">
					
						<td>Total</td>
					
						<td class="alignRight"><g:formatNumber number="${totalAmount}" type="currency" currencyCode="USD" /></td>
					
						<td></td>
						
						<td class="alignRight"><g:formatNumber number="${totalDividend}" type="currency" currencyCode="USD" /></td>
					
						<td class="alignRight"><g:formatNumber number="${totalDividend / totalAmount}" type="percent" maxFractionDigits="2"/></td>
					
						<td></td>
						
					</tr>
				</tbody>
			</table>
			<div class="pagination">
			</div>
		</div>
	</body>
</html>
