<%@ page import="com.doobs.invest.Industry" %>



<div class="fieldcontain ${hasErrors(bean: industryInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="industry.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${industryInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: industryInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="industry.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${industryInstance?.description}"/>
</div>

