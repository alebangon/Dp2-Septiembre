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
			<acme:message code="administrator.dashboard.form.label.number-public-tasks"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfPublicTasks}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.number-private-tasks"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfPrivateTasks}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.number-finished-task"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfFinishedTasks}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.number-non-finished-tasks"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfNonFinishedTasks}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.average-task-execution-period"/>
		</th>
		<td>
			<acme:print value="${averageTaskExecutionPeriods}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.deviation-task-execution-period"/>
		</th>
		<td>
			<acme:print value="${deviationTaskExecutionPeriods}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.minimum-task-execution-period"/>
		</th>
		<td>
			<acme:print value="${minimumTaskExecutionPeriods}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.maximum-task-execution-period"/>
		</th>
		<td>
			<acme:print value="${maximumTaskExecutionPeriods}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.average-task-workloads"/>
		</th>
		<td>
			<acme:print value="${averageTaskWorloads}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.deviation-task-workloads"/>
		</th>
		<td>
			<acme:print value="${deviationTaskWorloads}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.minimum-task-workloads"/>
		</th>
		<td>
			<acme:print value="${minimumTaskWorloads}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.maximum-task-workloads"/>
		</th>
		<td>
			<acme:print value="${maximumTaskWorloads}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.deviationEUR"/>
		</th>
		<td>
			<acme:print value="${devEUR}"/>
		</td>
	</tr>
		<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.deviationUSD"/>
		</th>
		<td>
			<acme:print value="${devUSD}"/>
		</td>
	</tr>
			<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.averageEUR"/>
		</th>
		<td>
			<acme:print value="${avgEUR}"/>
		</td>
	</tr>
			<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.averageUSD"/>
		</th>
		<td>
			<acme:print value="${avgUSD}"/>
		</td>
	</tr>
</table>
