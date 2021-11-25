<%--
- form.jsp
-
- Copyright (C) 2012-2021 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<h2>
	<acme:message code="administrator.dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<caption>
		<acme:message code="administrator.dashboard.form.title.general-indicators"/>
	</caption>	
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.number-public-duties"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfPublicDuties}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.number-private-duties"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfPrivateDuties}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.number-finished-duties"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfFinishedDuties}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.number-non-finished-duties"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfNonFinishedDuties}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.average-duty-execution-period"/>
		</th>
		<td>
			<acme:print value="${averageDutyExecutionPeriods}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.deviation-duty-execution-period"/>
		</th>
		<td>
			<acme:print value="${deviationDutyExecutionPeriods}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.minimum-duty-execution-period"/>
		</th>
		<td>
			<acme:print value="${minimumDutyExecutionPeriods}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.maximum-duty-execution-period"/>
		</th>
		<td>
			<acme:print value="${maximumDutyExecutionPeriods}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.average-duty-workloads"/>
		</th>
		<td>
			<acme:print value="${averageDutyWorloads}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.deviation-duty-workloads"/>
		</th>
		<td>
			<acme:print value="${deviationDutyWorloads}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.minimum-duty-workloads"/>
		</th>
		<td>
			<acme:print value="${minimumDutyWorloads}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.maximum-duty-workloads"/>
		</th>
		<td>
			<acme:print value="${maximumDutyWorloads}"/>
		</td>
	</tr>
</table>
