<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.dto.BugTrackerDTO"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.controller.BugTrackerListCtl"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Bug Tracker List View</title>

<meta name="viewport" content="width=device-width, initial-scale=1">

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>

<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>

<style>
.hm {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/list2.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	padding-top: 85px;
}

.p1 {
	padding: 8px;
}
</style>

</head>

<body class="hm">

	<div>

		<%@include file="Header.jsp"%>
		<%@include file="calendar.jsp"%>

	</div>

	<div>

		<form action="<%=ORSView.BUGTRACKER_LIST_CTL%>" method="post">

			<jsp:useBean id="dto" class="in.co.rays.project_3.dto.BugTrackerDTO"
				scope="request">
			</jsp:useBean>

			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);

				int index = ((pageNo - 1) * pageSize) + 1;

				int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

				List list = ServletUtility.getList(request);

				Iterator<BugTrackerDTO> it = list.iterator();

				if (list.size() != 0) {
			%>

			<center>
				<h1 class="text-primary font-weight-bold pt-3">
					<font color="black"> Bug Tracker List </font>
				</h1>
			</center>

			<br>

			<div class="row">

				<div class="col-md-4"></div>

				<%
					if (!ServletUtility.getSuccessMessage(request).equals("")) {
				%>

				<div class="col-md-4 alert alert-success alert-dismissible"
					style="background-color: #80ff80">

					<button type="button" class="close" data-dismiss="alert">
						&times;</button>

					<h4>

						<font color="#008000"> <%=ServletUtility.getSuccessMessage(request)%>

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

				<div class="col-sm-2">

					<input type="text" name="bugId" class="form-control"
						placeholder="Bug Id" value="<%=dto.getBugId()%>">

				</div>

				<div class="col-sm-3">

					<input type="text" name="title" class="form-control"
						placeholder="Title"
						value="<%=DataUtility.getStringData(dto.getTitle())%>">

				</div>

				<div class="col-sm-3">

					<input type="text" name="assignedTo" class="form-control"
						placeholder="Assigned To"
						value="<%=DataUtility.getStringData(dto.getAssignedTo())%>">

				</div>

				<div class="col-sm-2">

					<input type="submit" class="btn btn-primary btn-md"
						name="operation" value="<%=BugTrackerListCtl.OP_SEARCH%>">

					<input type="submit" class="btn btn-dark btn-md" name="operation"
						value="<%=BugTrackerListCtl.OP_RESET%>">

				</div>

			</div>

			<br>

			<div class="table-responsive">

				<table class="table table-dark table-bordered table-hover">

					<thead>

						<tr style="background-color: #8C8C8C;">

							<th width="10%"><input type="checkbox" id="select_all"
								name="Select"> Select All</th>

							<th>S.NO</th>
							<th>Bug Id</th>
							<th>Title</th>
							<th>Severity</th>
							<th>Assigned To</th>
							<th>Status</th>
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

							<td align="center"><%=index++%></td>

							<td align="center"><%=dto.getBugId()%></td>

							<td align="center"><%=dto.getTitle()%></td>

							<td align="center"><%=dto.getSeverity()%></td>

							<td align="center"><%=dto.getAssignedTo()%></td>

							<td align="center"><%=dto.getStatus()%></td>

							<td align="center"><a
								href="BugTrackerCtl?id=<%=dto.getId()%>"> Edit </a></td>

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
						class="btn btn-secondary btn-md"
						value="<%=BugTrackerListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>

					<td><input type="submit" name="operation"
						class="btn btn-primary btn-md"
						value="<%=BugTrackerListCtl.OP_NEW%>"></td>

					<td><input type="submit" name="operation"
						class="btn btn-danger btn-md"
						value="<%=BugTrackerListCtl.OP_DELETE%>"></td>

					<td align="right"><input type="submit" name="operation"
						class="btn btn-secondary btn-md"
						value="<%=BugTrackerListCtl.OP_NEXT%>"
						<%=(nextPageSize != 0) ? "" : "disabled"%>></td>

				</tr>

			</table>

			<br>

			<%
				}

				if (list.size() == 0) {
			%>

			<center>

				<h1 class="text-primary font-weight-bold pt-3">Bug Tracker List
				</h1>

			</center>

			<br>

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

			<br>

			<div style="padding-left: 48%;">

				<input type="submit" name="operation" class="btn btn-primary btn-md"
					value="<%=BugTrackerListCtl.OP_BACK%>">

			</div>

			<%
				}
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

		</form>

	</div>

	<br>
	<br>

</body>

<%@include file="FooterView.jsp"%>

</html>