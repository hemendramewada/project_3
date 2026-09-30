<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.VehicleCtl"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
	"http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Vehicle View</title>

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

		<form action="<%=ORSView.VEHICLE_CTL%>" method="post">

			<jsp:useBean id="dto" class="in.co.rays.project_3.dto.VehicleDTO"
				scope="request">
			</jsp:useBean>

			<div class="row pt-3">

				<div class="col-md-4 mb-4"></div>

				<div class="col-md-4 mb-4">

					<div class="card input-group-addon">

						<div class="card-body">

							<%
								long id = DataUtility.getLong(request.getParameter("id"));

								if (dto.getVehicleName() != null && dto.getVechicleId() != null && dto.getVechicleId() > 0) {
							%>

							<h3 class="text-center default-text text-primary">Update
								Vehicle</h3>

							<%
								} else {
							%>

							<h3 class="text-center default-text text-primary">Add
								Vehicle</h3>

							<%
								}
							%>

							<!-- Success Message -->

							<H4 align="center">

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

							</H4>


							<!-- Error Message -->

							<H4 align="center">

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

							</H4>


							<!-- Hidden Vehicle ID -->

							<input type="hidden" name="vechicleId"
								value="<%=dto.getVechicleId()%>">


							<!-- Vehicle Name -->

							<div class="md-form">

								<span class="pl-sm-5"> <b>Vehicle Name</b> <span
									style="color: red;">*</span>
								</span> </br>

								<div class="col-sm-12">

									<div class="input-group">

										<div class="input-group-prepend">

											<div class="input-group-text">

												<i class="fa fa-car grey-text" style="font-size: 1rem;"></i>

											</div>

										</div>

										<input type="text" class="form-control" name="vehicleName"
											placeholder="Vehicle Name"
											value="<%=DataUtility.getStringData(dto.getVehicleName())%>">

									</div>

								</div>

								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("vehicleName", request)%>
								</font> </br>


								<!-- Model -->

								<span class="pl-sm-5"> <b>Model</b> <span
									style="color: red;">*</span>
								</span> </br>

								<div class="col-sm-12">

									<div class="input-group">

										<div class="input-group-prepend">

											<div class="input-group-text">

												<i class="fa fa-cogs grey-text" style="font-size: 1rem;"></i>

											</div>

										</div>

										<input type="text" class="form-control" name="model"
											placeholder="Model"
											value="<%=DataUtility.getStringData(dto.getModel())%>">

									</div>

								</div>

								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("model", request)%>
								</font> </br>


								<!-- Color -->

								<span class="pl-sm-5"> <b>Color</b> <span
									style="color: red;">*</span>
								</span> </br>

								<div class="col-sm-12">

									<div class="input-group">

										<div class="input-group-prepend">

											<div class="input-group-text">

												<i class="fa fa-paint-brush grey-text"
													style="font-size: 1rem;"></i>

											</div>

										</div>

										<input type="text" class="form-control" name="color"
											placeholder="Color"
											value="<%=DataUtility.getStringData(dto.getColor())%>">

									</div>

								</div>

								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("color", request)%>
								</font> </br>


								<!-- Price -->

								<span class="pl-sm-5"> <b>Price</b> <span
									style="color: red;">*</span>
								</span> </br>

								<div class="col-sm-12">

									<div class="input-group">

										<div class="input-group-prepend">

											<div class="input-group-text">

												<i class="fa fa-money grey-text" style="font-size: 1rem;"></i>

											</div>

										</div>

										<input type="text" class="form-control" name="price"
											placeholder="Price"
											value="<%=dto.getPrice() != null ? dto.getPrice() : ""%>">

									</div>

								</div>

								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("price", request)%>
								</font> </br>


								<!-- Buttons -->

								<%
									if (dto.getVehicleName() != null && dto.getVechicleId() != null && dto.getVechicleId() > 0) {
								%>

								<div class="text-center">

									<input type="submit" name="operation"
										class="btn btn-success btn-md" style="font-size: 17px"
										value="<%=VehicleCtl.OP_UPDATE%>"> <input
										type="submit" name="operation" class="btn btn-warning btn-md"
										style="font-size: 17px" value="<%=VehicleCtl.OP_CANCEL%>">

								</div>

								<%
									} else {
								%>

								<div class="text-center">

									<input type="submit" name="operation"
										class="btn btn-success btn-md" style="font-size: 17px"
										value="<%=VehicleCtl.OP_SAVE%>"> <input type="submit"
										name="operation" class="btn btn-warning btn-md"
										style="font-size: 17px" value="<%=VehicleCtl.OP_RESET%>">

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

		<div class="col-md-4 mb-4"></div>

	</div>

</body>

<%@include file="FooterView.jsp"%>

</html>