<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.controller.CustomerCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page import="java.util.List"%>

<html>
<head>
<title>Customer View</title>

<style>
.p4 {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/user1.jpg');
	background-size: cover;
	padding-top: 75px;
}
</style>

</head>

<body class="p4">

	<%@include file="Header.jsp"%>

	<form action="<%=ORSView.CUSTOMER_CTL%>" method="post">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.CustomerDTO"
			scope="request" />

		<div class="row pt-3 pb-4">

			<div class="col-md-4"></div>

			<div class="col-md-4">
				<div class="card shadow-lg">
					<div class="card-body">

						<%
							if (dto.getId() != null && dto.getId() > 0) {
						%>
						<h3 class="text-center text-primary">Update Customer</h3>
						<%
							} else {
						%>
						<h3 class="text-center text-primary">Add Customer</h3>
						<%
							}
						%>





						<%
							if (!ServletUtility.getSuccessMessage(request).equals("")) {
						%>
						<div class="alert alert-success">
							<%=ServletUtility.getSuccessMessage(request)%>
						</div>
						<%
							}
						%>


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

						<!-- Client Name -->
						<b>Client Name *</b> <input type="text" name="clientName"
							class="form-control" placeholder="Enter Client Name"
							value="<%=DataUtility.getStringData(dto.getClientName())%>">

						<font color="red"> <%=ServletUtility.getErrorMessage("clientName", request)%>
						</font> <br>

						<!-- Location -->
						<b>Location *</b> <input type="text" name="location"
							class="form-control" placeholder="Enter Location"
							value="<%=DataUtility.getStringData(dto.getLocation())%>">

						<font color="red"> <%=ServletUtility.getErrorMessage("location", request)%>
						</font> <br>

						<!-- Contact Number -->
						<b>Contact Number *</b> <input type="text" name="contactNumber"
							class="form-control" placeholder="Enter Contact Number"
							value="<%=DataUtility.getStringData(dto.getContactNumber())%>">

						<font color="red"> <%=ServletUtility.getErrorMessage("contactNumber", request)%>
						</font> <br>

						<!-- Importance -->
						<!-- Importance -->
						<b>Importance *</b>

						<%
							HashMap map = (HashMap) request.getAttribute("importanceList");
						%>

						<%=HTMLUtility.getList("importance", dto.getImportance(), map)%>

						<font color="red"> <%=ServletUtility.getErrorMessage("importance", request)%>
						</font> <br>

						<div class="text-center">

							<%
								if (dto.getId() != null && dto.getId() > 0) {
							%>

							<input type="submit" name="operation" class="btn btn-success"
								value="<%=CustomerCtl.OP_UPDATE%>"> <input type="submit"
								name="operation" class="btn btn-warning"
								value="<%=CustomerCtl.OP_CANCEL%>">

							<%
								} else {
							%>

							<input type="submit" name="operation" class="btn btn-success"
								value="<%=CustomerCtl.OP_SAVE%>"> <input type="submit"
								name="operation" class="btn btn-warning"
								value="<%=CustomerCtl.OP_RESET%>">

							<%
								}
							%>

						</div>

					</div>
				</div>
			</div>

			<div class="col-md-4"></div>

		</div>

	</form>

	<%@include file="FooterView.jsp"%>

</body>
</html>
