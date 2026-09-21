<%@page import="in.co.rays.project_3.dto.DecorationDTO"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.DecorationListCtl"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>

<title>Decoration List View</title>

<meta name="viewport" content="width=device-width, initial-scale=1">

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>

<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>

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

<div>

<%@include file="Header.jsp"%>

</div>

<div>

<form action="<%=ORSView.DECORATION_LIST_CTL%>" method="post">

<jsp:useBean id="dto" class="in.co.rays.project_3.dto.DecorationDTO"
	scope="request"></jsp:useBean>

<%

int pageNo = ServletUtility.getPageNo(request);
int pageSize = ServletUtility.getPageSize(request);

int index = ((pageNo - 1) * pageSize) + 1;

int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

List list = ServletUtility.getList(request);

Iterator<DecorationDTO> it = list.iterator();

if (list.size() != 0) {

%>

<center>
<h1 class="text-primary font-weight-bold pt-3">
<font color="black">Decoration List</font>
</h1>
</center>

<div class="row">

<div class="col-md-4"></div>

<%

if (!ServletUtility.getSuccessMessage(request).equals("")) {

%>

<div class="col-md-4 alert alert-success">

<%=ServletUtility.getSuccessMessage(request)%>

</div>

<%

}

%>

<div class="col-md-4"></div>

</div>

<div class="row">

<div class="col-md-4"></div>

<%

if (!ServletUtility.getErrorMessage(request).equals("")) {

%>

<div class="col-md-4 alert alert-danger">

<%=ServletUtility.getErrorMessage(request)%>

</div>

<%

}

%>

<div class="col-md-4"></div>

</div>


<div class="row">

<div class="col-sm-3">

<input class="form-control" type="text"
name="theme"
placeholder="Enter Theme"
value="<%=ServletUtility.getParameter("theme", request)%>">

</div>

<div class="col-sm-3">

<input class="form-control" type="text"
name="vendorName"
placeholder="Enter Vendor Name"
value="<%=ServletUtility.getParameter("vendorName", request)%>">

</div>

<div class="col-sm-2">

<input class="form-control" type="text"
name="cost"
placeholder="Enter Cost"
value="<%=ServletUtility.getParameter("cost", request)%>">

</div>

<div class="col-sm-3">

<input type="submit"
class="btn btn-primary"
name="operation"
value="<%=DecorationListCtl.OP_SEARCH%>">

<input type="submit"
class="btn btn-dark"
name="operation"
value="<%=DecorationListCtl.OP_RESET%>">

</div>

</div>

<br>

<div class="table-responsive">

<table class="table table-dark table-bordered table-hover">

<thead>

<tr style="background-color:#8C8C8C">

<th><input type="checkbox" id="select_all">Select All</th>

<th>S.NO</th>

<th>Theme</th>

<th>Vendor Name</th>

<th>Cost</th>

<th>Edit</th>

</tr>

</thead>

<%

while (it.hasNext()) {

dto = it.next();

%>

<tbody>

<tr>

<td align="center">

<input type="checkbox"
class="checkbox"
name="ids"
value="<%=dto.getId()%>">

</td>

<td align="center"><%=index++%></td>

<td align="center"><%=dto.getTheme()%></td>

<td align="center"><%=dto.getVendorName()%></td>

<td align="center"><%=dto.getCost()%></td>

<td align="center">

<a href="DecorationCtl?id=<%=dto.getId()%>">Edit</a>

</td>

</tr>

</tbody>

<%

}

%>

</table>

</div>


<table width="100%">

<tr>

<td>

<input type="submit"
name="operation"
class="btn btn-warning"
value="<%=DecorationListCtl.OP_PREVIOUS%>"
<%=pageNo > 1 ? "" : "disabled"%>>

</td>

<td>

<input type="submit"
name="operation"
class="btn btn-primary"
value="<%=DecorationListCtl.OP_NEW%>">

</td>

<td>

<input type="submit"
name="operation"
class="btn btn-danger"
value="<%=DecorationListCtl.OP_DELETE%>">

</td>

<td align="right">

<input type="submit"
name="operation"
class="btn btn-warning"
value="<%=DecorationListCtl.OP_NEXT%>"
<%=(nextPageSize != 0) ? "" : "disabled"%>>

</td>

</tr>

</table>

<br>

<%

}

if (list.size() == 0) {

%>

<center>

<h1 class="text-primary font-weight-bold pt-3">
Decoration List
</h1>

</center>

<br><br>

<div style="padding-left:48%;">

<input type="submit"
name="operation"
class="btn btn-primary"
value="<%=DecorationListCtl.OP_BACK%>">

</div>

<%

}

%>

<input type="hidden" name="pageNo" value="<%=pageNo%>">

<input type="hidden" name="pageSize" value="<%=pageSize%>">

</form>

</div>

<br><br>

<%@include file="FooterView.jsp"%>

</body>

</html>