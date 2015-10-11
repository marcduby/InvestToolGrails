
<%@ page import="com.doobs.invest.Symbol" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'symbol.label', default: 'Symbol')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-symbol" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-symbol" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<th><g:message code="symbol.security.label" default="Security" /></th>
					
						<g:sortableColumn property="symbol" title="${message(code: 'symbol.symbol.label', default: 'Symbol')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${symbolInstanceList}" status="i" var="symbolInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${symbolInstance.id}">${fieldValue(bean: symbolInstance, field: "security")}</g:link></td>
					
						<td>${fieldValue(bean: symbolInstance, field: "symbol")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${symbolInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
