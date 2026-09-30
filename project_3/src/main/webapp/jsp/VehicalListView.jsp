```jsp
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.dto.VehicleDTO"%>
<%@page import="in.co.rays.project_3.controller.VehicleListCtl"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>

<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Vehicle List</title>

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>

<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>

<style>
.hm {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/list2.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	padding-top: 85px;
}

.text {
	text-align: center;
}
</style>

</head>

<%@include file="Header.jsp"%>

<body class="hm">

	<div>

		<form class="pb-5" action="<%=ORSView.VEHICLE_LIST_CTL%>"
			method="post">

			<jsp:useBean id="dto" class="in.co.rays.project_3.dto.VehicleDTO"
				scope="request">
			</jsp:useBean>

			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);

				int index = ((pageNo - 1) * pageSize) + 1;

				int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

				List list = ServletUtility.getList(request);

				Iterator<VehicleDTO> it = list.iterator();
			%>

			<center>
				<h1 class="text-dark">
					<b><u>Vehicle List</u></b>
				</h1>
			</center>

			<!-- Success Message -->

			<div class="row">

				<div class="col-md-4"></div>

				<%
					if (!ServletUtility.getSuccessMessage(request).equals("")) {
				%>

				<div class="col-md-4 alert alert-success alert-dismissible"
					style="background-color: #80ff80">

					<button type="button" class="close" data-dismiss="alert">
						&times;</button>

					<h4>
						<font color="#008000"> <%=ServletUtility.getSuccessMessage(request)%>
						</font>
					</h4>

				</div>

				<%
					}
				%>

				<div class="col-md-4"></div>

			</div>


			<!-- Error Message -->

			<div class="row">

				<div class="col-md-4"></div>

				<%
					if (!ServletUtility.getErrorMessage(request).equals("")) {
				%>

				<div class="col-md-4 alert alert-danger alert-dismissible">

					<button type="button" class="close" data-dismiss="alert">
						&times;</button>

					<h4>
						<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
						</font>
					</h4>

				</div>

				<%
					}
				%>

				<div class="col-md-4"></div>

			</div>


			<!-- Search -->

			<div class="row">

				<div class="col-sm-2"></div>

				<div class="col-sm-2">

					<input type="text" name="vehicleName"
						placeholder="Enter Vehicle Name" class="form-control"
						value="<%=ServletUtility.getParameter("vehicleName", request)%>">

				</div>

				&emsp;

				<div class="col-sm-2">

					<input type="text" name="model" placeholder="Enter Model"
						class="form-control"
						value="<%=ServletUtility.getParameter("model", request)%>">

				</div>

				&emsp;

				<div class="col-sm-2">

					<input type="text" name="color" placeholder="Enter Color"
						class="form-control"
						value="<%=ServletUtility.getParameter("color", request)%>">

				</div>

				&emsp;

				<div class="col-sm-2">

					<input type="text" name="price" placeholder="Enter Price"
						class="form-control"
						value="<%=ServletUtility.getParameter("price", request)%>">

				</div>

				<div class="col-sm-2">

					<input type="submit" class="btn btn-primary btn-md"
						style="font-size: 15px" name="operation"
						value="<%=VehicleListCtl.OP_SEARCH%>"> &emsp; <input
						type="submit" class="btn btn-dark btn-md" style="font-size: 15px"
						name="operation" value="<%=VehicleListCtl.OP_RESET%>">

				</div>

			</div>

			<br>


			<!-- Vehicle Table -->

			<div style="margin-bottom: 20px;" class="table-responsive">

				<table class="table table-bordered table-dark table-hover">

					<thead>

						<tr style="background-color: red;">

							<th width="10%"><input type="checkbox" id="select_all"
								name="Select" class="text"> Select All</th>

							<th width="5%" class="text">S.NO</th>

							<th width="20%" class="text">Vehicle Name</th>

							<th width="20%" class="text">Model</th>

							<th width="15%" class="text">Color</th>

							<th width="15%" class="text">Price</th>

							<th width="10%" class="text">Edit</th>

						</tr>

					</thead>

					<%
						while (it.hasNext()) {

							dto = it.next();
					%>

					<tbody>

						<tr>

							<td align="center"><input type="checkbox" class="checkbox"
								name="ids" value="<%=dto.getVechicleId()%>"></td>

							<td class="text"><%=index++%></td>

							<td class="text"><%=dto.getVehicleName()%></td>

							<td class="text"><%=dto.getModel()%></td>

							<td class="text"><%=dto.getColor()%></td>

							<td class="text"><%=dto.getPrice()%></td>

							<td class="text">
								<!-- IMPORTANT: Corrected Edit URL --> <a
								href="<%=ORSView.VEHICLE_CTL%>?id=<%=dto.getVechicleId()%>">
									Edit </a>

							</td>

						</tr>

					</tbody>

					<%
						}
					%>

				</table>

			</div>


			<!-- Buttons -->

			<table width="100%">

				<tr>

					<td><input type="submit" name="operation"
						class="btn btn-warning btn-md" style="font-size: 17px"
						value="<%=VehicleListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>


					<td><input type="submit" name="operation"
						class="btn btn-primary btn-md" style="font-size: 17px"
						value="<%=VehicleListCtl.OP_NEW%>"></td>


					<td><input type="submit" name="operation"
						class="btn btn-danger btn-md" style="font-size: 17px"
						value="<%=VehicleListCtl.OP_DELETE%>"></td>


					<td align="right"><input type="submit" name="operation"
						class="btn btn-warning btn-md"
						style="font-size: 17px; padding: 5px;"
						value="<%=VehicleListCtl.OP_NEXT%>"
						<%=(nextPageSize > 0) ? "" : "disabled"%>></td>

				</tr>

			</table>


			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

		</form>

	</div>

</body>

<%@include file="FooterView.jsp"%>

</html>
```
