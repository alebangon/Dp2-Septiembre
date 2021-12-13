<%--
- list.jsp
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

<acme:list readonly="true">
	<acme:list-column code="anonymous.shout.list.label.moment" path="moment"/>
	<acme:list-column code="anonymous.shout.list.label.author" path="author" />
	<acme:list-column code="anonymous.shout.list.label.text" path="text" />
	<acme:list-column code="anonymous.shout.form.label.optionalLink" path="optionalLink"/>
	<acme:list-column code="anonymous.shout.list.label.X1" path="X.X1" />
	<acme:list-column code="anonymous.shout.list.label.X2" path="X.X2" />
	<acme:list-column code="anonymous.shout.list.label.X3" path="X.X3" />
	<acme:list-column code="anonymous.shout.list.label.isImportant" path="X.isImportant" />
</acme:list>


