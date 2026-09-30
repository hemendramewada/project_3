<%@page import="in.co.rays.project_3.controller.PortfolioCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<title>Portfolio View</title>

<style>
.p4 {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/user1.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	padding-top: 75px;
}
</style>

<!-- Date Picker -->


</head>

<body class="p4">

	<div class="header">
		<%@include file="Header.jsp"%>
		<%@include file="calendar.jsp"%>
	</div>

	<main>
	<form action="<%=ORSView.PORTFOLIO_CTL%>" method="post">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.PortfolioDTO"
			scope="request" />

		<div class="row pt-3 pb-4">
			<div class="col-md-4"></div>

			<div class="col-md-4">
				<div class="card">
					<div class="card-body">

						<%
							if (dto.getId() != null && dto.getId() > 0) {
						%>
						<h3 class="text-center text-primary">Update Portfolio</h3>
						<%
							} else {
						%>
						<h3 class="text-center text-primary">Add Portfolio</h3>
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

						<!-- Portfolio Name -->
						<b>Portfolio Name *</b> 
						<input type="text" name="portfolioName"
							class="form-control" placeholder="Enter Portfolio Name"
							value="<%=DataUtility.getStringData(dto.getPortfolioName())%>">

						<font color="red">
							<%=ServletUtility.getErrorMessage("portfolioName", request)%>
						</font>
						<br>

						<!-- Total Value -->
						<b>Total Value *</b> 
						<input type="text" name="totalValue"
							class="form-control" placeholder="Enter Total Value"
							value="<%=DataUtility.getIntData(dto.getTotalValue())%>">

						<font color="red">
							<%=ServletUtility.getErrorMessage("totalValue", request)%>
						</font>
						<br>

						<!-- Created Date -->
						<b>Created Date *</b> 
						<input type="text" name="createdDate"
							id="datepicker" class="form-control"
							placeholder="Enter Created Date"
							value="<%=DataUtility.getDateString(dto.getCreatedDate())%>">

						<font color="red">
							<%=ServletUtility.getErrorMessage("createdDate", request)%>
						</font>
						<br>

						<%
							if (dto.getId() != null && dto.getId() > 0) {
						%>

						<div class="text-center">
							<input type="submit" name="operation" class="btn btn-success"
								value="<%=PortfolioCtl.OP_UPDATE%>">
							<input type="submit" name="operation" class="btn btn-warning"
								value="<%=PortfolioCtl.OP_CANCEL%>">
						</div>

						<%
							} else {
						%>

						<div class="text-center">
							<input type="submit" name="operation" class="btn btn-success"
								value="<%=PortfolioCtl.OP_SAVE%>">
							<input type="submit" name="operation" class="btn btn-warning"
								value="<%=PortfolioCtl.OP_RESET%>">
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