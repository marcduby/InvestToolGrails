
<%@ page import="com.doobs.invest.Holding" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'holding.label', default: 'Holding')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-holding" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-holding" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list holding">
			
				<g:if test="${holdingInstance?.security}">
				<li class="fieldcontain">
					<span id="security-label" class="property-label"><g:message code="holding.security.label" default="Security" /></span>
					
						<span class="property-value" aria-labelledby="security-label"><g:link controller="security" action="show" id="${holdingInstance?.security?.id}">${holdingInstance?.security?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${holdingInstance?.account}">
				<li class="fieldcontain">
					<span id="account-label" class="property-label"><g:message code="holding.account.label" default="Account" /></span>
					
						<span class="property-value" aria-labelledby="account-label"><g:link controller="account" action="show" id="${holdingInstance?.account?.id}">${holdingInstance?.account?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${holdingInstance?.amount}">
				<li class="fieldcontain">
					<span id="amount-label" class="property-label"><g:message code="holding.amount.label" default="Amount" /></span>
					
						<span class="property-value" aria-labelledby="amount-label"><g:fieldValue bean="${holdingInstance}" field="amount"/></span>
					
				</li>
				</g:if>
			
				<li class="fieldcontain">
					<span id="amount-label" class="property-label"><g:message code="holding.purchaseAmount.label" default="Purchase Amount" /></span>
					
						<span class="property-value" aria-labelledby="purchaseAmount-label"><g:fieldValue bean="${holdingInstance}" field="purchaseAmount"/></span>
					
				</li>
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${holdingInstance?.id}" />
					<g:link class="edit" action="edit" id="${holdingInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
