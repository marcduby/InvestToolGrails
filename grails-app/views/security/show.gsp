
<%@ page import="com.doobs.invest.Security" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'security.label', default: 'Security')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-security" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-security" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list security">
			
				<g:if test="${securityInstance?.currentSymbol}">
				<li class="fieldcontain">
					<span id="currentSymbol-label" class="property-label"><g:message code="security.currentSymbol.label" default="Current Symbol" /></span>
					
						<span class="property-value" aria-labelledby="currentSymbol-label"><g:link controller="symbol" action="show" id="${securityInstance?.currentSymbol?.id}">${securityInstance?.currentSymbol?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${securityInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="security.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${securityInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${securityInstance?.industry}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="security.industry.label" default="Industry" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${securityInstance}" field="industry"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${securityInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="security.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${securityInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${securityInstance?.id}" />
					<g:link class="edit" action="edit" id="${securityInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
