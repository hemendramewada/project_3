<%@page import="in.co.rays.project_3.dto.MovieDTO"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.MovieListCtl"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<title>Movie List View</title>
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

<form action="<%=ORSView.MOVIE_LIST_CTL%>" method="post">

<jsp:useBean id="dto" class="in.co.rays.project_3.dto.MovieDTO"
	scope="request"></jsp:useBean>

<%
int pageNo = ServletUtility.getPageNo(request);
int pageSize = ServletUtility.getPageSize(request);

int index = ((pageNo - 1) * pageSize) + 1;

Object nextObj = request.getAttribute("nextListSize");
int nextPageSize = (nextObj != null) ? DataUtility.getInt(nextObj.toString()) : 0;

List list = ServletUtility.getList(request);

Iterator<MovieDTO> it = list.iterator();
%>

<center>
	<h1 class="text-primary font-weight-bold pt-3">
		<font color="black">Movie List</font>
	</h1>
</center>

<!-- Success Message -->
<% if (!ServletUtility.getSuccessMessage(request).equals("")) { %>
<div class="alert alert-success alert-dismissible text-center">
<button type="button" class="close" data-dismiss="alert">&times;</button>
<%=ServletUtility.getSuccessMessage(request)%>
</div>
<% } %>

<!-- Error Message -->
<% if (!ServletUtility.getErrorMessage(request).equals("")) { %>
<div class="alert alert-danger alert-dismissible text-center">
<button type="button" class="close" data-dismiss="alert">&times;</button>
<%=ServletUtility.getErrorMessage(request)%>
</div>
<% } %>

<br>

<div class="row">

<div class="col-sm-3">
<input class="form-control" type="text" name="movieName"
placeholder="Movie Name"
value="<%=ServletUtility.getParameter("movieName", request)%>">
</div>

<div class="col-sm-3">
<input class="form-control" type="text" name="director"
placeholder="Director"
value="<%=ServletUtility.getParameter("director", request)%>">
</div>

<div class="col-sm-2">
<input type="submit" class="btn btn-primary"
name="operation"
value="<%=MovieListCtl.OP_SEARCH%>">
<input type="submit" class="btn btn-dark"
name="operation"
value="<%=MovieListCtl.OP_RESET%>">
</div>

</div>

<br>

<div class="table-responsive">
<table class="table table-dark table-bordered table-hover">

<thead>
<tr style="background-color:#8C8C8C;">
<th width="10%">
<input type="checkbox" id="select_all"> Select All
</th>
<th>S.NO</th>
<th>Movie Name</th>
<th>Director</th>
<th>Producer</th>
<th>Duration</th>
<th>Genre</th>
<th>Language</th>
<th>Release Date</th>
<th>Edit</th>
</tr>
</thead>

<tbody>
<%
while (it.hasNext()) {
	dto = it.next();
%>
<tr>
<td align="center">
<input type="checkbox" class="checkbox"
name="ids" value="<%=dto.getId()%>">
</td>

<td align="center"><%=index++%></td>
<td align="center"><%=dto.getMovieName()%></td>
<td align="center"><%=dto.getDirector()%></td>
<td align="center"><%=dto.getProducer()%></td>
<td align="center"><%=dto.getDuration()%></td>
<td align="center"><%=dto.getGenre()%></td>
<td align="center"><%=dto.getLanguage()%></td>
<td align="center">
<%=DataUtility.getDateString(dto.getReleaseDate())%>
</td>

<td align="center">
<a href="MovieCtl?id=<%=dto.getId()%>">Edit</a>
</td>

</tr>
<%
}
%>
</tbody>

</table>
</div>

<table width="100%">
<tr>

<td>
<input type="submit" name="operation"
class="btn btn-warning"
value="<%=MovieListCtl.OP_PREVIOUS%>"
<%=pageNo > 1 ? "" : "disabled"%>>
</td>

<td>
<input type="submit" name="operation"
class="btn btn-primary"
value="<%=MovieListCtl.OP_NEW%>">
</td>

<td>
<input type="submit" name="operation"
class="btn btn-danger"
value="<%=MovieListCtl.OP_DELETE%>">
</td>

<td align="right">
<input type="submit" name="operation"
class="btn btn-warning"
value="<%=MovieListCtl.OP_NEXT%>"
<%=(nextPageSize != 0) ? "" : "disabled"%>>
</td>

</tr>
</table>

<input type="hidden" name="pageNo" value="<%=pageNo%>">
<input type="hidden" name="pageSize" value="<%=pageSize%>">

</form>

<%@include file="FooterView.jsp"%>

</body>
</html>