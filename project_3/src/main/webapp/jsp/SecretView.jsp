<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.controller.SecretCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>

<html>
<head>
<title>Secret View</title>

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

	<form action="<%=ORSView.SECRET_CTL%>" method="post">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.SecretDTO"
			scope="request" />

		<div class="row pt-3 pb-4">

			<div class="col-md-4"></div>

			<div class="col-md-4">
				<div class="card shadow-lg">
					<div class="card-body">

						<%
							if (dto.getId() != null && dto.getId() > 0) {
						%>
						<h3 class="text-center text-primary">Update Secret</h3>
						<%
							} else {
						%>
						<h3 class="text-center text-primary">Add Secret</h3>
						<%
							}
						%>

						<!-- ✅ SUCCESS MESSAGE -->
						<%
							if (!ServletUtility.getSuccessMessage(request).equals("")) {
						%>
						<div class="alert alert-success">
							<%=ServletUtility.getSuccessMessage(request)%>
						</div>
						<%
							}
						%>

						<!-- ❌ ERROR MESSAGE -->
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

						<!-- Secret Code -->
						<b>Secret Code *</b> <input type="text" name="secretCode"
							class="form-control" placeholder="Enter Secret Code"
							value="<%=DataUtility.getStringData(dto.getSecretCode())%>">

						<font color="red"> <%=ServletUtility.getErrorMessage("secretCode", request)%>
						</font> <br>

						<!-- Key Name -->
						<b>Key Name *</b> <input type="text" name="keyName"
							class="form-control" placeholder="Enter Key Name"
							value="<%=DataUtility.getStringData(dto.getKeyName())%>">

						<font color="red"> <%=ServletUtility.getErrorMessage("keyName", request)%>
						</font> <br>

						<!-- Secret Value -->
						<b>Secret Value *</b> <input type="text" name="secretValue"
							class="form-control" placeholder="Enter Secret Value"
							value="<%=DataUtility.getStringData(dto.getSecretValue())%>">

						<font color="red"> <%=ServletUtility.getErrorMessage("secretValue", request)%>
						</font> <br>

						<!-- Status -->
						<b>Status *</b>

						<%
							HashMap map = (HashMap) request.getAttribute("statusList");
						%>

						<%=HTMLUtility.getList("status", dto.getStatus(), map)%>

						<font color="red"> <%=ServletUtility.getErrorMessage("status", request)%>
						</font> <br>

						<div class="text-center">

							<%
								if (dto.getId() != null && dto.getId() > 0) {
							%>

							<input type="submit" name="operation" class="btn btn-success"
								value="<%=SecretCtl.OP_UPDATE%>"> <input type="submit"
								name="operation" class="btn btn-warning"
								value="<%=SecretCtl.OP_CANCEL%>">

							<%
								} else {
							%>

							<input type="submit" name="operation" class="btn btn-success"
								value="<%=SecretCtl.OP_SAVE%>"> <input type="submit"
								name="operation" class="btn btn-warning"
								value="<%=SecretCtl.OP_RESET%>">

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