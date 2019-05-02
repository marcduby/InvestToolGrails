<!DOCTYPE html>
<html>
    <head>
        <title><g:layoutTitle default="Grails" /></title>
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
        <g:layoutHead />
        <g:javascript library="application" />
    </head>
    <body>
		<div id="grailsLogo" role="banner"><a href="http://grails.org"><img src="${resource(dir: 'images', file: 'grails_logo.png')}" alt="Grails"/></a></div>
		<div class="nav" role="navigation">
		    <ul id="supernav">
			  	<li><g:link class="${request.forwardURI.contains('/about') ? 'current' : ''}" controller="about" action="info">About</g:link></li>
				<li><g:link class="${request.forwardURI.contains('/account') ? 'current' : ''}" controller="account">Accounts</g:link></li>
				<li><g:link class="${request.forwardURI.contains('/user') ? 'current' : ''}" controller="user">Users</g:link></li>
				<li><g:link class="${request.forwardURI.contains('/security') ? 'current' : ''}" controller="security">Securities</g:link></li>
				<li><g:link class="${request.forwardURI.contains('/symbol') ? 'current' : ''}" controller="symbol">Symbols</g:link></li>
				<li><g:link class="${request.forwardURI.contains('/holding') ? 'current' : ''}" controller="holding">Holdings</g:link></li>
				<li><g:link class="${request.forwardURI.contains('/report') ? 'current' : ''}" controller="report">Reports</g:link></li>
                <li><g:link class="${request.forwardURI.contains('/accountBalanceSheet') ? 'current' : ''}" controller="accountBalanceSheet" action="indexByYear" params="[year:Calendar.getInstance().get(Calendar.YEAR)]">Cash</g:link></li>
                <li><g:link class="${request.forwardURI.contains('/accountBalanceSheet') ? 'current' : ''}" controller="accountBalanceSheet" action="quarterReport" params="[year:Calendar.getInstance().get(Calendar.YEAR)]">Quarters</g:link></li>
                <li><g:link class="${request.forwardURI.contains('/accountBalanceSheet') ? 'current' : ''}" controller="accountBalanceSheet" action="distributionReport" params="[year:Calendar.getInstance().get(Calendar.YEAR)]">Distrib</g:link></li>
                <li><g:link class="${request.forwardURI.contains('/accountBalanceSheet') ? 'current' : ''}" controller="accountBalanceSheet" action="monthReport" params="[year:Calendar.getInstance().get(Calendar.YEAR)]">Month</g:link></li>
                <li><g:link class="${request.forwardURI.contains('/accountBalanceSheet') ? 'current' : ''}" controller="accountBalanceSheet" action="monthReport" params="[year:2018]">Month2018</g:link></li>
                <li><g:link class="${request.forwardURI.contains('/accountBalanceSheet') ? 'current' : ''}" controller="accountBalanceSheet" action="incomeReport" params="[year:2017]">Income 2017</g:link></li>
                <li><g:link class="${request.forwardURI.contains('/accountBalanceSheet') ? 'current' : ''}" controller="accountBalanceSheet" action="incomeReport" params="[year:2018]">Income 2018</g:link></li>
                <li><g:link class="${request.forwardURI.contains('/accountBalanceSheet') ? 'current' : ''}" controller="accountBalanceSheet" action="yearlyIncomeReport" params="[groupId:3]">Yearly Income</g:link></li>
                <li><g:link class="${request.forwardURI.contains('/accountBalanceSheet') ? 'current' : ''}" controller="accountBalanceSheet" action="yearlyBalanceReport" params="[groupId:3]">Yearly Balance</g:link></li>
		    </ul>
		</div>
        <div id="spinner" class="spinner" style="display:none;">
            <img src="${resource(dir:'images',file:'spinner.gif')}" alt="${message(code:'spinner.alt',default:'Loading...')}" />
        </div>
        <g:layoutBody />
    </body>
</html>