<%@ page import="com.doobs.invest.News" %>



<div class="fieldcontain ${hasErrors(bean: newsInstance, field: 'comment', 'error')} ">
	<label for="comment">
		<g:message code="news.comment.label" default="Comment" />
		
	</label>
	<g:textField name="comment" value="${newsInstance?.comment}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: newsInstance, field: 'text', 'error')} ">
	<label for="text">
		<g:message code="news.text.label" default="Text" />
		
	</label>
	<g:textField name="text" value="${newsInstance?.text}"/>
</div>

