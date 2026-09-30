<%@page import="in.co.rays.project_3.controller.FreelancerCtl"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Freelancer View</title>

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

	<div>

		<main>

			<form action="<%=ORSView.FREELANCER_CTL%>" method="post">

				<div class="row pt-3 pb-3">

					<jsp:useBean id="dto"
						class="in.co.rays.project_3.dto.FreelancerDTO" scope="request">
					</jsp:useBean>

					<div class="col-md-4 mb-4"></div>

					<div class="col-md-4 mb-4">

						<div class="card">

							<div class="card-body">

								<%
									if (dto.getId() != null && dto.getId() > 0) {
								%>

								<h3 class="text-center text-primary">Update Freelancer</h3>

								<%
									} else {
								%>

								<h3 class="text-center text-primary">Add Freelancer</h3>

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


								<!-- Name -->

								<label><b>Name</b> <font color="red">*</font></label> <input
									type="text" name="name" class="form-control"
									placeholder="Enter Name"
									value="<%=DataUtility.getStringData(dto.getName())%>">

								<font color="red"> <%=ServletUtility.getErrorMessage("name", request)%>
								</font> <br>


								<!-- Skill -->

								<label><b>Skill</b> <font color="red">*</font></label> <input
									type="text" name="skill" class="form-control"
									placeholder="Enter Skill"
									value="<%=DataUtility.getStringData(dto.getSkill())%>">

								<font color="red"> <%=ServletUtility.getErrorMessage("skill", request)%>
								</font> <br>


								<!-- Experience -->

								<label><b>Experience</b> <font color="red">*</font></label> <input
									type="text" name="experience" class="form-control"
									placeholder="Enter Experience"
									value="<%=dto.getExperience() == 0 ? "" : dto.getExperience()%>">

								<font color="red"> <%=ServletUtility.getErrorMessage("experience", request)%>
								</font> <br>


								<!-- Hourly Rate -->

								<label><b>Hourly Rate</b> <font color="red">*</font></label> <input
									type="text" name="hourlyRate" class="form-control"
									placeholder="Enter Hourly Rate"
									value="<%=dto.getHourlyRate() == 0 ? "" : dto.getHourlyRate()%>">

								<font color="red"> <%=ServletUtility.getErrorMessage("hourlyRate", request)%>
								</font> <br> <br>

								<%
									if (dto.getId() != null && dto.getId() > 0) {
								%>

								<div class="text-center">

									<input type="submit" name="operation"
										class="btn btn-success btn-md"
										value="<%=FreelancerCtl.OP_UPDATE%>"> <input
										type="submit" name="operation" class="btn btn-warning btn-md"
										value="<%=FreelancerCtl.OP_CANCEL%>">

								</div>

								<%
									} else {
								%>

								<div class="text-center">

									<input type="submit" name="operation"
										class="btn btn-success btn-md"
										value="<%=FreelancerCtl.OP_SAVE%>"> <input
										type="submit" name="operation" class="btn btn-warning btn-md"
										value="<%=FreelancerCtl.OP_RESET%>">

								</div>

								<%
									}
								%>
							
</html>