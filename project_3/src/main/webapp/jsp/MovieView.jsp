<%@page import="in.co.rays.project_3.controller.MovieCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Movie View</title>

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
	<%@include file="calendar.jsp"%>   <!-- SAME AS STUDENT -->
</div>

<div>
<jsp:useBean id="dto" class="in.co.rays.project_3.dto.MovieDTO" scope="request"></jsp:useBean>

<main>
<form action="<%=ORSView.MOVIE_CTL%>" method="post">

<div class="row pt-3 pb-3">
<div class="col-md-4 mb-4"></div>

<div class="col-md-4 mb-4">
<div class="card">
<div class="card-body">

<%
long id = DataUtility.getLong(request.getParameter("id"));
if (dto != null && id > 0) {
%>
<h3 class="text-center text-primary">Update Movie</h3>
<%
} else {
%>
<h3 class="text-center text-primary">Add Movie</h3>
<%
}
%>

<h4 align="center">
<% if (!ServletUtility.getSuccessMessage(request).equals("")) { %>
<div class="alert alert-success alert-dismissible">
<button type="button" class="close" data-dismiss="alert">&times;</button>
<%=ServletUtility.getSuccessMessage(request)%>
</div>
<% } %>
</h4>

<h4 align="center">
<% if (!ServletUtility.getErrorMessage(request).equals("")) { %>
<div class="alert alert-danger alert-dismissible">
<button type="button" class="close" data-dismiss="alert">&times;</button>
<%=ServletUtility.getErrorMessage(request)%>
</div>
<% } %>
</h4>

<input type="hidden" name="id" value="<%=dto.getId()%>">
<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
<input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">
<input type="hidden" name="createdDatetime"
	value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
<input type="hidden" name="modifiedDatetime"
	value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

<b>Movie Name <span style="color:red">*</span></b>
<input type="text" class="form-control" name="movieName"
	value="<%=DataUtility.getStringData(dto.getMovieName())%>">
<font color="red"><%=ServletUtility.getErrorMessage("movieName", request)%></font>
<br>

<b>Director <span style="color:red">*</span></b>
<input type="text" class="form-control" name="director"
	value="<%=DataUtility.getStringData(dto.getDirector())%>">
<font color="red"><%=ServletUtility.getErrorMessage("director", request)%></font>
<br>

<b>Producer</b>
<input type="text" class="form-control" name="producer"
	value="<%=DataUtility.getStringData(dto.getProducer())%>">
<br>

<b>Duration</b>
<input type="text" class="form-control" name="duration"
	value="<%=DataUtility.getStringData(dto.getDuration())%>">
<br>

<b>Genre</b>
<input type="text" class="form-control" name="genre"
	value="<%=DataUtility.getStringData(dto.getGenre())%>">
<br>

<b>Language</b>
<input type="text" class="form-control" name="language"
	value="<%=DataUtility.getStringData(dto.getLanguage())%>">
<br>

<!-- SAME AS STUDENT DOB FIELD STYLE -->
<b>Release Date <span style="color:red">*</span></b>
<input type="text" id="datepicker" name="releaseDate"
	class="form-control" readonly="readonly"
	value="<%=DataUtility.getDateString(dto.getReleaseDate())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("releaseDate", request)%>
</font>
<br>

<%
if (id > 0) {
%>
<div class="text-center">
<input type="submit" class="btn btn-success"
	name="operation" value="<%=MovieCtl.OP_UPDATE%>">
<input type="submit" class="btn btn-warning"
	name="operation" value="<%=MovieCtl.OP_CANCEL%>">
</div>
<%
} else {
%>
<div class="text-center">
<input type="submit" class="btn btn-success"
	name="operation" value="<%=MovieCtl.OP_SAVE%>">
<input type="submit" class="btn btn-warning"
	name="operation" value="<%=MovieCtl.OP_RESET%>">
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

<%@include file="FooterView.jsp"%>

</body>
</html>