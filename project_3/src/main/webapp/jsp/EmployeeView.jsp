<%@page import="in.co.rays.project_3.controller.EmployeeCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@page import="in.co.rays.project_3.dto.EmployeeDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Employee View</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script>
	$(function() {
		$("#datepicker").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : '1950:2025',
			dateFormat : 'dd/mm/yy'
		});
	});
</script>

<style>
body.p4 {
	background: linear-gradient(rgba(0,0,0,0.7), rgba(0,0,0,0.7)),
	url('<%=ORSView.APP_CONTEXT%>/img/user1.jpg');
	background-size: cover;
	background-position: center;
	min-height: 100vh;
	font-family: 'Segoe UI', sans-serif;
}

.form-card {
	background: rgba(255, 255, 255, 0.08);
	backdrop-filter: blur(10px);
	padding: 40px;
	border-radius: 15px;
	box-shadow: 0 0 25px rgba(0,0,0,0.6);
	width: 400px;
	margin: 80px auto;
	color: white;
}

.form-card h3 {
	text-align: center;
	margin-bottom: 25px;
	color: #00c6ff;
}

.form-card input[type="text"] {
	width: 100%;
	padding: 8px;
	margin-top: 5px;
	margin-bottom: 10px;
	border-radius: 8px;
	border: none;
}

.btn-custom {
	padding: 8px 20px;
	border-radius: 8px;
	font-weight: 600;
	border: none;
	cursor: pointer;
}

.btn-save {
	background-color: #28a745;
	color: white;
}

.btn-reset {
	background-color: #dc3545;
	color: white;
}

.error {
	color: #ff4d4d;
	font-size: 13px;
}

.success {
	color: #00ff99;
}
</style>

</head>

<body class="p4">

<%@include file="Header.jsp"%>
<%@include file="calendar.jsp"%>

<jsp:useBean id="dto"
	class="in.co.rays.project_3.dto.EmployeeDTO"
	scope="request" />

<%
	long id = DataUtility.getLong(request.getParameter("id"));
%>

<main>
<form action="<%=ORSView.EMPLOYEE_CTL%>" method="post">

<input type="hidden" name="id" value="<%=dto.getId()%>">
<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
<input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">
<input type="hidden" name="createdDatetime"
	value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
<input type="hidden" name="modifiedDatetime"
	value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

<div class="form-card">

<% if (id > 0) { %>
	<h3>Update Employee</h3>
<% } else { %>
	<h3>Add Employee</h3>
<% } %>

<div class="success">
<%=ServletUtility.getSuccessMessage(request)%>
</div>

<div class="error">
<%=ServletUtility.getErrorMessage(request)%>
</div>

<label>Employee Name *</label>
<input type="text" name="employeeName"
	value="<%=DataUtility.getStringData(dto.getEmployeeName())%>">
<div class="error">
<%=ServletUtility.getErrorMessage("employeeName", request)%>
</div>

<label>Last Name *</label>
<input type="text" name="lastName"
	value="<%=DataUtility.getStringData(dto.getLastName())%>">
<div class="error">
<%=ServletUtility.getErrorMessage("lastName", request)%>
</div>

<label>Department *</label>
<input type="text" name="department"
	value="<%=DataUtility.getStringData(dto.getDepartment())%>">
<div class="error">
<%=ServletUtility.getErrorMessage("department", request)%>
</div>

<label>DOB *</label>
<input type="text" id="datepicker" name="dob" readonly="readonly"
	value="<%=DataUtility.getDateString(dto.getDob())%>">
<div class="error">
<%=ServletUtility.getErrorMessage("dob", request)%>
</div>

<br>

<% if (id > 0) { %>

	<input type="submit" name="operation"
		class="btn-custom btn-save"
		value="<%=EmployeeCtl.OP_UPDATE%>">

	<input type="submit" name="operation"
		class="btn-custom btn-reset"
		value="<%=EmployeeCtl.OP_CANCEL%>">

<% } else { %>

	<input type="submit" name="operation"
		class="btn-custom btn-save"
		value="<%=EmployeeCtl.OP_SAVE%>">

	<input type="submit" name="operation"
		class="btn-custom btn-reset"
		value="<%=EmployeeCtl.OP_RESET%>">

<% } %>

</div>

</form>
</main>

<%@include file="FooterView.jsp"%>

</body>
</html>