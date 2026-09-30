<%@page import="in.co.rays.project_3.controller.LanguageCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Language View</title>
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
	</div>

	<main>
	<form action="<%=ORSView.LANGUAGE_CTL%>" method="post">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.LanguageDTO"
			scope="request"></jsp:useBean>

		<div class="row pt-3 pb-4">
			<div class="col-md-4"></div>

			<div class="col-md-4">
				<div class="card">
					<div class="card-body">

						<%
							if (dto.getId() != null && dto.getId() > 0) {
						%>
						<h3 class="text-center text-primary">Update Language</h3>
						<%
							} else {
						%>
						<h3 class="text-center text-primary">Add Language</h3>
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
						<input type="hidden" name="id" value="<%=dto.getId()%>"> <input
							type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
						<input type="hidden" name="modifiedBy"
							value="<%=dto.getModifiedBy()%>"> <input type="hidden"
							name="createdDatetime"
							value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
						<input type="hidden" name="modifiedDatetime"
							value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

						<!-- Language Code -->
						<span><b>Language Code</b> <span style="color: red">*</span></span>
						<input type="text" name="languageCode" class="form-control"
							value="<%=DataUtility.getStringData(dto.getLanguageCode())%>">
						<font color="red"> <%=ServletUtility.getErrorMessage("languageCode", request)%>
						</font> <br>

						<!-- Language Name -->
						<span><b>Language Name</b> <span style="color: red">*</span></span>
						<input type="text" name="languageName" class="form-control"
							value="<%=DataUtility.getStringData(dto.getLanguageName())%>">
						<font color="red"> <%=ServletUtility.getErrorMessage("languageName", request)%>
						</font> <br>

						<!-- Direction -->
						<span><b>Direction</b> <span style="color: red">*</span></span> <select
							name="direction" class="form-control">
							<option value="">--Select--</option>
							<option value="LTR"
								<%="LTR".equals(dto.getDirection()) ? "selected" : ""%>>Left
								To Right</option>
							<option value="RTL"
								<%="RTL".equals(dto.getDirection()) ? "selected" : ""%>>Right
								To Left</option>
						</select> <font color="red"> <%=ServletUtility.getErrorMessage("direction", request)%>
						</font> <br>

						<!-- Language Status -->
						<span><b>Status</b> <span style="color: red">*</span></span> <select
							name="languageStatus" class="form-control">
							<option value="">--Select--</option>
							<option value="Active"
								<%="Active".equals(dto.getLanguageStatus()) ? "selected" : ""%>>Active</option>
							<option value="Inactive"
								<%="Inactive".equals(dto.getLanguageStatus()) ? "selected" : ""%>>Inactive</option>
						</select> <font color="red"> <%=ServletUtility.getErrorMessage("languageStatus", request)%>
						</font> <br> <br>

						<%
							if (dto.getId() != null && dto.getId() > 0) {
						%>
						<div class="text-center">
							<input type="submit" name="operation" class="btn btn-success"
								value="<%=LanguageCtl.OP_UPDATE%>"> <input type="submit"
								name="operation" class="btn btn-warning"
								value="<%=LanguageCtl.OP_CANCEL%>">
						</div>
						<%
							} else {
						%>
						<div class="text-center">
							<input type="submit" name="operation" class="btn btn-success"
								value="<%=LanguageCtl.OP_SAVE%>"> <input type="submit"
								name="operation" class="btn btn-warning"
								value="<%=LanguageCtl.OP_RESET%>">
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