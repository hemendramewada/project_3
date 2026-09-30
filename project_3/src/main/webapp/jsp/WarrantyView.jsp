<%@page import="in.co.rays.project_3.controller.WarrantyCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<title>Warranty View</title>

<style>
.p4 {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/user1.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	padding-top: 75px;
}
</style>

<script type="text/javascript">
	$(function() {
		$("#datepicker1").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			yearRange : '1950:2030',
		});
	});

	$(function() {
		$("#datepicker2").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			yearRange : '1950:2030',
		});
	});
</script>

</head>

<body class="p4">

	<div class="header">
		<%@include file="Header.jsp"%>
		<%@include file="calendar.jsp"%>
	</div>

	<main>
	<form action="<%=ORSView.WARRANTY_CTL%>" method="post">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.WarrantyDTO"
			scope="request" />

		<div class="row pt-3 pb-4">
			<div class="col-md-4"></div>

			<div class="col-md-4">
				<div class="card">
					<div class="card-body">

						<%
							if (dto.getId() != null && dto.getId() > 0) {
						%>
						<h3 class="text-center text-primary">Update Warranty</h3>
						<%
							} else {
						%>
						<h3 class="text-center text-primary">Add Warranty</h3>
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

						<!-- Product Name -->
						<b>Product Name *</b> <input type="text" name="productName"
							class="form-control" placeholder="Enter Product Name"
							value="<%=DataUtility.getStringData(dto.getProductName())%>">

						<font color="red"> <%=ServletUtility.getErrorMessage("productName", request)%>
						</font> <br>

						<!-- Start Date -->
						<b>Start Date *</b> <input type="text" name="startDate"
							id="datepicker1" class="form-control" placeholder="Enter Start date"
							value="<%=DataUtility.getDateString(dto.getStartDate())%>">

						<font color="red"> <%=ServletUtility.getErrorMessage("startDate", request)%>
						</font> <br>

						<!-- End Date -->
						<b>End Date *</b> <input type="text" name="endDate"
							id="datepicker2" class="form-control" placeholder="Enter end date"
							value="<%=DataUtility.getDateString(dto.getEndDate())%>">

						<font color="red"> <%=ServletUtility.getErrorMessage("endDate", request)%>
						</font> <br>

						<%
							if (dto.getId() != null && dto.getId() > 0) {
						%>

						<div class="text-center">
							<input type="submit" name="operation" class="btn btn-success"
								value="<%=WarrantyCtl.OP_UPDATE%>"> <input type="submit"
								name="operation" class="btn btn-warning"
								value="<%=WarrantyCtl.OP_CANCEL%>">
						</div>

						<%
							} else {
						%>

						<div class="text-center">
							<input type="submit" name="operation" class="btn btn-success"
								value="<%=WarrantyCtl.OP_SAVE%>"> <input type="submit"
								name="operation" class="btn btn-warning"
								value="<%=WarrantyCtl.OP_RESET%>">
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