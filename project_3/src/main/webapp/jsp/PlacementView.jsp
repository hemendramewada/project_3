<%@page import="in.co.rays.project_3.controller.PlacementCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<title>Placement View</title>

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
	</div>

	<main>
	<form action="<%=ORSView.PLACEMENT_CTL%>" method="post">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.PlacementDTO"
			scope="request" />

		<div class="row pt-3 pb-4">
			<div class="col-md-4"></div>

			<div class="col-md-4">
				<div class="card">
					<div class="card-body">

						<%
							if (dto.getId() != null && dto.getId() > 0) {
						%>
						<h3 class="text-center text-primary">Update Placement</h3>
						<%
							} else {
						%>
						<h3 class="text-center text-primary">Add Placement</h3>
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

						<!-- Placement Code -->
						<b>Placement Code *</b> <input type="text" name="placementCode"
							class="form-control" placeholder="Enter placement code"
							value="<%=DataUtility.getStringData(dto.getPlacementCode())%>">
						<font color="red"> <%=ServletUtility.getErrorMessage("placementCode", request)%>
						</font> <br>

						<!-- Student Name -->
						<b>Student Name *</b> <input type="text" name="studentName"
							class="form-control" placeholder="Enter Student Name"
							value="<%=DataUtility.getStringData(dto.getStudentName())%>">
						<font color="red"> <%=ServletUtility.getErrorMessage("studentName", request)%>
						</font> <br>

						<!-- Company Name -->
						<b>Company Name *</b> <input type="text" name="companyName"
							class="form-control" placeholder="Enter Company Name"
							value="<%=DataUtility.getStringData(dto.getCompanyName())%>">
						<font color="red"> <%=ServletUtility.getErrorMessage("companyName", request)%>
						</font> <br>

						<!-- Placement Status -->
						<b>Placement Status *</b> <select name="placementStatus"
							class="form-control">
							<option value="">--Select--</option>

							<option value="Selected"
								<%="Selected".equals(dto.getPlacementStatus()) ? "selected" : ""%>>
								Selected</option>

							<option value="Pending"
								<%="Pending".equals(dto.getPlacementStatus()) ? "selected" : ""%>>
								Pending</option>

							<option value="Rejected"
								<%="Rejected".equals(dto.getPlacementStatus()) ? "selected" : ""%>>
								Rejected</option>

						</select> <font color="red"> <%=ServletUtility.getErrorMessage("placementStatus", request)%>
						</font> <br> <br>

						<%
							if (dto.getId() != null && dto.getId() > 0) {
						%>
						<div class="text-center">
							<input type="submit" name="operation" class="btn btn-success"
								value="<%=PlacementCtl.OP_UPDATE%>"> <input
								type="submit" name="operation" class="btn btn-warning"
								value="<%=PlacementCtl.OP_CANCEL%>">
						</div>
						<%
							} else {
						%>
						<div class="text-center">
							<input type="submit" name="operation" class="btn btn-success"
								value="<%=PlacementCtl.OP_SAVE%>"> <input type="submit"
								name="operation" class="btn btn-warning"
								value="<%=PlacementCtl.OP_RESET%>">
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