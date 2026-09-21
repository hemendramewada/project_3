<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.controller.SettingsCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Settings View</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">

<style>
	body {
		padding-top: 110px;
		background-color: #f4f6f9;
	}
</style>

</head>

<body>

	<%@include file="Header.jsp"%>

	<div class="container">

		<form action="<%=ORSView.SETTINGS_CTL%>" method="post">

			<jsp:useBean id="dto"
				class="in.co.rays.project_3.dto.SettingsDTO"
				scope="request"></jsp:useBean>

			<div class="row">
				<div class="col-md-4"></div>

				<div class="col-md-4">
					<div class="card shadow">
						<div class="card-body">

							<%
								if (dto.getId() != null && dto.getId() > 0) {
							%>
							<h4 class="text-center text-primary">Update Settings</h4>
							<%
								} else {
							%>
							<h4 class="text-center text-primary">Add Settings</h4>
							<%
								}
							%>

							<hr>

							<%
								if (!ServletUtility.getSuccessMessage(request).equals("")) {
							%>
							<div class="alert alert-success text-center">
								<%=ServletUtility.getSuccessMessage(request)%>
							</div>
							<%
								}
							%>

							<%
								if (!ServletUtility.getErrorMessage(request).equals("")) {
							%>
							<div class="alert alert-danger text-center">
								<%=ServletUtility.getErrorMessage(request)%>
							</div>
							<%
								}
							%>

							<input type="hidden" name="id" value="<%=dto.getId()%>">

							<div class="form-group">
								<label>Setting Name *</label>
								<input type="text" name="settingName"
									class="form-control"
									value="<%=DataUtility.getStringData(dto.getSettingName())%>">
								<span class="text-danger">
									<%=ServletUtility.getErrorMessage("settingName", request)%>
								</span>
							</div>

							<div class="form-group">
								<label>Setting Value *</label>
								<input type="text" name="settingValue"
									class="form-control"
									value="<%=DataUtility.getStringData(dto.getSettingValue())%>">
								<span class="text-danger">
									<%=ServletUtility.getErrorMessage("settingValue", request)%>
								</span>
							</div>

							<!-- 🔥 Setting Type Dropdown -->
							<div class="form-group">
								<label>Setting Type *</label>

								<%
									HashMap typeMap = new HashMap();
									typeMap.put("Male", "Male");
									typeMap.put("Female", "Female");
								%>

								<%=HTMLUtility.getList("settingType",
										DataUtility.getStringData(dto.getSettingType()),
										typeMap)%>

								<span class="text-danger">
									<%=ServletUtility.getErrorMessage("settingType", request)%>
								</span>
							</div>

							<!-- 🔥 Setting Status Dropdown -->
							<div class="form-group">
								<label>Setting Status *</label>

								<%
									HashMap statusMap = new HashMap();
									statusMap.put("Committed", "Committed");
									statusMap.put("One Sided", "One Sided");
								%>

								<%=HTMLUtility.getList("settingStatus",
										DataUtility.getStringData(dto.getSettingStatus()),
										statusMap)%>

								<span class="text-danger">
									<%=ServletUtility.getErrorMessage("settingStatus", request)%>
								</span>
							</div>

							<div class="text-center">
								<%
									if (dto.getId() != null && dto.getId() > 0) {
								%>
								<input type="submit" name="operation"
									class="btn btn-success"
									value="<%=SettingsCtl.OP_UPDATE%>">
								<%
									} else {
								%>
								<input type="submit" name="operation"
									class="btn btn-success"
									value="<%=SettingsCtl.OP_SAVE%>">
								<input type="submit" name="operation"
									class="btn btn-warning"
									value="<%=SettingsCtl.OP_RESET%>">
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

	</div>

	<%@include file="FooterView.jsp"%>

</body>
</html>
