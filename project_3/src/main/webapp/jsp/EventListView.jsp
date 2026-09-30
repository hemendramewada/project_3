<%@page import="in.co.rays.project_3.controller.EventListCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.dto.EventDTO"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>

<html>
<head>
<title>Event List</title>

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

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.EventDTO"
			scope="request"></jsp:useBean>

		<h2 class="text-center text-light font-weight-bold">Event List</h2>

		<form action="<%=ORSView.EVENT_LIST_CTL%>" method="post">

			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;
				int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

				List<EventDTO> list = (List<EventDTO>) ServletUtility.getList(request);
				Iterator<EventDTO> it = list.iterator();
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

								<!-- Event Code -->
								<div class="mx-2">
									<label><b>Event Code :</b></label>
								</div>
								<div class="mx-2">
									<input type="text" class="form-control form-control-sm"
										name="eventCode"
										value="<%=ServletUtility.getParameter("eventCode", request)%>">
								</div>

								<!-- Event Name -->
								<div class="mx-2">
									<label><b>Event Name :</b></label>
								</div>
								<div class="mx-2">
									<input type="text" class="form-control form-control-sm"
										name="eventName"
										value="<%=ServletUtility.getParameter("eventName", request)%>">
								</div>

								<!-- Event Status Preload -->
								<div class="mx-2">
									<label><b>Status :</b></label>
								</div>
								<div class="mx-2">
									<select name="eventStatus" class="form-control form-control-sm">
										<option value="">--Select--</option>

										<%
											String selectedStatus = ServletUtility.getParameter("eventStatus", request);
											String[] statusArray = { "Active", "Inactive", "Canceled" };

											for (String status : statusArray) {
										%>
										<option value="<%=status%>"
											<%=status.equals(selectedStatus) ? "selected" : ""%>>
											<%=status%>
										</option>
										<%
											}
										%>

									</select>
								</div>

								<!-- Buttons -->
								<div class="mx-2">
									<input type="submit" class="btn btn-sm btn-primary"
										name="operation" value="<%=EventListCtl.OP_SEARCH%>">

									<input type="submit"
										class="btn btn-sm btn-outline-secondary ml-1" name="operation"
										value="<%=EventListCtl.OP_RESET%>">
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
							<th>Event Code</th>
							<th>Event Name</th>
							<th>Event Date</th>
							<th>Organizer</th>
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
							<td><%=dto.getEventCode()%></td>
							<td><%=dto.getEventName()%></td>
							<td><%=DataUtility.getDateString(dto.getEventDate())%></td>
							<td><%=dto.getOrganizer()%></td>
							<td><%=dto.getEventStatus()%></td>
							<td><a href="EventCtl?id=<%=dto.getId()%>"
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
						value="<%=EventListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>

					<td width="25%" class="text-center"><input type="submit"
						class="btn btn-outline-success" name="operation"
						value="<%=EventListCtl.OP_NEW%>"></td>

					<td width="25%" class="text-center"><input type="submit"
						class="btn btn-outline-danger" name="operation"
						value="<%=EventListCtl.OP_DELETE%>"></td>

					<td width="25%" class="text-right"><input type="submit"
						class="btn btn-outline-primary" name="operation"
						value="<%=EventListCtl.OP_NEXT%>"
						<%=nextPageSize != 0 ? "" : "disabled"%>></td>

				</tr>
			</table>

		</form>
	</div>

	<%@include file="FooterView.jsp"%>

</body>
</html>