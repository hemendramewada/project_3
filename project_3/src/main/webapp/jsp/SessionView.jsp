<%@page import="in.co.rays.project_3.controller.SessionCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<link rel="stylesheet"
    href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script >
$(function() {
    $("#udate1").datepicker({
        dateFormat: 'dd/mm/yy',
        changeMonth: true,
        changeYear: true,
        yearRange: '1950:2030',   // auto-updates years
           // up to today's date
    });
});</script>
<title>Session View</title>

<style>
.p4 {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/user1.jpg');
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
	<%@include file="calendar.jsp" %>
</div>

<main>
<form action="<%=ORSView.SESSION_CTL%>" method="post">

<jsp:useBean id="dto" class="in.co.rays.project_3.dto.SessionDTO" scope="request"/>

<div class="row pt-3 pb-4">
<div class="col-md-4"></div>

<div class="col-md-4">
<div class="card">
<div class="card-body">

<%
if (dto.getId() != null && dto.getId() > 0) {
%>
<h3 class="text-center text-primary">Update Session</h3>
<%
} else {
%>
<h3 class="text-center text-primary">Add Session</h3>
<%
}
%>

<!-- Success Message -->
<%
if (!ServletUtility.getSuccessMessage(request).equals("")) {
%>
<div class="alert alert-success">
<%=ServletUtility.getSuccessMessage(request)%>
</div>
<%
}
%>

<!-- Error Message -->
<%
if (!ServletUtility.getErrorMessage(request).equals("")) {
%>
<div class="alert alert-danger">
<%=ServletUtility.getErrorMessage(request)%>
</div>
<%
}
%>

<!-- Hidden ID -->
<input type="hidden" name="id" value="<%=dto.getId()%>">

<!-- Session Token -->
<b>Session Token *</b>
<input type="text" name="sessionToken" class="form-control"
	value="<%=DataUtility.getStringData(dto.getSessionToken())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("sessionToken", request)%>
</font>
<br>

<!-- User Name -->
<b>User Name *</b>
<input type="text" name="userName" class="form-control"
	value="<%=DataUtility.getStringData(dto.getUserName())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("userName", request)%>
</font>
<br>

<!-- Login Time -->
<b>Login Time *</b>
<input type="text" id="datepicker3" name="loginTime"
	class="form-control" readonly="readonly"
	value="<%=DataUtility.getDateString(dto.getLoginTime())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("loginTime", request)%>
</font>
<br>

<!-- Logout Time -->
<b>Logout Time</b>
<input type="text" id="udate1" name="logoutTime"
	class="form-control" readonly="readonly"
	value="<%=DataUtility.getDateString(dto.getLogoutTime())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("logoutTime", request)%>
</font>
<br>

<!-- Session Status -->
<b>Session Status *</b>
<select name="sessionStatus" class="form-control">
	<option value="">--Select--</option>

	<option value="Active"
	<%= "Active".equals(dto.getSessionStatus()) ? "selected" : "" %>>
	Active</option>

	<option value="Inactive"
	<%= "Inactive".equals(dto.getSessionStatus()) ? "selected" : "" %>>
	Inactive</option>

</select>
<font color="red">
<%=ServletUtility.getErrorMessage("sessionStatus", request)%>
</font>
<br><br>

<%
if (dto.getId() != null && dto.getId() > 0) {
%>
<div class="text-center">
<input type="submit" name="operation"
	class="btn btn-success"
	value="<%=SessionCtl.OP_UPDATE%>">

<input type="submit" name="operation"
	class="btn btn-warning"
	value="<%=SessionCtl.OP_CANCEL%>">
</div>
<%
} else {
%>
<div class="text-center">
<input type="submit" name="operation"
	class="btn btn-success"
	value="<%=SessionCtl.OP_SAVE%>">

<input type="submit" name="operation"
	class="btn btn-warning"
	value="<%=SessionCtl.OP_RESET%>">
</div>
<%
}
%>

</div>
</div>
</div>

<div class="col-md-4"></div>
</div>

</form>
</main>

<%@include file="FooterView.jsp"%>

</body>
</html>