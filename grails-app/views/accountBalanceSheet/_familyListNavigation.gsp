<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <g:each in="${userGroupList}" status="i" var="accountUserGroupBeanInstance">
            <li><g:link class="create" action="${action}" params="[groupId: accountUserGroupBeanInstance?.id, year: year]">${accountUserGroupBeanInstance?.name}</g:link></li>
        </g:each>
    </ul>
</div>
