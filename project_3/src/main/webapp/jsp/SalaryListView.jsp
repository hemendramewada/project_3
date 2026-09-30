<%@page import="in.co.rays.project_3.controller.SalaryListCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.dto.SalaryDTO"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>

<html>
<head>
<title>Salary List</title>

<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />

<style>
.p4 {
	 background-image: url('<%=ORSView.APP_CONTEXT%>/img/Linkme.jpg');
	background-size: cover;
	background-position: center;
	background-attachment: fixed;
	min-height: 100vh;
	padding-top: 70px;
	padding-bottom: 80px;
}
</style>
</head>

<body class="p4">

	<%@include file="Header.jsp"%>

	<div class="container-fluid">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.SalaryDTO"
			scope="request"></jsp:useBean>

		<h2 class="text-center text-light font-weight-bold">Salary List</h2>

		<form action="<%=ORSView.SALARY_LIST_CTL%>" method="post">

			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;
				int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

				List<SalaryDTO> list = (List<SalaryDTO>) ServletUtility.getList(request);
				Iterator<SalaryDTO> it = list.iterator();
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

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

			<!-- SEARCH FILTER -->
			<div class="table-responsive">
				<table
					class="table table-borderless w-100 text-center bg-light rounded shadow-sm">
					<tr>
						<td>
							<div
								class="d-flex justify-content-center align-items-center flex-wrap p-3">

								<!-- Salary Code -->
								<div class="mx-2">
									<label><b>Salary Code :</b></label>
								</div>
								<div class="mx-2">
									<input type="text" class="form-control form-control-sm"
										name="salaryCode" placeholder="Enter Salary Code"
										value="<%=ServletUtility.getParameter("salaryCode", request)%>">
								</div>

								<!-- Employee Name -->
								<div class="mx-2">
									<label><b>Employee Name :</b></label>
								</div>
								<div class="mx-2">
									<input type="text" class="form-control form-control-sm"
										name="employeeName" placeholder="Enter Employee Name"
										value="<%=ServletUtility.getParameter("employeeName", request)%>">
								</div>

								<!-- Salary Status -->
								<div class="mx-2">
									<label><b>Salary Status :</b></label>
								</div>
								<div class="mx-2">
									<input type="text" class="form-control form-control-sm"
										name="salaryStatus" placeholder="Enter Salary Status"
										value="<%=ServletUtility.getParameter("salaryStatus", request)%>">
								</div>

								<!-- Buttons -->
								<div class="mx-2">
									<input type="submit" class="btn btn-sm btn-primary"
										name="operation" value="<%=SalaryListCtl.OP_SEARCH%>">

									<input type="submit"
										class="btn btn-sm btn-outline-secondary ml-1" name="operation"
										value="<%=SalaryListCtl.OP_RESET%>">
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
							<th>Salary Code</th>
							<th>Employee Name</th>
							<th>Salary Amount</th>
							<th>Salary Status</th>
							<th>Edit</th>
						</tr>
					</thead>

					<tbody>
						<%
							while (it.hasNext()) {
								dto = it.next();
						%>
						<tr>
							<td><input type="checkbox" class="checkbox" name="ids"
								value="<%=dto.getId()%>"></td>
							<td><%=index++%></td>
							<td><%=dto.getSalaryCode()%></td>
							<td><%=dto.getEmployeeName()%></td>
							<td><%=dto.getSalaryAmount()%></td>
							<td><%=dto.getSalaryStatus()%></td>
							<td><a href="SalaryCtl?id=<%=dto.getId()%>"
								class="btn btn-link btn-sm p-0">Edit</a></td>
						</tr>
						<%
							}
						%>
					</tbody>

				</table>
			</div>

			<!-- PAGINATION -->
			<table class="table w-100">
				<tr>

					<td width="25%"><input type="submit"
						class="btn btn-outline-primary" name="operation"
						value="<%=SalaryListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>

					<td width="25%" class="text-center"><input type="submit"
						class="btn btn-outline-success" name="operation"
						value="<%=SalaryListCtl.OP_NEW%>"></td>

					<td width="25%" class="text-center"><input type="submit"
						class="btn btn-outline-danger" name="operation"
						value="<%=SalaryListCtl.OP_DELETE%>"></td>

					<td width="25%" class="text-right"><input type="submit"
						class="btn btn-outline-primary" name="operation"
						value="<%=SalaryListCtl.OP_NEXT%>"
						<%=nextPageSize != 0 ? "" : "disabled"%>></td>

				</tr>
			</table>

			<%
				if (list.size() == 0) {
			%>

			<table class="table w-100">
				<tr>
					<td class="text-right"><input type="submit"
						class="btn btn-warning" name="operation"
						value="<%=SalaryListCtl.OP_BACK%>"></td>
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