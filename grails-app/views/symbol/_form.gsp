<%@ page import="com.doobs.invest.Symbol" %>



<div class="fieldcontain ${hasErrors(bean: symbolInstance, field: 'security', 'error')} required">
	<label for="security">
		<g:message code="symbol.security.label" default="Security" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="security" name="security.id" from="${securityList}" optionKey="id" required="" value="${symbolInstance?.security?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: symbolInstance, field: 'symbol', 'error')} ">
	<label for="symbol">
		<g:message code="symbol.symbol.label" default="Symbol" />
		
	</label>
	<g:textField name="symbol" value="${symbolInstance?.symbol}"/>
</div>

