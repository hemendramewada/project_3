<%@page import="in.co.rays.project_3.controller.VoiceCommandCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Voice Command View</title>

<meta name="viewport" content="width=device-width, initial-scale=1">

<style type="text/css">
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

			<form action="<%=ORSView.VOICE_COMMAND_CTL%>" method="post">

				<jsp:useBean id="dto"
					class="in.co.rays.project_3.dto.VoiceCommandDTO" scope="request">
				</jsp:useBean>

				<div class="row pt-3">

					<div class="col-md-4 mb-4"></div>

					<div class="col-md-4 mb-4">

						<div class="card input-group-addon">

							<div class="card-body">

								<%
									long id = DataUtility.getLong(request.getParameter("id"));

									if (dto.getCommandId() > 0) {
								%>

								<h3 class="text-center default-text text-primary">Update
									Voice Command</h3>

								<%
									} else {
								%>

								<h3 class="text-center default-text text-primary">Add Voice
									Command</h3>

								<%
									}
								%>

								<div>

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

									<input type="hidden" name="id" value="<%=dto.getCommandId()%>">

									<input type="hidden" name="createdBy"
										value="<%=dto.getCreatedBy()%>"> <input type="hidden"
										name="modifiedBy" value="<%=dto.getModifiedBy()%>"> <input
										type="hidden" name="createdDatetime"
										value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">

									<input type="hidden" name="modifiedDatetime"
										value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

								</div>

								<div class="md-form">
									<span class="pl-sm-5"> <b>Command Code</b> <span
										style="color: red;">*</span>
									</span> <br>

									<div class="col-sm-12">
										<div class="input-group">

											<div class="input-group-prepend">
												<div class="input-group-text">
													<i class="fa fa-code grey-text" style="font-size: 1rem;"></i>
												</div>
											</div>

											<input type="text" class="form-control" name="commandCode"
												placeholder="Command Code"
												value="<%=DataUtility.getStringData(dto.getCommandCode())%>">

										</div>
									</div>

									<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("commandCode", request)%>
									</font> <br> <span class="pl-sm-5"> <b>User Name</b> <span
										style="color: red;">*</span>
									</span> <br>

									<div class="col-sm-12">
										<div class="input-group">

											<div class="input-group-prepend">
												<div class="input-group-text">
													<i class="fa fa-user grey-text" style="font-size: 1rem;"></i>
												</div>
											</div>

											<input type="text" class="form-control" name="userName"
												placeholder="User Name"
												value="<%=DataUtility.getStringData(dto.getUserName())%>">

										</div>
									</div>

									<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("userName", request)%>
									</font> <br> <span class="pl-sm-5"> <b>Command Text</b> <span
										style="color: red;">*</span>
									</span> <br>

									<div class="col-sm-12">
										<div class="input-group">

											<div class="input-group-prepend">
												<div class="input-group-text">
													<i class="fa fa-microphone grey-text"
														style="font-size: 1rem;"></i>
												</div>
											</div>

											<input type="text" class="form-control" name="commandText"
												placeholder="Command Text"
												value="<%=DataUtility.getStringData(dto.getCommandText())%>">

										</div>
									</div>

									<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("commandText", request)%>
									</font> <br> <span class="pl-sm-5"> <b>Status</b> <span
										style="color: red;">*</span>
									</span> <br>

									<div class="col-sm-12">
										<div class="input-group">

											<div class="input-group-prepend">
												<div class="input-group-text">
													<i class="fa fa-check-circle grey-text"
														style="font-size: 1rem;"></i>
												</div>
											</div>

											<select class="form-control" name="status">

												<option value="">--Select Status--</option>

												<option value="Active"
													<%=("Active".equals(dto.getStatus())) ? "selected" : ""%>>
													Active</option>

												<option value="Inactive"
													<%=("Inactive".equals(dto.getStatus())) ? "selected" : ""%>>
													Inactive</option>

											</select>

										</div>
									</div>

									<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("status", request)%>
									</font> <br>
									<%
										if (dto.getCommandId() > 0) {
									%>

									<div class="text-center">

										<input type="submit" name="operation"
											class="btn btn-success btn-md" style="font-size: 17px"
											value="<%=VoiceCommandCtl.OP_UPDATE%>"> <input
											type="submit" name="operation" class="btn btn-warning btn-md"
											style="font-size: 17px"
											value="<%=VoiceCommandCtl.OP_CANCEL%>">

									</div>

									<%
										} else {
									%>

									<div class="text-center">

										<input type="submit" name="operation"
											class="btn btn-success btn-md" style="font-size: 17px"
											value="<%=VoiceCommandCtl.OP_SAVE%>"> <input
											type="submit" name="operation" class="btn btn-warning btn-md"
											style="font-size: 17px" value="<%=VoiceCommandCtl.OP_RESET%>">

									</div>

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

	</div>

</body>

<%@include file="FooterView.jsp"%>

</html>