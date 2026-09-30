<%@page import="in.co.rays.project_3.controller.InventoryCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Inventory View</title>
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
		<%@include file="calendar.jsp"%>
	</div>

	<main>
		<form action="<%=ORSView.INVENTORY_CTL%>" method="post">

			<jsp:useBean id="dto" class="in.co.rays.project_3.dto.InventoryDTO"
				scope="request"></jsp:useBean>

			<div class="row pt-3 pb-4">
				<div class="col-md-4"></div>

				<div class="col-md-4">
					<div class="card">
						<div class="card-body">

							<%
								if (dto.getId() != null && dto.getId() > 0) {
							%>
							<h3 class="text-center text-primary">Update Inventory</h3>
							<%
								} else {
							%>
							<h3 class="text-center text-primary">Add Inventory</h3>
							<%
								}
							%>

							<!-- Success Message -->
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

							<!-- Error Message -->
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

							<!-- Hidden Fields -->
							<input type="hidden" name="id" value="<%=dto.getId()%>">
							<input type="hidden" name="createdBy"
								value="<%=dto.getCreatedBy()%>"> <input type="hidden"
								name="modifiedBy" value="<%=dto.getModifiedBy()%>"> <input
								type="hidden" name="createdDatetime"
								value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
							<input type="hidden" name="modifiedDatetime"
								value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

							<!-- Supplier Name -->
							<span><b>Supplier Name</b> <span style="color: red">*</span></span>
							<input type="text" name="supplierName" class="form-control"
								value="<%=DataUtility.getStringData(dto.getSupplierName())%>">
							<font color="red"> <%=ServletUtility.getErrorMessage("supplierName", request)%>
							</font> <br>

							<!-- Product -->
							<span><b>Product</b> <span style="color: red">*</span></span> <input
								type="text" name="product" class="form-control"
								value="<%=DataUtility.getStringData(dto.getProduct())%>">
							<font color="red"> <%=ServletUtility.getErrorMessage("product", request)%>
							</font> <br>

							<!-- Quantity -->
							<span><b>Quantity</b> <span style="color: red">*</span></span> <input
								type="text" name="quantity" class="form-control"
								value="<%=DataUtility.getStringData(dto.getQuantity())%>">
							<font color="red"> <%=ServletUtility.getErrorMessage("quantity", request)%>
							</font> <br>

							<!-- Purchase Date -->
							<span><b>Purchase Date</b> <span style="color: red">*</span></span>
							<input type="text" id="datepicker4" name="dob"
								class="form-control" readonly="readonly"
								value="<%=DataUtility.getDateString(dto.getDob())%>"> <font
								color="red"> <%=ServletUtility.getErrorMessage("dob", request)%>
							</font> <br> <br>

							<%
								if (dto.getId() != null && dto.getId() > 0) {
							%>
							<div class="text-center">
								<input type="submit" name="operation" class="btn btn-success"
									value="<%=InventoryCtl.OP_UPDATE%>"> <input
									type="submit" name="operation" class="btn btn-warning"
									value="<%=InventoryCtl.OP_CANCEL%>">
							</div>
							<%
								} else {
							%>
							<div class="text-center">
								<input type="submit" name="operation" class="btn btn-success"
									value="<%=InventoryCtl.OP_SAVE%>"> <input type="submit"
									name="operation" class="btn btn-warning"
									value="<%=InventoryCtl.OP_RESET%>">
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
