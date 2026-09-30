<%@page import="in.co.rays.project_3.controller.SmartParkingCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
	"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Smart Parking View</title>

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

		<form action="<%=ORSView.SMART_PARKING_CTL%>" method="post">

			<jsp:useBean id="dto"
				class="in.co.rays.project_3.dto.SmartParkingDTO" scope="request">
			</jsp:useBean>

			<div class="row pt-3">

				<!-- Grid column -->
				<div class="col-md-4 mb-4"></div>

				<div class="col-md-4 mb-4">

					<div class="card input-group-addon">

						<div class="card-body">

							<%
								long id = DataUtility.getLong(request.getParameter("slotId"));

								if (dto.getVehicalNumber() != null && dto.getSlotId() > 0) {
							%>

							<h3 class="text-center default-text text-primary">Update
								Smart Parking</h3>

							<%
								} else {
							%>

							<h3 class="text-center default-text text-primary">Add Smart
								Parking</h3>

							<%
								}
							%>


							<!-- Success Message -->
							<H4 align="center">

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

							</H4>


							<!-- Error Message -->
							<H4 align="center">

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

							</H4>


							<!-- Hidden Slot ID -->
							<input type="hidden" name="slotId" value="<%=dto.getSlotId()%>">


							<!-- Vehical Number -->

							<div class="md-form">

								<span class="pl-sm-5"> <b>Vehical Number</b> <span
									style="color: red;">*</span>
								</span> </br>

								<div class="col-sm-12">

									<div class="input-group">

										<div class="input-group-prepend">

											<div class="input-group-text">

												<i class="fa fa-car grey-text" style="font-size: 1rem;">
												</i>

											</div>

										</div>

										<input type="text" class="form-control" name="vehicalNumber"
											placeholder="Vehical Number"
											value="<%=DataUtility.getStringData(dto.getVehicalNumber())%>">

									</div>

								</div>

								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("vehicalNumber", request)%>
								</font> </br>


								<!-- Vehical Type -->

								<span class="pl-sm-5"> <b>Vehical Type</b> <span
									style="color: red;">*</span>
								</span> </br>

								<div class="col-sm-12">

									<div class="input-group">

										<div class="input-group-prepend">

											<div class="input-group-text">

												<i class="fa fa-car grey-text" style="font-size: 1rem;">
												</i>

											</div>

										</div>

										<input type="text" class="form-control" name="vehicalType"
											placeholder="Vehical Type"
											value="<%=DataUtility.getStringData(dto.getVehicalType())%>">

									</div>

								</div>

								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("vehicalType", request)%>
								</font> </br>


								<!-- Occupied -->

								<span class="pl-sm-5"> <b>Occupied</b> <span
									style="color: red;">*</span>
								</span> </br>

								<div class="col-sm-12">

									<div class="input-group">

										<div class="input-group-prepend">

											<div class="input-group-text">

												<i class="fa fa-parking grey-text" style="font-size: 1rem;">
												</i>

											</div>

										</div>

										<select class="form-control" name="occupied">

											<%
												if (dto.isOccupied()) {
											%>

											<option value="true" selected>Occupied</option>

											<option value="false">Available</option>

											<%
												} else {
											%>

											<option value="true">Occupied</option>

											<option value="false" selected>Available</option>

											<%
												}
											%>

										</select>

									</div>

								</div>

								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("occupied", request)%>
								</font> </br>


								<!-- Buttons -->

								<%
									if (dto.getVehicalNumber() != null && dto.getSlotId() > 0) {
								%>

								<div class="text-center">

									<!-- Update -->
									<input type="submit" name="operation"
										class="btn btn-success btn-md" style="font-size: 17px"
										value="<%=SmartParkingCtl.OP_UPDATE%>">

									<!-- Park Vehicle -->
									<input type="submit" name="operation"
										class="btn btn-primary btn-md" style="font-size: 17px"
										value="<%=SmartParkingCtl.OP_PARK%>">

									<!-- Release Slot -->
									<input type="submit" name="operation"
										class="btn btn-info btn-md" style="font-size: 17px"
										value="<%=SmartParkingCtl.OP_RELEASE%>">

									<!-- Cancel -->
									<input type="submit" name="operation"
										class="btn btn-warning btn-md" style="font-size: 17px"
										value="<%=SmartParkingCtl.OP_CANCEL%>">

								</div>

								<%
									} else {
								%>

								<div class="text-center">

									<!-- Save -->
									<input type="submit" name="operation"
										class="btn btn-success btn-md" style="font-size: 17px"
										value="<%=SmartParkingCtl.OP_SAVE%>">

									<!-- Reset -->
									<input type="submit" name="operation"
										class="btn btn-warning btn-md" style="font-size: 17px"
										value="<%=SmartParkingCtl.OP_RESET%>">

								</div>

								<%
									}
								%>

							</div>

						</div>

					</div>

				</div>

			</div>

		</form>

		</main>

	</div>

</body>

<%@include file="FooterView.jsp"%>

</html>