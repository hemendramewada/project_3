<%@page import="in.co.rays.project_3.controller.HospitalListCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.dto.HospitalDTO"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>

<html>
<head>
<title>Hospital List</title>

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

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.HospitalDTO"
			scope="request"></jsp:useBean>

		<h2 class="text-center text-light font-weight-bold">Hospital List</h2>

		<form action="<%=ORSView.HOSPITAL_LIST_CTL%>" method="post">

			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;
				int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

				List<HospitalDTO> list = (List<HospitalDTO>) ServletUtility.getList(request);
				List<HospitalDTO> hospitalNameList = (List<HospitalDTO>) request.getAttribute("hospitalNameList");

				Iterator<HospitalDTO> it = list.iterator();
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

								<!-- Hospital ID -->
								<div class="mx-2">
									<label><b>Hospital ID :</b></label>
								</div>
								<div class="mx-2">
									<input type="text" class="form-control form-control-sm"
										name="hospitalId"
										value="<%=ServletUtility.getParameter("hospitalId", request)%>">
								</div>

								<!-- Hospital Name (Dynamic Preload) -->
								<div class="mx-2">
									<label><b>Hospital Name :</b></label>
								</div>
								<div class="mx-2">
									<select name="hospitalName"
										class="form-control form-control-sm">
										<option value="">--Select--</option>

										<%
											if (hospitalNameList != null) {
												for (HospitalDTO h : hospitalNameList) {
										%>
										<option value="<%=h.getHospitalName()%>"
											<%=h.getHospitalName().equals(ServletUtility.getParameter("hospitalName", request))
							? "selected"
							: ""%>>
											<%=h.getHospitalName()%>
										</option>
										<%
											}
											}
										%>
									</select>
								</div>

								<!-- City -->
								<div class="mx-2">
									<label><b>City :</b></label>
								</div>
								<div class="mx-2">
									<input type="text" class="form-control form-control-sm"
										name="city"
										value="<%=ServletUtility.getParameter("city", request)%>">
								</div>

								<!-- Buttons -->
								<div class="mx-2">
									<input type="submit" class="btn btn-sm btn-primary"
										name="operation" value="<%=HospitalListCtl.OP_SEARCH%>">

									<input type="submit"
										class="btn btn-sm btn-outline-secondary ml-1" name="operation"
										value="<%=HospitalListCtl.OP_RESET%>">
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
							<th>Hospital ID</th>
							<th>Hospital Name</th>
							<th>City</th>
							<th>Contact Number</th>
							<th>Edit</th>
						</tr>
					</thead>

					<tbody>
						<%
							while (it.hasNext()) {
								dto = it.next();
						%>
						<tr>
							<td><input type="checkbox" name="ids"
								value="<%=dto.getId()%>"></td>
							<td><%=index++%></td>
							<td><%=dto.getHospitalId()%></td>
							<td><%=dto.getHospitalName()%></td>
							<td><%=dto.getCity()%></td>
							<td><%=dto.getContactNumber()%></td>
							<td><a href="HospitalCtl?id=<%=dto.getId()%>"
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
						value="<%=HospitalListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>

					<td width="25%" class="text-center"><input type="submit"
						class="btn btn-outline-success" name="operation"
						value="<%=HospitalListCtl.OP_NEW%>"></td>

					<td width="25%" class="text-center"><input type="submit"
						class="btn btn-outline-danger" name="operation"
						value="<%=HospitalListCtl.OP_DELETE%>"></td>

					<td width="25%" class="text-right"><input type="submit"
						class="btn btn-outline-primary" name="operation"
						value="<%=HospitalListCtl.OP_NEXT%>"
						<%=nextPageSize != 0 ? "" : "disabled"%>></td>

				</tr>
			</table>

		</form>
	</div>

	<%@include file="FooterView.jsp"%>

</body>
</html>