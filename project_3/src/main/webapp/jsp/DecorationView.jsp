<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@page import="in.co.rays.project_3.controller.DecorationCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.dto.DecorationDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<title>Decoration View</title>

<meta name="viewport" content="width=device-width, initial-scale=1">

<style type="text/css">

.p4{
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

</div>

<div>

<jsp:useBean id="dto" class="in.co.rays.project_3.dto.DecorationDTO" scope="request"></jsp:useBean>

<main>

<form action="<%=ORSView.DECORATION_CTL%>" method="post">

<div class="row pt-3 pb-3">

<div class="col-md-4 mb-4"></div>

<div class="col-md-4 mb-4">

<div class="card">

<div class="card-body">

<%

long id = DataUtility.getLong(request.getParameter("id"));

if(dto!=null && id>0){

%>

<h3 class="text-center text-primary">Update Decoration</h3>

<%

}else{

%>

<h3 class="text-center text-primary">Add Decoration</h3>

<%

}

%>

<div>

<h4 align="center">

<%

if(!ServletUtility.getSuccessMessage(request).equals("")){

%>

<div class="alert alert-success">

<%=ServletUtility.getSuccessMessage(request)%>

</div>

<%

}

%>

</h4>

<h4 align="center">

<%

if(!ServletUtility.getErrorMessage(request).equals("")){

%>

<div class="alert alert-danger">

<%=ServletUtility.getErrorMessage(request)%>

</div>

<%

}

%>

</h4>

<input type="hidden" name="id" value="<%=dto.getId()%>">

<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">

<input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">

<input type="hidden" name="createdDatetime"
value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">

<input type="hidden" name="modifiedDatetime"
value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

</div>


<span><b>Theme</b><span style="color:red">*</span></span>

<input type="text" name="theme" class="form-control"
placeholder="Enter Theme"
value="<%=DataUtility.getStringData(dto.getTheme())%>">

<font color="red">
<%=ServletUtility.getErrorMessage("theme", request)%>
</font>

<br>


<span><b>Vendor Name</b><span style="color:red">*</span></span>

<input type="text" name="vendorName" class="form-control"
placeholder="Enter Vendor Name"
value="<%=DataUtility.getStringData(dto.getVendorName())%>">

<font color="red">
<%=ServletUtility.getErrorMessage("vendorName", request)%>
</font>

<br>


<span><b>Cost</b><span style="color:red">*</span></span>

<input type="text" name="cost" class="form-control"
placeholder="Enter Cost"
value="<%=DataUtility.getStringData(dto.getCost())%>">

<font color="red">
<%=ServletUtility.getErrorMessage("cost", request)%>
</font>

<br>


<%

if(id>0){

%>

<div class="text-center">

<input type="submit" class="btn btn-success"
name="operation"
value="<%=DecorationCtl.OP_UPDATE%>">

<input type="submit"
class="btn btn-warning"
name="operation"
value="<%=DecorationCtl.OP_CANCEL%>">

</div>

<%

}else{

%>

<div class="text-center">

<input type="submit"
name="operation"
class="btn btn-success"
value="<%=DecorationCtl.OP_SAVE%>">

<input type="submit"
name="operation"
class="btn btn-warning"
value="<%=DecorationCtl.OP_RESET%>">

</div>

<%

}

%>

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