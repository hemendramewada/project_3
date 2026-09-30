<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.dto.FreelancerDTO"%>
<%@page import="in.co.rays.project_3.controller.FreelancerListCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Freelancer List View</title>

<meta name="viewport" content="width=device-width, initial-scale=1">

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>

<style>
.hm {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/list2.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	padding-top: 85px;
}
</style>

</head>

<body class="hm">

	<%@include file="Header.jsp"%>

	<form action="<%=ORSView.FREELANCER_LIST_CTL%>" method="post">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.FreelancerDTO"
			scope="request">
		</jsp:useBean>

		<%
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);

			int index = ((pageNo - 1) * pageSize) + 1;

			int nextPageSize = 0;

			if (request.getAttribute("nextListSize") != null) {
				nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
			}

			List list = ServletUtility.getList(request);

			Iterator<FreelancerDTO> it = list.iterator();
		%>

		<center>
			<h1 class="text-primary font-weight-bold pt-3">
				<font color="black">Freelancer List</font>
			</h1>
		</center>

		<br>

		<div class="row">
			<div class="col-md-4"></div>

			<%
				if (!ServletUtility.getSuccessMessage(request).equals("")) {
			%>

			<div class="col-md-4 alert alert-success alert-dismissible">
				<button type="button" class="close" data-dismiss="alert">
					&times;</button>

				<h4>
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font>
				</h4>
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

			<div class="col-md-4 alert alert-danger alert-dismissible">
				<button type="button" class="close" data-dismiss="alert">
					&times;</button>

				<h4>
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
					</font>
				</h4>
			</div>

			<%
				}
			%>

			<div class="col-md-4"></div>
		</div>

		<div class="row">

			<div class="col-sm-2"></div>

			<div class="col-sm-3">
				<input type="text" name="name" class="form-control"
					placeholder="Enter Name"
					value="<%=DataUtility.getStringData(dto.getName())%>">
			</div>

			<div class="col-sm-3">
				<input type="text" name="skill" class="form-control"
					placeholder="Enter Skill"
					value="<%=DataUtility.getStringData(dto.getSkill())%>">
			</div>

			<div class="col-sm-2">

				<input type="submit" class="btn btn-primary" name="operation"
					value="<%=FreelancerListCtl.OP_SEARCH%>"> <input
					type="submit" class="btn btn-dark" name="operation"
					value="<%=FreelancerListCtl.OP_RESET%>">

			</div>

			<div class="col-sm-2"></div>

		</div>

		<br>

		<div class="table-responsive">

			<table class="table table-dark table-bordered table-hover">

				<thead>

					<tr>

						<th width="10%"><input type="checkbox" id="select_all">
							Select All</th>

						<th>S.NO</th>
						<th>Name</th>
						<th>Skill</th>
						<th>Experience</th>
						<th>Hourly Rate</th>
						<th>Edit</th>

					</tr>

				</thead>

				<tbody>

					<%
						while (it.hasNext()) {

							dto = it.next();
					%>

					<tr>

						<td align="center"><input type="checkbox" class="checkbox"
							name="ids" value="<%=dto.getId()%>"></td>

						<td><%=index++%></td>

						<td><%=dto.getName()%></td>

						<td><%=dto.getSkill()%></td>

						<td><%=dto.getExperience()%></td>

						<td><%=dto.getHourlyRate()%></td>

						<td><a href="FreelancerCtl?id=<%=dto.getId()%>"> Edit </a></td>

					</tr>

					<%
						}
					%>

				</tbody>

			</table>

		</div>

		<table width="100%">

			<tr>

				<td><input type="submit" name="operation"
					class="btn btn-secondary"
					value="<%=FreelancerListCtl.OP_PREVIOUS%>"
					<%=pageNo > 1 ? "" : "disabled"%>></td>

				<td><input type="submit" name="operation"
					class="btn btn-primary" value="<%=FreelancerListCtl.OP_NEW%>">
				</td>

				<td><input type="submit" name="operation"
					class="btn btn-danger" value="<%=FreelancerListCtl.OP_DELETE%>">
				</td>

				<td align="right"><input type="submit" name="operation"
					class="btn btn-secondary" value="<%=FreelancerListCtl.OP_NEXT%>"
					<%=(nextPageSize != 0) ? "" : "disabled"%>></td>

			</tr>

		</table>

		<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
			type="hidden" name="pageSize" value="<%=pageSize%>">

	</form>

	<br>
	<br>

	<%@include file="FooterView.jsp"%>

</body>
</html>