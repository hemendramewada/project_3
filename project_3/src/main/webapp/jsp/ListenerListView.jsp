<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.dto.ListenerDTO"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.controller.ListenerListCtl"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>

<html>
<head>
<title>Listener List</title>

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>

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

	<form class="pb-5" action="<%=ORSView.LISTENER_LIST_CTL%>"
		method="post">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.ListenerDTO"
			scope="request"></jsp:useBean>

		<%
			HashMap map = (HashMap) request.getAttribute("statusList");

			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int index = ((pageNo - 1) * pageSize) + 1;
			int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

			List list = ServletUtility.getList(request);
			Iterator<ListenerDTO> it = list.iterator();
		%>

		<center>
			<h1 class="text-dark">
				<b><u>Listener List</u></b>
			</h1>
		</center>

		<!-- ✅ SUCCESS -->
		<%
			if (!ServletUtility.getSuccessMessage(request).equals("")) {
		%>
		<div class="alert alert-success text-center">
			<%=ServletUtility.getSuccessMessage(request)%>
		</div>
		<%
			}
		%>

		<!-- ❌ ERROR -->
		<%
			if (!ServletUtility.getErrorMessage(request).equals("")) {
		%>
		<div class="alert alert-danger text-center">
			<%=ServletUtility.getErrorMessage(request)%>
		</div>
		<%
			}
		%>

		<%
			if (list.size() != 0) {
		%>

		<!-- 🔍 SEARCH -->
		<div class="row">

			<div class="col-sm-2"></div>

			<div class="col-sm-2">
				<input type="text" name="listenerCode" class="form-control"
					placeholder="Listener Code"
					value="<%=ServletUtility.getParameter("listenerCode", request)%>">
			</div>

			<div class="col-sm-2">
				<input type="text" name="queueName" class="form-control"
					placeholder="Queue Name"
					value="<%=ServletUtility.getParameter("queueName", request)%>">
			</div>

			<div class="col-sm-2">
				<input type="text" name="consumerGroup" class="form-control"
					placeholder="Consumer Group"
					value="<%=ServletUtility.getParameter("consumerGroup", request)%>">
			</div>

			<div class="col-sm-2">
				<%=HTMLUtility.getList("status", ServletUtility.getParameter("status", request), map)%>
			</div>

			<div class="col-sm-2">
				<input type="submit" class="btn btn-primary" name="operation"
					value="<%=ListenerListCtl.OP_SEARCH%>"> <input
					type="submit" class="btn btn-dark" name="operation"
					value="<%=ListenerListCtl.OP_RESET%>">
			</div>

		</div>

		<br>

		<!-- 📋 TABLE -->
		<div class="table-responsive">
			<table class="table table-bordered table-dark table-hover">

				<thead>
					<tr style="background-color: red;">
						<th><input type="checkbox" id="select_all"> Select
							All</th>
						<th>S.No</th>
						<th>Listener Code</th>
						<th>Queue Name</th>
						<th>Consumer Group</th>
						<th>Status</th>
						<th>Edit</th>
					</tr>
				</thead>

				<tbody>

					<%
						while (it.hasNext()) {
								dto = it.next();
					%>

					<tr>
						<td align="center"><input type="checkbox" class="checkbox"
							name="ids" value="<%=dto.getId()%>"></td>

						<td class="text"><%=index++%></td>
						<td class="text"><%=dto.getListenerCode()%></td>
						<td class="text"><%=dto.getQueueName()%></td>
						<td class="text"><%=dto.getConsumerGroup()%></td>
						<td class="text"><%=dto.getStatus()%></td>

						<td class="text"><a href="ListenerCtl?id=<%=dto.getId()%>">Edit</a>
						</td>
					</tr>

					<%
						}
					%>

				</tbody>
			</table>
		</div>

		<!-- 🔘 BUTTONS -->
		<table width="100%">
			<tr>

				<td><input type="submit" class="btn btn-warning"
					name="operation" value="<%=ListenerListCtl.OP_PREVIOUS%>"
					<%=pageNo > 1 ? "" : "disabled"%>></td>

				<td><input type="submit" class="btn btn-primary"
					name="operation" value="<%=ListenerListCtl.OP_NEW%>"></td>

				<td><input type="submit" class="btn btn-danger"
					name="operation" value="<%=ListenerListCtl.OP_DELETE%>"></td>

				<td align="right"><input type="submit" class="btn btn-warning"
					name="operation" value="<%=ListenerListCtl.OP_NEXT%>"
					<%=(nextPageSize > 0) ? "" : "disabled"%>></td>

			</tr>
		</table>

		<%
			} else {
		%>

		<center>
			<h1 style="color: white;">No Records Found</h1>
		</center>

		<div style="padding-left: 48%;">
			<input type="submit" name="operation" class="btn btn-primary"
				value="<%=ListenerListCtl.OP_BACK%>">
		</div>

		<%
			}
		%>

		<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
			type="hidden" name="pageSize" value="<%=pageSize%>">

	</form>

	<%@include file="FooterView.jsp"%>

</body>
</html>