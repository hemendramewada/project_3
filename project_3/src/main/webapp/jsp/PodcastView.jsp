<%@page import="in.co.rays.project_3.controller.PodcastCtl"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Podcast View</title>

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

		<form action="<%=ORSView.PODCAST_CTL%>" method="post">

			<jsp:useBean id="dto" class="in.co.rays.project_3.dto.PodcastDTO"
				scope="request">
			</jsp:useBean>

			<div class="row pt-3 pb-3">

				<div class="col-md-4 mb-4"></div>

				<div class="col-md-4 mb-4">

					<div class="card">

						<div class="card-body">

							<%
								if (dto.getId() != null && dto.getId() > 0) {
							%>

							<h3 class="text-center text-primary">Update Podcast</h3>

							<%
								} else {
							%>

							<h3 class="text-center text-primary">Add Podcast</h3>

							<%
								}
							%>

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

							<input type="hidden" name="id" value="<%=dto.getId()%>">

							<input type="hidden" name="createdBy"
								value="<%=dto.getCreatedBy()%>"> <input type="hidden"
								name="modifiedBy" value="<%=dto.getModifiedBy()%>"> <input
								type="hidden" name="createdDatetime"
								value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">

							<input type="hidden" name="modifiedDatetime"
								value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">
							<span><b>Title</b><span style="color: red;">*</span></span>

							<div class="input-group">
								<input type="text" class="form-control" name="title"
									placeholder="Enter Title"
									value="<%=DataUtility.getStringData(dto.getTitle())%>">
							</div>

							<font color="red"> <%=ServletUtility.getErrorMessage("title", request)%>
							</font> <br> <span><b>Host Name</b><span style="color: red;">*</span></span>

							<div class="input-group">
								<input type="text" class="form-control" name="hostName"
									placeholder="Enter Host Name"
									value="<%=DataUtility.getStringData(dto.getHostName())%>">
							</div>

							<font color="red"> <%=ServletUtility.getErrorMessage("hostName", request)%>
							</font> <br> <span><b>Duration</b><span style="color: red;">*</span></span>

							<div class="input-group">
								<input type="text" class="form-control" name="duration"
									placeholder="Enter Duration"
									value="<%=DataUtility.getStringData(dto.getDuration())%>">
							</div>

							<font color="red"> <%=ServletUtility.getErrorMessage("duration", request)%>
							</font> <br> <span><b>Category</b><span style="color: red;">*</span></span>

							<div class="input-group">
								<input type="text" class="form-control" name="category"
									placeholder="Enter Category"
									value="<%=DataUtility.getStringData(dto.getCategory())%>">
							</div>

							<font color="red"> <%=ServletUtility.getErrorMessage("category", request)%>
							</font> <br>
							<%
								if (dto.getId() != null && dto.getId() > 0) {
							%>

							<div class="text-center">

								<input type="submit" name="operation" class="btn btn-success"
									value="<%=PodcastCtl.OP_UPDATE%>"> <input type="submit"
									name="operation" class="btn btn-warning"
									value="<%=PodcastCtl.OP_CANCEL%>">

							</div>

							<%
								} else {
							%>

							<div class="text-center">

								<input type="submit" name="operation" class="btn btn-success"
									value="<%=PodcastCtl.OP_SAVE%>"> <input type="submit"
									name="operation" class="btn btn-warning"
									value="<%=PodcastCtl.OP_RESET%>">

							</div>

							<%
								}
							%>

						</div>

					</div>

				</div>

				<div class="col-md-4 mb-4"></div>

			</div>

		</form>

	</main>

</body>

<%@include file="FooterView.jsp"%>

</html>