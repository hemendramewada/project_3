<%@page import="in.co.rays.project_3.controller.ResultListCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.dto.ResultDTO"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>

<html>
<head>
<title>Result List</title>

<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>

<style>
.p4 {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/result1.jpg');
	background-size: cover;
	background-position: center;
	background-repeat: no-repeat;
	padding-top: 70px;
	padding-bottom: 30px;
}
</style>
</head>

<body class="p4">

	<%@include file="Header.jsp"%>

	<div class="container-fluid">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.ResultDTO"
			scope="request"></jsp:useBean>

		<h2 class="text-center text-light font-weight-bold">Result List</h2>

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

		<form action="<%=ORSView.RESULT_LIST_CTL%>" method="post">

			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;

				int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

				List<ResultDTO> list = (List<ResultDTO>) ServletUtility.getList(request);

				Iterator<ResultDTO> it = list.iterator();

				if (list.size() != 0) {
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>"> <br>

			<!-- SEARCH FILTER -->
			<div class="table-responsive">
				<table
					class="table table-borderless w-100 text-center bg-transparent">
					<tr>
						<td>
							<div
								class="d-flex justify-content-center align-items-center flex-wrap bg-light p-3 rounded shadow-sm">

								<!-- Result Code -->
								<div class="mx-2">
									<label><b>Result Code :</b></label>
								</div>
								<div class="mx-2">
									<input type="text" class="form-control form-control-sm"
										name="resultCode" placeholder="Enter Result Code"
										value="<%=ServletUtility.getParameter("resultCode", request)%>">
								</div>

								<!-- Student Name -->
								<div class="mx-2">
									<label><b>Student Name :</b></label>
								</div>
								<div class="mx-2">
									<input type="text" class="form-control form-control-sm"
										name="studentName" placeholder="Enter Student Name"
										value="<%=ServletUtility.getParameter("studentName", request)%>">
								</div>

								<!-- Grade -->
								<div class="mx-2">
									<label><b>Grade :</b></label>
								</div>
								<div class="mx-2">
									<input type="text" class="form-control form-control-sm"
										name="grade" placeholder="Enter Grade"
										value="<%=ServletUtility.getParameter("grade", request)%>">
								</div>

								<!-- Buttons -->
								<div class="mx-2">
									<input type="submit" class="btn btn-sm btn-primary"
										name="operation" value="<%=ResultListCtl.OP_SEARCH%>">

									<input type="submit"
										class="btn btn-sm btn-outline-secondary ml-1" name="operation"
										value="<%=ResultListCtl.OP_RESET%>">
								</div>

							</div>
						</td>
					</tr>
				</table>
			</div>

			<br>

			<!-- DATA TABLE -->
			<div class="table-responsive">
				<table
					class="table table-bordered table-hover w-100 text-center bg-white shadow-sm">

					<thead class="thead-light">
						<tr>
							<th><input type="checkbox" id="select_all"> Select
								All</th>
							<th>S.No</th>
							<th>Result Code</th>
							<th>Student Name</th>
							<th>Marks</th>
							<th>Grade</th>
							<th>Edit</th>
						</tr>
					</thead>

					<tbody>
						<%
							while (it.hasNext()) {
									dto = (ResultDTO) it.next();
						%>
						<tr>
							<td><input type="checkbox" class="checkbox" name="ids"
								value="<%=dto.getId()%>"></td>

							<td><%=index++%></td>

							<td><%=dto.getResultCode()%></td>

							<td class="text-capitalize"><%=dto.getStudentName()%></td>

							<td><%=dto.getMarks()%></td>

							<td><%=dto.getGrade()%></td>

							<td><a href="ResultCtl?id=<%=dto.getId()%>"
								class="btn btn-link btn-sm p-0"> Edit </a></td>

						</tr>
						<%
							}
						%>
					</tbody>

				</table>
			</div>

			<!-- PAGINATION & ACTIONS -->
			<table class="table w-100">
				<tr>

					<td width="25%"><input type="submit"
						class="btn btn-outline-primary" name="operation"
						value="<%=ResultListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>

					<td width="25%" class="text-center"><input type="submit"
						class="btn btn-outline-success" name="operation"
						value="<%=ResultListCtl.OP_NEW%>"></td>

					<td width="25%" class="text-center"><input type="submit"
						class="btn btn-outline-danger" name="operation"
						value="<%=ResultListCtl.OP_DELETE%>"></td>

					<td width="25%" class="text-right"><input type="submit"
						class="btn btn-outline-primary" name="operation"
						value="<%=ResultListCtl.OP_NEXT%>"
						<%=nextPageSize != 0 ? "" : "disabled"%>></td>

				</tr>
			</table>

			<%
				}

				if (list.size() == 0) {
			%>

			<table class="table w-100">
				<tr>
					<td class="text-right"><input type="submit"
						class="btn btn-warning" name="operation"
						value="<%=ResultListCtl.OP_BACK%>"></td>
				</tr>
			</table>

			<%
				}
			%>

		</form>
	</div>

	<%@include file="FooterView.jsp"%>

</body>
</html>