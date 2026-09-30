<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.dto.SecretDTO"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.controller.SecretListCtl"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>

<html>
<head>
<title>Secret List</title>

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

	<form class="pb-5" action="<%=ORSView.SECRET_LIST_CTL%>" method="post">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.SecretDTO"
			scope="request"></jsp:useBean>

		<%
			HashMap map = (HashMap) request.getAttribute("statusList");

			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int index = ((pageNo - 1) * pageSize) + 1;
			int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

			List list = ServletUtility.getList(request);
			Iterator<SecretDTO> it = list.iterator();
		%>

		<center>
			<h1 class="text-dark">
				<b><u>Secret List</u></b>
			</h1>
		</center>

		<!-- 🔥 SUCCESS MESSAGE (ALWAYS SHOW) -->
		<%
			if (!ServletUtility.getSuccessMessage(request).equals("")) {
		%>
		<div class="alert alert-success text-center">
			<%=ServletUtility.getSuccessMessage(request)%>
		</div>
		<%
			}
		%>

		<!-- 🔥 ERROR MESSAGE (DB DOWN YAHI DIKHEGA) -->
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
				<input type="text" name="secretCode" class="form-control"
					placeholder="Secret Code"
					value="<%=ServletUtility.getParameter("secretCode", request)%>">
			</div>

			<div class="col-sm-2">
				<input type="text" name="keyName" class="form-control"
					placeholder="Key Name"
					value="<%=ServletUtility.getParameter("keyName", request)%>">
			</div>

			<div class="col-sm-3">
				<%=HTMLUtility.getList("status", ServletUtility.getParameter("status", request), map)%>
			</div>

			<div class="col-sm-2">
				<input type="submit" class="btn btn-primary" name="operation"
					value="<%=SecretListCtl.OP_SEARCH%>"> <input type="submit"
					class="btn btn-dark" name="operation"
					value="<%=SecretListCtl.OP_RESET%>">
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
						<th>Secret Code</th>
						<th>Key Name</th>
						<th>Secret Value</th>
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
						<td class="text"><%=dto.getSecretCode()%></td>
						<td class="text"><%=dto.getKeyName()%></td>
						<td class="text"><%=dto.getSecretValue()%></td>
						<td class="text"><%=dto.getStatus()%></td>

						<td class="text"><a href="SecretCtl?id=<%=dto.getId()%>">Edit</a>
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
					name="operation" value="<%=SecretListCtl.OP_PREVIOUS%>"
					<%=pageNo > 1 ? "" : "disabled"%>></td>

				<td><input type="submit" class="btn btn-primary"
					name="operation" value="<%=SecretListCtl.OP_NEW%>"></td>

				<td><input type="submit" class="btn btn-danger"
					name="operation" value="<%=SecretListCtl.OP_DELETE%>"></td>

				<td align="right"><input type="submit" class="btn btn-warning"
					name="operation" value="<%=SecretListCtl.OP_NEXT%>"
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
				value="<%=SecretListCtl.OP_BACK%>">
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