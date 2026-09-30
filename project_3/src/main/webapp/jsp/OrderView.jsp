```jsp
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@page import="in.co.rays.project_3.controller.OrderCtl"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
	"http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Order View</title>

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
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/order1.jpg');
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

		<form action="<%=ORSView.ORDER_CTL%>" method="post">

			<jsp:useBean id="dto" class="in.co.rays.project_3.dto.OrderDTO"
				scope="request"></jsp:useBean>

			<div class="row pt-3">

				<div class="col-md-4 mb-4"></div>

				<div class="col-md-4 mb-4">

					<div class="card input-group-addon">

						<div class="card-body">

							<%
								long id = DataUtility.getLong(request.getParameter("orderId"));
							%>

							<%
								if (dto.getOrderId() != null && dto.getOrderId() > 0) {
							%>

							<h3 class="text-center default-text text-primary">Update
								Order</h3>

							<%
								} else {
							%>

							<h3 class="text-center default-text text-primary">Add Order
							</h3>

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


							<!-- Hidden Order Id -->

							<input type="hidden" name="orderId" value="<%=dto.getOrderId()%>">


							<!-- Order Date -->

							<div class="md-form">

								<span class="pl-sm-5"> <b>Order Date</b> <span
									style="color: red;">*</span>
								</span> </br>

								<div class="col-sm-12">

									<div class="input-group">

										<div class="input-group-prepend">

											<div class="input-group-text">

												<i class="fa fa-calendar grey-text" style="font-size: 1rem;">
												</i>

											</div>

										</div>

										<input type="text" id="datepicker2" name="orderDate"
											class="form-control" placeholder="Order Date"
											readonly="readonly"
											value="<%=DataUtility.getDateString(dto.getOrderDate())%>">

									</div>

								</div>

								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("orderDate", request)%>

								</font> </br>

							</div>


							<!-- Status -->

							<div class="md-form">

								<span class="pl-sm-5"> <b>Status</b> <span
									style="color: red;">*</span>

								</span> </br>

								<div class="col-sm-12">

									<div class="input-group">

										<div class="input-group-prepend">

											<div class="input-group-text">

												<i class="fa fa-info-circle grey-text"
													style="font-size: 1rem;"> </i>

											</div>

										</div>

										<input type="text" class="form-control" name="status"
											placeholder="Status"
											value="<%=DataUtility.getStringData(dto.getStatus())%>">

									</div>

								</div>

								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("status", request)%>

								</font> </br>

							</div>


							<!-- Customer Id -->

							<div class="md-form">

								<span class="pl-sm-5"> <b>Customer Id</b> <span
									style="color: red;">*</span>

								</span> </br>

								<div class="col-sm-12">

									<div class="input-group">

										<div class="input-group-prepend">

											<div class="input-group-text">

												<i class="fa fa-user grey-text" style="font-size: 1rem;">
												</i>

											</div>

										</div>

										<input type="text" class="form-control" name="customerId"
											placeholder="Customer Id" value="<%=dto.getCustomerId()%>">

									</div>

								</div>

								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("customerId", request)%>

								</font> </br>

							</div>


							<!-- Buttons -->

							<%
								if (dto.getOrderId() != null && dto.getOrderId() > 0) {
							%>

							<div class="text-center">

								<input type="submit" name="operation"
									class="btn btn-success btn-md" style="font-size: 17px"
									value="<%=OrderCtl.OP_UPDATE%>"> <input type="submit"
									name="operation" class="btn btn-warning btn-md"
									style="font-size: 17px" value="<%=OrderCtl.OP_CANCEL%>">

							</div>

							<%
								} else {
							%>

							<div class="text-center">

								<input type="submit" name="operation"
									class="btn btn-success btn-md" style="font-size: 17px"
									value="<%=OrderCtl.OP_SAVE%>"> <input type="submit"
									name="operation" class="btn btn-warning btn-md"
									style="font-size: 17px" value="<%=OrderCtl.OP_RESET%>">

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

</body>

<%@include file="FooterView.jsp"%>

</html>
```
