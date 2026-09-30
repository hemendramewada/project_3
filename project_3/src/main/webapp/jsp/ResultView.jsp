<%@page import="in.co.rays.project_3.controller.ResultCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<title>Result View</title>

<style>
.p4 {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/result.jpg');
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
	</div>

	<main>
		<form action="<%=ORSView.RESULT_CTL%>" method="post">

			<jsp:useBean id="dto" class="in.co.rays.project_3.dto.ResultDTO"
				scope="request" />

			<div class="row pt-3 pb-4">
				<div class="col-md-4"></div>

				<div class="col-md-4">
					<div class="card">
						<div class="card-body">

							<%
								if (dto.getId() != null && dto.getId() > 0) {
							%>
							<h3 class="text-center text-primary">Update Result</h3>
							<%
								} else {
							%>
							<h3 class="text-center text-primary">Add Result</h3>
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

							<!-- Hidden ID -->
							<input type="hidden" name="id" value="<%=dto.getId()%>">

							<!-- Result Code -->
							<b>Result Code *</b> <input type="text" name="resultCode"
								placeholder="enter ResultCode" class="form-control"
								value="<%=DataUtility.getStringData(dto.getResultCode())%>">
							<font color="red"> <%=ServletUtility.getErrorMessage("resultCode", request)%>
							</font> <br>

							<!-- Student Name -->
							<b>Student Name *</b> <input type="text" name="studentName"
								placeholder="enter Student Name" class="form-control"
								value="<%=DataUtility.getStringData(dto.getStudentName())%>">
							<font color="red"> <%=ServletUtility.getErrorMessage("studentName", request)%>
							</font> <br>

							<!-- Marks -->
							<b>Marks *</b> <input type="text" name="marks"
								placeholder="enter Marks" class="form-control"
								value="<%=dto.getMarks()%>"> <font color="red"> <%=ServletUtility.getErrorMessage("marks", request)%>
							</font> <br>

							<!-- Grade -->
							<b>Grade *</b> <select name="grade" class="form-control"
								placeholder="enter Grade">
								<option value="">--Select--</option>

								<option value="A"
									<%="A".equals(dto.getGrade()) ? "selected" : ""%>>A</option>

								<option value="B"
									<%="B".equals(dto.getGrade()) ? "selected" : ""%>>B</option>

								<option value="C"
									<%="C".equals(dto.getGrade()) ? "selected" : ""%>>C</option>

								<option value="D"
									<%="D".equals(dto.getGrade()) ? "selected" : ""%>>D</option>

								<option value="F"
									<%="F".equals(dto.getGrade()) ? "selected" : ""%>>F</option>
							</select> <font color="red"> <%=ServletUtility.getErrorMessage("grade", request)%>
							</font> <br> <br>

							<%
								if (dto.getId() != null && dto.getId() > 0) {
							%>
							<div class="text-center">
								<input type="submit" name="operation" class="btn btn-success"
									value="<%=ResultCtl.OP_UPDATE%>"> <input type="submit"
									name="operation" class="btn btn-warning"
									value="<%=ResultCtl.OP_CANCEL%>">
							</div>
							<%
								} else {
							%>
							<div class="text-center">
								<input type="submit" name="operation" class="btn btn-success"
									value="<%=ResultCtl.OP_SAVE%>"> <input type="submit"
									name="operation" class="btn btn-warning"
									value="<%=ResultCtl.OP_RESET%>">
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