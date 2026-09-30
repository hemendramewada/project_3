<%@page import="in.co.rays.project_3.controller.DonationCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<title>Donation View</title>

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
        dateFormat: 'dd/mm/yy',
        changeMonth: true,
        changeYear: true,
        yearRange: '1950:2030'
    });
});
</script>

</head>

<body class="p4">

	<div class="header">
		<%@include file="Header.jsp"%>
		<%@include file="calendar.jsp" %>
	</div>

	<main>
	<form action="<%=ORSView.DONATION_CTL%>" method="post">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.DonationDTO"
			scope="request" />

		<div class="row pt-3 pb-4">
			<div class="col-md-4"></div>

			<div class="col-md-4">
				<div class="card">
					<div class="card-body">

						<%
							if (dto.getId() != null && dto.getId() > 0) {
						%>
						<h3 class="text-center text-primary">Update Donation</h3>
						<%
							} else {
						%>
						<h3 class="text-center text-primary">Add Donation</h3>
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

						<!-- Donor Name -->
						<b>Donor Name *</b>
						<input type="text" name="donorName"
							class="form-control" placeholder="Enter Donor Name"
							value="<%=DataUtility.getStringData(dto.getDonorName())%>">
						<font color="red">
							<%=ServletUtility.getErrorMessage("donorName", request)%>
						</font>
						<br>

						<!-- Amount -->
						<b>Amount *</b>
						<input type="text" name="amount"
							class="form-control" placeholder="Enter Donation Amount"
							value="<%=DataUtility.getStringData(dto.getAmount())%>">
						<font color="red">
							<%=ServletUtility.getErrorMessage("amount", request)%>
						</font>
						<br>

						<!-- Donation Date -->
						<b>Donation Date *</b>
						<input type="text" name="donationDate" id="datepicker"
							class="form-control" placeholder="Enter Donation Date"
							value="<%=DataUtility.getDateString(dto.getDonationDate())%>">
						<font color="red">
							<%=ServletUtility.getErrorMessage("donationDate", request)%>
						</font>
						<br>

						<%
							if (dto.getId() != null && dto.getId() > 0) {
						%>
						<div class="text-center">
							<input type="submit" name="operation" class="btn btn-success"
								value="<%=DonationCtl.OP_UPDATE%>">
							<input type="submit" name="operation" class="btn btn-warning"
								value="<%=DonationCtl.OP_CANCEL%>">
						</div>
						<%
							} else {
						%>
						<div class="text-center">
							<input type="submit" name="operation" class="btn btn-success"
								value="<%=DonationCtl.OP_SAVE%>">
							<input type="submit" name="operation" class="btn btn-warning"
								value="<%=DonationCtl.OP_RESET%>">
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