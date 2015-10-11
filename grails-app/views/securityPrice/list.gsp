
<%@ page import="com.doobs.invest.SecurityPrice" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'securityPrice.label', default: 'SecurityPrice')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-securityPrice" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-securityPrice" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="price" title="${message(code: 'securityPrice.price.label', default: 'Price')}" />
					
						<th><g:message code="securityPrice.security.label" default="Security" /></th>
					
						<th><g:message code="securityPrice.symbol.label" default="Symbol" /></th>
					
						<g:sortableColumn property="transactionDate" title="${message(code: 'securityPrice.transactionDate.label', default: 'Transaction Date')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${securityPriceInstanceList}" status="i" var="securityPriceInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${securityPriceInstance.id}">${fieldValue(bean: securityPriceInstance, field: "price")}</g:link></td>
					
						<td>${fieldValue(bean: securityPriceInstance, field: "security")}</td>
					
						<td>${fieldValue(bean: securityPriceInstance, field: "symbol")}</td>
					
						<td><g:formatDate date="${securityPriceInstance.transactionDate}" /></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${securityPriceInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
