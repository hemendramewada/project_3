<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.controller.ListenerCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>

<html>
<head>
<title>Listener View</title>

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

	<form action="<%=ORSView.LISTENER_CTL%>" method="post">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.ListenerDTO"
			scope="request" />

		<div class="row pt-3 pb-4">

			<div class="col-md-4"></div>

			<div class="col-md-4">
				<div class="card shadow-lg">
					<div class="card-body">

						<%
							if (dto.getId() != null && dto.getId() > 0) {
						%>
						<h3 class="text-center text-primary">Update Listener</h3>
						<%
							} else {
						%>
						<h3 class="text-center text-primary">Add Listener</h3>
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

						<!-- Listener Code -->
						<b>Listener Code *</b> 
						<input type="text" name="listenerCode"
							class="form-control" placeholder="Enter Listener Code"
							value="<%=DataUtility.getStringData(dto.getListenerCode())%>">

						<font color="red">
							<%=ServletUtility.getErrorMessage("listenerCode", request)%>
						</font><br>

						<!-- Queue Name -->
						<b>Queue Name *</b> 
						<input type="text" name="queueName"
							class="form-control" placeholder="Enter Queue Name"
							value="<%=DataUtility.getStringData(dto.getQueueName())%>">

						<font color="red">
							<%=ServletUtility.getErrorMessage("queueName", request)%>
						</font><br>

						<!-- Consumer Group -->
						<b>Consumer Group *</b> 
						<input type="text" name="consumerGroup"
							class="form-control" placeholder="Enter Consumer Group"
							value="<%=DataUtility.getStringData(dto.getConsumerGroup())%>">

						<font color="red">
							<%=ServletUtility.getErrorMessage("consumerGroup", request)%>
						</font><br>

						<!-- Status Dropdown -->
						<b>Status *</b>

						<%
							HashMap map = (HashMap) request.getAttribute("statusList");
						%>

						<%=HTMLUtility.getList("status", dto.getStatus(), map)%>

						<font color="red">
							<%=ServletUtility.getErrorMessage("status", request)%>
						</font><br>

						<div class="text-center">

							<%
								if (dto.getId() != null && dto.getId() > 0) {
							%>

							<input type="submit" name="operation" class="btn btn-success"
								value="<%=ListenerCtl.OP_UPDATE%>"> 
								
							<input type="submit" name="operation" class="btn btn-warning"
								value="<%=ListenerCtl.OP_CANCEL%>">

							<%
								} else {
							%>

							<input type="submit" name="operation" class="btn btn-success"
								value="<%=ListenerCtl.OP_SAVE%>"> 
								
							<input type="submit" name="operation" class="btn btn-warning"
								value="<%=ListenerCtl.OP_RESET%>">

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