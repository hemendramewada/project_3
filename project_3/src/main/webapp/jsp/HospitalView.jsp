<%@page import="in.co.rays.project_3.controller.HospitalCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<title>Hospital View</title>

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
	<form action="<%=ORSView.HOSPITAL_CTL%>" method="post">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.HospitalDTO"
			scope="request" />

		<div class="row pt-3 pb-4">
			<div class="col-md-4"></div>

			<div class="col-md-4">
				<div class="card">
					<div class="card-body">

						<%
							if (dto.getId() != null && dto.getId() > 0) {
						%>
						<h3 class="text-center text-primary">Update Hospital</h3>
						<%
							} else {
						%>
						<h3 class="text-center text-primary">Add Hospital</h3>
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

						<input type="hidden" name="id" value="<%=dto.getId()%>">

						<!-- Hospital ID -->
						<b>Hospital ID *</b> <input type="text" name="hospitalId"
							class="form-control"
							value="<%=DataUtility.getStringData(dto.getHospitalId())%>">
						<font color="red"> <%=ServletUtility.getErrorMessage("hospitalId", request)%>
						</font> <br>

						<!-- Hospital Name -->
						<b>Hospital Name *</b> <input type="text" name="hospitalName"
							class="form-control"
							value="<%=DataUtility.getStringData(dto.getHospitalName())%>">
						<font color="red"> <%=ServletUtility.getErrorMessage("hospitalName", request)%>
						</font> <br>

						<!-- City -->
						<b>City *</b> <input type="text" name="city" class="form-control"
							value="<%=DataUtility.getStringData(dto.getCity())%>"> <font
							color="red"> <%=ServletUtility.getErrorMessage("city", request)%>
						</font> <br>

						<!-- Contact Number -->
						<b>Contact Number *</b> <input type="text" name="contactNumber"
							class="form-control"
							value="<%=DataUtility.getStringData(dto.getContactNumber())%>">
						<font color="red"> <%=ServletUtility.getErrorMessage("contactNumber", request)%>
						</font> <br>
						<br>

						<%
							if (dto.getId() != null && dto.getId() > 0) {
						%>
						<div class="text-center">
							<input type="submit" name="operation" class="btn btn-success"
								value="<%=HospitalCtl.OP_UPDATE%>"> <input type="submit"
								name="operation" class="btn btn-warning"
								value="<%=HospitalCtl.OP_CANCEL%>">
						</div>
						<%
							} else {
						%>
						<div class="text-center">
							<input type="submit" name="operation" class="btn btn-success"
								value="<%=HospitalCtl.OP_SAVE%>"> <input type="submit"
								name="operation" class="btn btn-warning"
								value="<%=HospitalCtl.OP_RESET%>">
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