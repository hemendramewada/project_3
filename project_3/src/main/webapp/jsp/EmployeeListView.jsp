<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@page import="in.co.rays.project_3.dto.EmployeeDTO"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.EmployeeListCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<title>Employee List View</title>

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>

<style>
.p4 {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/list2.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	padding-top: 85px;
}
</style>

</head>

<body class="p4">

<%@include file="Header.jsp"%>

<form action="<%=ORSView.EMPLOYEE_LIST_CTL%>" method="post">

<jsp:useBean id="dto" class="in.co.rays.project_3.dto.EmployeeDTO"
	scope="request"></jsp:useBean>

<%
	int pageNo = ServletUtility.getPageNo(request);
	int pageSize = ServletUtility.getPageSize(request);
	int index = ((pageNo - 1) * pageSize) + 1;
	int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
	List list = ServletUtility.getList(request);
	Iterator<EmployeeDTO> it = list.iterator();
%>

<center>
	<h1><font color="black">Employee List</font></h1>
</center>

<center>
<font color="green">
<%=ServletUtility.getSuccessMessage(request)%>
</font>

<font color="red">
<%=ServletUtility.getErrorMessage(request)%>
</font>
</center>

<br>

<!-- Search Panel -->
<div align="center">
	Employee Name:
	<input type="text" name="employeeName"
		value="<%=ServletUtility.getParameter("employeeName", request)%>">

	Last Name:
	<input type="text" name="lastName"
		value="<%=ServletUtility.getParameter("lastName", request)%>">

	Department:
	<input type="text" name="department"
		value="<%=ServletUtility.getParameter("department", request)%>">

	<input type="submit" name="operation"
		value="<%=EmployeeListCtl.OP_SEARCH%>">

	<input type="submit" name="operation"
		value="<%=EmployeeListCtl.OP_RESET%>">
</div>

<br>

<%
if (list != null && list.size() > 0) {
%>

<table border="1" width="100%" style="background-color:white">

<tr style="background-color:lightgray;">
	<th><input type="checkbox" id="select_all"></th>
	<th>S.No</th>
	<th>Employee Name</th>
	<th>Last Name</th>
	<th>Department</th>
	<th>Edit</th>
</tr>

<%
while (it.hasNext()) {
	dto = it.next();
%>

<tr>
	<td align="center">
		<input type="checkbox" name="ids" class="checkbox"
			value="<%=dto.getId()%>">
	</td>

	<td align="center"><%=index++%></td>
	<td align="center"><%=dto.getEmployeeName()%></td>
	<td align="center"><%=dto.getLastName()%></td>
	<td align="center"><%=dto.getDepartment()%></td>

	<td align="center">
		<a href="<%=ORSView.EMPLOYEE_CTL%>?id=<%=dto.getId()%>">
		Edit</a>
	</td>
</tr>

<%
}
%>

</table>

<br>

<table width="100%">
<tr>
<td>
<input type="submit" name="operation"
	value="<%=EmployeeListCtl.OP_PREVIOUS%>"
	<%=pageNo > 1 ? "" : "disabled"%>>
</td>

<td align="center">
<input type="submit" name="operation"
	value="<%=EmployeeListCtl.OP_NEW%>">
</td>

<td align="center">
<input type="submit" name="operation"
	value="<%=EmployeeListCtl.OP_DELETE%>">
</td>

<td align="right">
<input type="submit" name="operation"
	value="<%=EmployeeListCtl.OP_NEXT%>"
	<%=(nextPageSize != 0) ? "" : "disabled"%>>
</td>
</tr>
</table>

<%
} else {
%>

<center>
<br><br>
<input type="submit" name="operation"
	value="<%=EmployeeListCtl.OP_BACK%>">
</center>

<%
}
%>

<input type="hidden" name="pageNo" value="<%=pageNo%>">
<input type="hidden" name="pageSize" value="<%=pageSize%>">

</form>

<br><br>

<%@include file="FooterView.jsp"%>

</body>
</html>