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

<acme:form>
	<acme:form-textbox code="officer.duty.form.label.title" path="title"/>
	<acme:form-moment code="officer.duty.form.label.executionPeriodInit" path="executionPeriodInit"/>
	<acme:form-moment code="officer.duty.form.label.executionPeriodEnd" path="executionPeriodEnd"/>
	<acme:form-textarea code="officer.duty.form.label.description" path="description"/>
	<acme:form-url code="officer.duty.form.label.optionalLink" path="optionalLink"/>
	
	<jstl:if test="${command == 'show'}">
		<acme:form-textbox readonly="true" code="officer.duty.form.label.workLoad" path="workLoad"/>
	</jstl:if>
	<acme:form-submit test="${command == 'create'}" code="officer.duty.form.button.create" action="/officer/duty/create"/>
	<acme:form-submit test="${command == 'show'}" code="officer.duty.form.button.update" action="/officer/duty/update"/>
	<acme:form-submit test="${command == 'show'}" code="officer.duty.form.button.delete" action="/officer/duty/delete"/>
	<acme:form-submit test="${command == 'delete'}" code="officer.duty.form.button.delete" action="/officer/duty/delete"/>
  	<acme:form-return code="officer.duty.form.button.return"/>
</acme:form>
