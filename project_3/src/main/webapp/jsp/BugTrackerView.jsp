<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.controller.BugTrackerCtl"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Bug Tracker View</title>

<meta name="viewport" content="width=device-width, initial-scale=1">

<style type="text/css">
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
	</div>

	<main>

		<form action="<%=ORSView.BUGTRACKER_CTL%>" method="post">

			<div class="row pt-3 pb-3">

				<jsp:useBean id="dto" class="in.co.rays.project_3.dto.BugTrackerDTO"
					scope="request">
				</jsp:useBean>

				<div class="col-md-4 mb-4"></div>

				<div class="col-md-4 mb-4">

					<div class="card">

						<div class="card-body">

							<%
								if (dto.getId() != null && dto.getId() > 0) {
							%>

							<h3 class="text-center text-primary">Update Bug Tracker</h3>

							<%
								} else {
							%>

							<h3 class="text-center text-primary">Add Bug Tracker</h3>

							<%
								}
							%>

							<h4 align="center">

								<%
									if (!ServletUtility.getSuccessMessage(request).equals("")) {
								%>

								<div class="alert alert-success alert-dismissible">

									<button type="button" class="close" data-dismiss="alert">
										&times;</button>

									<%=ServletUtility.getSuccessMessage(request)%>

								</div>

								<%
									}
								%>

							</h4>

							<h4 align="center">

								<%
									if (!ServletUtility.getErrorMessage(request).equals("")) {
								%>

								<div class="alert alert-danger alert-dismissible">

									<button type="button" class="close" data-dismiss="alert">
										&times;</button>

									<%=ServletUtility.getErrorMessage(request)%>

								</div>

								<%
									}
								%>

							</h4>

							<input type="hidden" name="id" value="<%=dto.getId()%>">

							<input type="hidden" name="createdBy"
								value="<%=dto.getCreatedBy()%>"> <input type="hidden"
								name="modifiedBy" value="<%=dto.getModifiedBy()%>"> <input
								type="hidden" name="createdDatetime"
								value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">

							<input type="hidden" name="modifiedDatetime"
								value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">


							<!-- Bug Id -->

							<span class="pl-sm-5"> <b>Bug Id</b> <span
								style="color: red;">*</span>
							</span>

							<div class="col-sm-12">

								<div class="input-group">

									<div class="input-group-prepend">

										<div class="input-group-text">
											<i class="fa fa-bug"></i>
										</div>

									</div>
									<input type="text" class="form-control" name="bugId"
										placeholder="Enter Bug Id"
										value="<%=dto.getBugId() == 0 ? "" : dto.getBugId()%>">

								</div>

							</div>

							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("bugId", request)%>

							</font> <br>


							<!-- Title -->

							<span class="pl-sm-5"> <b>Title</b> <span
								style="color: red;">*</span>
							</span>

							<div class="col-sm-12">

								<div class="input-group">

									<div class="input-group-prepend">

										<div class="input-group-text">
											<i class="fa fa-file"></i>
										</div>

									</div>

									<input type="text" class="form-control" name="title"
										placeholder="Enter Title"
										value="<%=DataUtility.getStringData(dto.getTitle())%>">

								</div>

							</div>

							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("title", request)%>

							</font> <br>


							<!-- Severity -->

							<%
								HashMap severityMap = new HashMap();

								severityMap.put("Low", "Low");
								severityMap.put("Medium", "Medium");
								severityMap.put("High", "High");
								severityMap.put("Critical", "Critical");

								String severityList = HTMLUtility.getList("severity", dto.getSeverity(), severityMap);
							%>

							<span class="pl-sm-5"> <b>Severity</b> <span
								style="color: red;">*</span>
							</span>

							<div class="col-sm-12">

								<div class="input-group">

									<div class="input-group-prepend">

										<div class="input-group-text">
											<i class="fa fa-warning"></i>
										</div>

									</div>

									<%=severityList%>

								</div>

							</div>

							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("severity", request)%>

							</font> <br>


							<!-- Assigned To -->

							<span class="pl-sm-5"> <b>Assigned To</b> <span
								style="color: red;">*</span>
							</span>

							<div class="col-sm-12">

								<div class="input-group">

									<div class="input-group-prepend">

										<div class="input-group-text">
											<i class="fa fa-user"></i>
										</div>

									</div>

									<input type="text" class="form-control" name="assignedTo"
										placeholder="Enter Assigned To"
										value="<%=DataUtility.getStringData(dto.getAssignedTo())%>">

								</div>

							</div>

							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("assignedTo", request)%>

							</font> <br>


							<!-- Status -->

							<%
								HashMap statusMap = new HashMap();

								statusMap.put("Open", "Open");
								statusMap.put("In Progress", "In Progress");
								statusMap.put("Resolved", "Resolved");
								statusMap.put("Closed", "Closed");

								String statusList = HTMLUtility.getList("status", dto.getStatus(), statusMap);
							%>

							<span class="pl-sm-5"> <b>Status</b> <span
								style="color: red;">*</span>
							</span>

							<div class="col-sm-12">

								<div class="input-group">

									<div class="input-group-prepend">

										<div class="input-group-text">
											<i class="fa fa-tasks"></i>
										</div>

									</div>

									<%=statusList%>

								</div>

							</div>

							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("status", request)%>

							</font> <br> <br>

							<div class="text-center">

								<%
									if (dto.getId() != null && dto.getId() > 0) {
								%>

								<input type="submit" name="operation"
									class="btn btn-success btn-md"
									value="<%=BugTrackerCtl.OP_UPDATE%>"> <input
									type="submit" name="operation" class="btn btn-warning btn-md"
									value="<%=BugTrackerCtl.OP_CANCEL%>">

								<%
									} else {
								%>

								<input type="submit" name="operation"
									class="btn btn-success btn-md"
									value="<%=BugTrackerCtl.OP_SAVE%>"> <input
									type="submit" name="operation" class="btn btn-warning btn-md"
									value="<%=BugTrackerCtl.OP_RESET%>">

								<%
									}
								%>

							</div>

						</div>

					</div>

				</div>

				<div class="col-md-4 mb-4"></div>

			</div>

		</form>

	</main>

</body>

<%@include file="FooterView.jsp"%>

</html>