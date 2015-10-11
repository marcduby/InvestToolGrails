<%@ page import="com.doobs.invest.Security" %>



<div class="fieldcontain ${hasErrors(bean: securityInstance, field: 'currentSymbol', 'error')}">
	<label for="currentSymbol">
		<g:message code="security.currentSymbol.label" default="Current Symbol" />
	</label>
	<g:select id="currentSymbol" name="currentSymbol.id" from="${symbolList}" optionKey="id" value="${securityInstance?.currentSymbol?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: securityInstance, field: 'industry', 'error')}">
	<label for="industry">
		<g:message code="security.industry.label" default="Industry" />
	</label>
	<g:select id="industry" name="industry.id" from="${industryList}" optionKey="id" value="${securityInstance?.industry?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: securityInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="security.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${securityInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: securityInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="security.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${securityInstance?.name}"/>
</div>

