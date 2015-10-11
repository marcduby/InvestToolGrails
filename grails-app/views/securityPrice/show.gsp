
<%@ page import="com.doobs.invest.SecurityPrice" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'securityPrice.label', default: 'SecurityPrice')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-securityPrice" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-securityPrice" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list securityPrice">
			
				<g:if test="${securityPriceInstance?.price}">
				<li class="fieldcontain">
					<span id="price-label" class="property-label"><g:message code="securityPrice.price.label" default="Price" /></span>
					
						<span class="property-value" aria-labelledby="price-label"><g:fieldValue bean="${securityPriceInstance}" field="price"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${securityPriceInstance?.security}">
				<li class="fieldcontain">
					<span id="security-label" class="property-label"><g:message code="securityPrice.security.label" default="Security" /></span>
					
						<span class="property-value" aria-labelledby="security-label"><g:link controller="security" action="show" id="${securityPriceInstance?.security?.id}">${securityPriceInstance?.security?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${securityPriceInstance?.symbol}">
				<li class="fieldcontain">
					<span id="symbol-label" class="property-label"><g:message code="securityPrice.symbol.label" default="Symbol" /></span>
					
						<span class="property-value" aria-labelledby="symbol-label"><g:link controller="symbol" action="show" id="${securityPriceInstance?.symbol?.id}">${securityPriceInstance?.symbol?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${securityPriceInstance?.transactionDate}">
				<li class="fieldcontain">
					<span id="transactionDate-label" class="property-label"><g:message code="securityPrice.transactionDate.label" default="Transaction Date" /></span>
					
						<span class="property-value" aria-labelledby="transactionDate-label"><g:formatDate date="${securityPriceInstance?.transactionDate}" /></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${securityPriceInstance?.id}" />
					<g:link class="edit" action="edit" id="${securityPriceInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
