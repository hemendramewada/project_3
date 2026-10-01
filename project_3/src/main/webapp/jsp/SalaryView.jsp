<%@page import="in.co.rays.project_3.controller.SalaryCtl"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
	"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<meta http-equiv="Content-Type"
	  content="text/html; charset=ISO-8859-1">

<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Salary View</title>

<style type="text/css">

.log1 {
	padding-top: 3%;
}

i.css {
	border: 2px solid #8080803b;
	padding-left: 10px;
	padding-bottom: 11px;
	background-color: #ebebe0;
}

.input-group-addon {
	box-shadow: 9px 8px 7px #001a33;
}

.p4 {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/user1.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	padding-top: 85px;
}

</style>

</head>

<body class="p4">

	<div class="header">
		<%@include file="Header.jsp"%>
	</div>

	<div>

		<jsp:useBean id="dto"
			class="in.co.rays.project_3.dto.SalaryDTO"
			scope="request">
		</jsp:useBean>

		<main>

			<form action="<%=ORSView.SALARY_CTL%>" method="post">

				<div class="row pt-3">

					<div class="col-md-4"></div>

					<div class="col-md-4">

						<div class="card input-group-addon">

							<div class="card-body">

								<%
									if (dto.getId() != null && dto.getId() > 0) {
								%>

								<h3 class="text-center text-primary font-weight-bold">
									Update Salary
								</h3>

								<%
									} else {
								%>

								<h3 class="text-center text-primary font-weight-bold">
									Add Salary
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

										<button type="button" class="close"
											data-dismiss="alert">&times;</button>

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

										<button type="button" class="close"
											data-dismiss="alert">&times;</button>

										<%=ServletUtility.getErrorMessage(request)%>

									</div>

									<%
										}
									%>

								</H4>


								<!-- Salary Code -->

								<div class="md-form input-group-addon">

									<span class="pl-sm-5">
										<b>Salary Code</b>
										<span style="color: red;">*</span>
									</span>

									<br>

									<div class="col-sm-12">

										<div class="input-group">

											<div class="input-group-prepend">

												<div class="input-group-text">

													<i class="fa fa-barcode grey-text"
														style="font-size: 1rem;"></i>

												</div>

											</div>

											<input type="text"
												name="salaryCode"
												class="form-control"
												style="border: 2px solid #8080803b;"
												placeholder="Enter Salary Code"
												value="<%=DataUtility.getStringData(dto.getSalaryCode())%>">

										</div>

									</div>

									<font color="red" class="pl-sm-5">
										<%=ServletUtility.getErrorMessage("salaryCode", request)%>
									</font>

									<br>


									<!-- Employee Name -->

									<span class="pl-sm-5">
										<b>Employee Name</b>
										<span style="color: red;">*</span>
									</span>

									<br>

									<div class="col-sm-12">

										<div class="input-group">

											<div class="input-group-prepend">

												<div class="input-group-text">

													<i class="fa fa-user grey-text"></i>

												</div>

											</div>

											<input type="text"
												name="employeeName"
												class="form-control"
												style="border: 2px solid #8080803b;"
												placeholder="Enter Employee Name"
												value="<%=DataUtility.getStringData(dto.getEmployeeName())%>">

										</div>

									</div>

									<font color="red" class="pl-sm-5">
										<%=ServletUtility.getErrorMessage("employeeName", request)%>
									</font>

									<br>


									<!-- Salary Amount -->

									<span class="pl-sm-5">
										<b>Salary Amount</b>
										<span style="color: red;">*</span>
									</span>

									<br>

									<div class="col-sm-12">

										<div class="input-group">

											<div class="input-group-prepend">

												<div class="input-group-text">

													<i class="fa fa-money grey-text"></i>

												</div>

											</div>

											<input type="text"
												name="salaryAmount"
												class="form-control"
												style="border: 2px solid #8080803b;"
												placeholder="Enter Salary Amount"
												value="<%=dto.getSalaryAmount() == null ? "" : dto.getSalaryAmount()%>">

										</div>

									</div>

									<font color="red" class="pl-sm-5">
										<%=ServletUtility.getErrorMessage("salaryAmount", request)%>
									</font>

									<br>


									<!-- Salary Status -->

									<span class="pl-sm-5">
										<b>Salary Status</b>
										<span style="color: red;">*</span>
									</span>

									<br>

									<div class="col-sm-12">

										<div class="input-group">

											<div class="input-group-prepend">

												<div class="input-group-text">

													<i class="fa fa-toggle-on grey-text"></i>

												</div>

											</div>

											<input type="text"
												name="salaryStatus"
												class="form-control"
												style="border: 2px solid #8080803b;"
												placeholder="Enter Salary Status"
												value="<%=DataUtility.getStringData(dto.getSalaryStatus())%>">

										</div>

									</div>

									<font color="red" class="pl-sm-5">
										<%=ServletUtility.getErrorMessage("salaryStatus", request)%>
									</font>

									<br>

								</div>

								<br>
								<br>


								<!-- Buttons -->

								<%
									if (dto.getId() != null && dto.getId() > 0) {
								%>

								<div class="text-center">

									<input type="submit"
										name="operation"
										class="btn btn-success btn-md"
										style="font-size: 17px"
										value="<%=SalaryCtl.OP_UPDATE%>">

									<input type="submit"
										name="operation"
										class="btn btn-warning btn-md"
										style="font-size: 17px"
										value="<%=SalaryCtl.OP_CANCEL%>">

								</div>

								<%
									} else {
								%>

								<div class="text-center">

									<input type="submit"
										name="operation"
										class="btn btn-success btn-md"
										style="font-size: 17px"
										value="<%=SalaryCtl.OP_SAVE%>">

									<input type="submit"
										name="operation"
										class="btn btn-warning btn-md"
										style="font-size: 17px"
										value="<%=SalaryCtl.OP_RESET%>">

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
