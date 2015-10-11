
<%@ page import="com.doobs.invest.Transaction" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'transaction.label', default: 'Transaction')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-transaction" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-transaction" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list transaction">
			
				<g:if test="${transactionInstance?.account}">
				<li class="fieldcontain">
					<span id="account-label" class="property-label"><g:message code="transaction.account.label" default="Account" /></span>
					
						<span class="property-value" aria-labelledby="account-label"><g:link controller="account" action="show" id="${transactionInstance?.account?.id}">${transactionInstance?.account?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${transactionInstance?.amount}">
				<li class="fieldcontain">
					<span id="amount-label" class="property-label"><g:message code="transaction.amount.label" default="Amount" /></span>
					
						<span class="property-value" aria-labelledby="amount-label"><g:fieldValue bean="${transactionInstance}" field="amount"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${transactionInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="transaction.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${transactionInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${transactionInstance?.price}">
				<li class="fieldcontain">
					<span id="price-label" class="property-label"><g:message code="transaction.price.label" default="Price" /></span>
					
						<span class="property-value" aria-labelledby="price-label"><g:fieldValue bean="${transactionInstance}" field="price"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${transactionInstance?.security}">
				<li class="fieldcontain">
					<span id="security-label" class="property-label"><g:message code="transaction.security.label" default="Security" /></span>
					
						<span class="property-value" aria-labelledby="security-label"><g:link controller="security" action="show" id="${transactionInstance?.security?.id}">${transactionInstance?.security?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${transactionInstance?.symbol}">
				<li class="fieldcontain">
					<span id="symbol-label" class="property-label"><g:message code="transaction.symbol.label" default="Symbol" /></span>
					
						<span class="property-value" aria-labelledby="symbol-label"><g:link controller="symbol" action="show" id="${transactionInstance?.symbol?.id}">${transactionInstance?.symbol?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${transactionInstance?.transactionDate}">
				<li class="fieldcontain">
					<span id="transactionDate-label" class="property-label"><g:message code="transaction.transactionDate.label" default="Transaction Date" /></span>
					
						<span class="property-value" aria-labelledby="transactionDate-label"><g:formatDate date="${transactionInstance?.transactionDate}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${transactionInstance?.type}">
				<li class="fieldcontain">
					<span id="type-label" class="property-label"><g:message code="transaction.type.label" default="Type" /></span>
					
						<span class="property-value" aria-labelledby="type-label"><g:link controller="transactionType" action="show" id="${transactionInstance?.type?.id}">${transactionInstance?.type?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${transactionInstance?.id}" />
					<g:link class="edit" action="edit" id="${transactionInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
