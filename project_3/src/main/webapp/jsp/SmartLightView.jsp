<%@page import="in.co.rays.project_3.controller.SmartLightCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Smart Light View</title>

<meta name="viewport" content="width=device-width, initial-scale=1">

<style>
i.css {
	border: 2px solid #8080803b;
	padding-left: 10px;
	padding-bottom: 11px;
	background-color: #ebebe0;
}

.input-group-addon {
	box-shadow: 9px 8px 7px #001a33;
}

.hm {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/user1.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	padding-top: 75px;
}
</style>

</head>

<body class="hm">

	<div class="header">

		<%@include file="Header.jsp"%>

		<%@include file="calendar.jsp"%>

	</div>

	<div>

		<main>

			<form action="<%=ORSView.SMARTLIGHT_CTL%>" method="post">

				<jsp:useBean id="dto" class="in.co.rays.project_3.dto.SmartLightDTO"
					scope="request">
				</jsp:useBean>

				<div class="row pt-3">

					<div class="col-md-4 mb-4"></div>

					<div class="col-md-4 mb-4">

						<div class="card input-group-addon">

							<div class="card-body">

								<%
									long lightId = DataUtility.getLong(request.getParameter("lightId"));

									if (dto.getLightId() > 0) {
								%>

								<h3 class="text-center text-primary">Update Smart Light 💡

								</h3>

								<%
									} else {
								%>

								<h3 class="text-center text-primary">Add Smart Light 💡</h3>

								<%
									}
								%>

								<h4 align="center">

									<%
										if (!ServletUtility.getSuccessMessage(request).equals("")) {
									%>

									<div class="alert alert-success alert-dismissible">

										<button type="button" class="close" data-dismiss="alert">&times;</button>

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

										<button type="button" class="close" data-dismiss="alert">&times;</button>

										<%=ServletUtility.getErrorMessage(request)%>

									</div>

									<%
										}
									%>

								</h4>

								<input type="hidden" name="lightId"
									value="<%=dto.getLightId()%>"> <input type="hidden"
									name="createdBy" value="<%=dto.getCreatedBy()%>"> <input
									type="hidden" name="modifiedBy"
									value="<%=dto.getModifiedBy()%>"> <input type="hidden"
									name="createdDatetime"
									value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">

								<input type="hidden" name="modifiedDatetime"
									value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">
								<div class="md-form">

									<!-- Light Code -->

									<span class="pl-sm-5"> <b>Light Code 💡</b> <span
										style="color: red;">*</span>
									</span><br>

									<div class="col-sm-12">
										<div class="input-group">

											<div class="input-group-prepend">
												<div class="input-group-text">
													<i class="fa fa-lightbulb-o grey-text"
														style="font-size: 1rem;"></i>
												</div>
											</div>

											<input type="text" class="form-control" name="lightCode"
												placeholder="Enter Light Code"
												value="<%=DataUtility.getStringData(dto.getLightCode())%>">

										</div>
									</div>

									<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("lightCode", request)%>
									</font> <br>


									<!-- Room Name -->

									<span class="pl-sm-5"> <b>Room Name 🏠</b> <span
										style="color: red;">*</span>
									</span><br>

									<div class="col-sm-12">
										<div class="input-group">

											<div class="input-group-prepend">
												<div class="input-group-text">
													<i class="fa fa-home grey-text" style="font-size: 1rem;"></i>
												</div>
											</div>

											<input type="text" class="form-control" name="roomName"
												placeholder="Enter Room Name"
												value="<%=DataUtility.getStringData(dto.getRoomName())%>">

										</div>
									</div>

									<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("roomName", request)%>
									</font> <br>


									<!-- Brightness Level -->

									<span class="pl-sm-5"> <b>Brightness Level 🔆</b> <span
										style="color: red;">*</span>
									</span><br>

									<div class="col-sm-12">
										<div class="input-group">

											<div class="input-group-prepend">
												<div class="input-group-text">
													<i class="fa fa-sun-o grey-text" style="font-size: 1rem;"></i>
												</div>
											</div>

											<input type="number" class="form-control"
												name="brightnessLevel" min="0" max="100"
												placeholder="0 - 100"
												value="<%=dto.getBrightnessLevel() == null ? "" : dto.getBrightnessLevel()%>">

										</div>
									</div>

									<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("brightnessLevel", request)%>
									</font> <br>


									<!-- Status -->

									<span class="pl-sm-5"> <b>Status ⚡</b> <span
										style="color: red;">*</span>
									</span><br>

									<div class="col-sm-12">
										<div class="input-group">

											<div class="input-group-prepend">
												<div class="input-group-text">
													<i class="fa fa-toggle-on grey-text"
														style="font-size: 1rem;"></i>
												</div>
											</div>

											<select name="status" class="form-control">

												<option value="">--Select Status--</option>

												<option value="ON"
													<%=("ON".equals(dto.getStatus())) ? "selected" : ""%>>
													ON</option>

												<option value="OFF"
													<%=("OFF".equals(dto.getStatus())) ? "selected" : ""%>>
													OFF</option>

											</select>

										</div>
									</div>

									<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("status", request)%>
									</font> <br>
									<%
										if (dto.getLightId() > 0) {
									%>

									<div class="text-center">

										<input type="submit" name="operation"
											class="btn btn-success btn-md" style="font-size: 17px"
											value="<%=SmartLightCtl.OP_UPDATE%>"> <input
											type="submit" name="operation" class="btn btn-warning btn-md"
											style="font-size: 17px" value="<%=SmartLightCtl.OP_CANCEL%>">

									</div>

									<%
										} else {
									%>

									<div class="text-center">

										<input type="submit" name="operation"
											class="btn btn-success btn-md" style="font-size: 17px"
											value="<%=SmartLightCtl.OP_SAVE%>"> <input
											type="submit" name="operation" class="btn btn-warning btn-md"
											style="font-size: 17px" value="<%=SmartLightCtl.OP_RESET%>">

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