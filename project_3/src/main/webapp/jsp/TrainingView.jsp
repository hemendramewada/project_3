<%@page import="in.co.rays.project_3.controller.EmployeeCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@page import="in.co.rays.project_3.dto.EmployeeDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employee View</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<style type="text/css">
.p4 {
	background-image:
		url('<%=ORSView.APP_CONTEXT%>/img/user1.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	padding-top: 75px;
}
</style>

</head>

<body class="p4">

<div class="header">
	<%@include file="Header.jsp"%>
	<%@include file="calendar.jsp"%>
</div>

<div>

<jsp:useBean id="dto"
	class="in.co.rays.project_3.dto.EmployeeDTO"
	scope="request"></jsp:useBean>

<main>
<form action="<%=ORSView.EMPLOYEE_CTL%>" method="post">

<div class="row pt-3 pb-3">
<div class="col-md-4 mb-4"></div>

<div class="col-md-4 mb-4">
<div class="card">
<div class="card-body">

<%
	long id = DataUtility.getLong(request.getParameter("id"));
	if (dto != null && id > 0) {
%>
<h3 class="text-center default-text text-primary">
	Update Employee
</h3>
<% } else { %>
<h3 class="text-center default-text text-primary">
	Add Employee
</h3>
<% } %>

<!-- Success Message -->
<H4 align="center">
<%
if (!ServletUtility.getSuccessMessage(request).equals("")) {
%>
<div class="alert alert-success alert-dismissible">
<button type="button" class="close"
	data-dismiss="alert">&times;</button>
<%=ServletUtility.getSuccessMessage(request)%>
</div>
<% } %>
</H4>

<!-- Error Message -->
<H4 align="center">
<%
if (!ServletUtility.getErrorMessage(request).equals("")) {
%>
<div class="alert alert-danger alert-dismissible">
<button type="button" class="close"
	data-dismiss="alert">&times;</button>
<%=ServletUtility.getErrorMessage(request)%>
</div>
<% } %>
</H4>

<input type="hidden" name="id"
	value="<%=dto.getId()%>">
<input type="hidden" name="createdBy"
	value="<%=dto.getCreatedBy()%>">
<input type="hidden" name="modifiedBy"
	value="<%=dto.getModifiedBy()%>">
<input type="hidden" name="createdDatetime"
	value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
<input type="hidden" name="modifiedDatetime"
	value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

<!-- Employee Name -->
<span class="pl-sm-5"><b>Employee Name</b>
<span style="color:red;">*</span></span><br>

<div class="col-sm-12">
<div class="input-group">
<div class="input-group-prepend">
<div class="input-group-text">
<i class="fa fa-user grey-text"></i>
</div>
</div>
<input type="text" class="form-control"
	name="employeeName"
	value="<%=DataUtility.getStringData(dto.getEmployeeName())%>">
</div>
</div>

<font color="red" class="pl-sm-5">
<%=ServletUtility.getErrorMessage("employeeName",request)%>
</font><br>

<!-- Last Name -->
<span class="pl-sm-5"><b>Last Name</b>
<span style="color:red;">*</span></span><br>

<div class="col-sm-12">
<div class="input-group">
<div class="input-group-prepend">
<div class="input-group-text">
<i class="fa fa-user-circle grey-text"></i>
</div>
</div>
<input type="text" class="form-control"
	name="lastName"
	value="<%=DataUtility.getStringData(dto.getLastName())%>">
</div>
</div>

<font color="red" class="pl-sm-5">
<%=ServletUtility.getErrorMessage("lastName",request)%>
</font><br>

<!-- Department -->
<span class="pl-sm-5"><b>Department</b>
<span style="color:red;">*</span></span><br>

<div class="col-sm-12">
<div class="input-group">
<div class="input-group-prepend">
<div class="input-group-text">
<i class="fa fa-building grey-text"></i>
</div>
</div>
<input type="text" class="form-control"
	name="department"
	value="<%=DataUtility.getStringData(dto.getDepartment())%>">
</div>
</div>

<font color="red" class="pl-sm-5">
<%=ServletUtility.getErrorMessage("department",request)%>
</font><br>

<!-- DOB -->
<span class="pl-sm-5"><b>DOB</b>
<span style="color:red;">*</span></span><br>

<div class="col-sm-12">
<div class="input-group">
<div class="input-group-prepend">
<div class="input-group-text">
<i class="fa fa-calendar grey-text"></i>
</div>
</div>
<input type="text" id="datepicker"
	name="dob"
	class="form-control"
	readonly="readonly"
	value="<%=DataUtility.getDateString(dto.getDob())%>">
</div>
</div>

<font color="red" class="pl-sm-5">
<%=ServletUtility.getErrorMessage("dob",request)%>
</font><br>

<% if(id>0) { %>

<div class="text-center">
<input type="submit"
	class="btn btn-success"
	name="operation"
	value="<%=EmployeeCtl.OP_UPDATE%>">

<input type="submit"
	class="btn btn-warning"
	name="operation"
	value="<%=EmployeeCtl.OP_CANCEL%>">
</div>

<% } else { %>

<div class="text-center">
<input type="submit"
	class="btn btn-success btn-md"
	name="operation"
	value="<%=EmployeeCtl.OP_SAVE%>">

<input type="submit"
	class="btn btn-warning btn-md"
	name="operation"
	value="<%=EmployeeCtl.OP_RESET%>">
</div>

<% } %>

</div>
</div>
</div>

<div class="col-md-4 mb-4"></div>
</div>

</form>
</main>
</div>

</body>
<%@include file="FooterView.jsp"%>
</html>