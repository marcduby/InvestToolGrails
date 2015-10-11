<%@ page import="com.doobs.invest.SecurityPrice" %>



<div class="fieldcontain ${hasErrors(bean: securityPriceInstance, field: 'price', 'error')} required">
	<label for="price">
		<g:message code="securityPrice.price.label" default="Price" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="price" value="${fieldValue(bean: securityPriceInstance, field: 'price')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: securityPriceInstance, field: 'security', 'error')} required">
	<label for="security">
		<g:message code="securityPrice.security.label" default="Security" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="security" name="security.id" from="${com.doobs.invest.Security.list()}" optionKey="id" required="" value="${securityPriceInstance?.security?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: securityPriceInstance, field: 'symbol', 'error')} required">
	<label for="symbol">
		<g:message code="securityPrice.symbol.label" default="Symbol" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="symbol" name="symbol.id" from="${com.doobs.invest.Symbol.list()}" optionKey="id" required="" value="${securityPriceInstance?.symbol?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: securityPriceInstance, field: 'transactionDate', 'error')} required">
	<label for="transactionDate">
		<g:message code="securityPrice.transactionDate.label" default="Transaction Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="transactionDate" precision="day"  value="${securityPriceInstance?.transactionDate}"  />
</div>

