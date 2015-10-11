
<%@ page import="com.doobs.invest.Security" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'security.label', default: 'Security')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-security" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-security" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="name" title="${message(code: 'security.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="description" title="${message(code: 'security.description.label', default: 'Description')}" />
					
						<g:sortableColumn property="industry" title="${message(code: 'security.industry.label', default: 'Industry')}" />
					
						<th><g:message code="security.currentSymbol.label" default="Current Symbol" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${securityInstanceList}" status="i" var="securityInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${securityInstance.id}">${fieldValue(bean: securityInstance, field: "name")}</g:link></td>
					
						<td>${fieldValue(bean: securityInstance, field: "description")}</td>
					
						<td>${securityInstance?.industry?.name}</td>
					
						<td>${fieldValue(bean: securityInstance, field: "currentSymbol")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${securityInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
