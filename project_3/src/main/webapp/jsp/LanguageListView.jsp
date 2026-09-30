<%@page import="in.co.rays.project_3.controller.LanguageListCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.dto.LanguageDTO"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>

<html>
<head>
<title>Language List</title>

<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>


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

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.LanguageDTO"
			scope="request"></jsp:useBean>

		<h2 class="text-center text-light font-weight-bold">Language List</h2>

		<!-- Messages -->
		<%
			if (!ServletUtility.getErrorMessage(request).equals("")) {
		%>
		<div class="alert alert-danger">
			<%=ServletUtility.getErrorMessage(request)%>
		</div>
		<%
			}
			if (!ServletUtility.getSuccessMessage(request).equals("")) {
		%>
		<div class="alert alert-success">
			<%=ServletUtility.getSuccessMessage(request)%>
		</div>
		<%
			}
		%>

		<form action="<%=ORSView.LANGUAGE_LIST_CTL%>" method="post">

			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;

				int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

				List<LanguageDTO> list = (List<LanguageDTO>) ServletUtility.getList(request);

				Iterator<LanguageDTO> it = list.iterator();

				if (list.size() != 0) {
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<!-- SEARCH FILTER -->
			<div class="table-responsive">
				<table
					class="table table-borderless w-100 text-center bg-transparent">
					<tr>
						<td>
							<div
								class="d-flex justify-content-center align-items-center flex-wrap bg-light p-3 rounded shadow-sm">

								<div class="mx-2">
									<label><b>Language Code :</b></label>
								</div>
								<div class="mx-2">
									<input type="text" class="form-control form-control-sm"
										name="languageCode" placeholder="Enter Code"
										value="<%=ServletUtility.getParameter("languageCode", request)%>">
								</div>

								<div class="mx-2">
									<label><b>Language Name :</b></label>
								</div>
								<div class="mx-2">
									<input type="text" class="form-control form-control-sm"
										name="languageName" placeholder="Enter Name"
										value="<%=ServletUtility.getParameter("languageName", request)%>">
								</div>

								<div class="mx-2">
									<label><b>Status :</b></label>
								</div>
								<div class="mx-2">
									<select name="languageStatus"
										class="form-control form-control-sm">
										<option value="">--All--</option>
										<option value="Active">Active</option>
										<option value="Inactive">Inactive</option>
									</select>
								</div>

								<div class="mx-2">
									<input type="submit" class="btn btn-sm btn-primary"
										name="operation" value="<%=LanguageListCtl.OP_SEARCH%>">

									<input type="submit"
										class="btn btn-sm btn-outline-secondary ml-1" name="operation"
										value="<%=LanguageListCtl.OP_RESET%>">
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
							<th>Language Code</th>
							<th>Language Name</th>
							<th>Direction</th>
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
							<td><input type="checkbox" class="checkbox" name="ids"
								value="<%=dto.getId()%>"></td>
							<td><%=index++%></td>
							<td><%=dto.getLanguageCode()%></td>
							<td class="text-capitalize"><%=dto.getLanguageName()%></td>
							<td><%=dto.getDirection()%></td>
							<td><%=dto.getLanguageStatus()%></td>
							<td><a href="LanguageCtl?id=<%=dto.getId()%>"
								class="btn btn-link btn-sm p-0"> Edit </a></td>
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
						value="<%=LanguageListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>

					<td width="25%" class="text-center"><input type="submit"
						class="btn btn-outline-success" name="operation"
						value="<%=LanguageListCtl.OP_NEW%>"></td>

					<td width="25%" class="text-center"><input type="submit"
						class="btn btn-outline-danger" name="operation"
						value="<%=LanguageListCtl.OP_DELETE%>"></td>

					<td width="25%" class="text-right"><input type="submit"
						class="btn btn-outline-primary" name="operation"
						value="<%=LanguageListCtl.OP_NEXT%>"
						<%=nextPageSize != 0 ? "" : "disabled"%>></td>
				</tr>
			</table>

			<%
				} else {
			%>

			<table class="table w-100">
				<tr>
					<td class="text-center text-light">No Record Found</td>
				</tr>
				<tr>
					<td class="text-center"><input type="submit"
						class="btn btn-success" name="operation"
						value="<%=LanguageListCtl.OP_BACK%>"></td>
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