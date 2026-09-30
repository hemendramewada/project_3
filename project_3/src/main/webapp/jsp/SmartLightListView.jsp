<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.dto.SmartLightDTO"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.controller.SmartLightListCtl"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>

<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Smart Light List</title>

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>

<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>

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

	<div>

		<form class="pb-5" action="<%=ORSView.SMARTLIGHT_LIST_CTL%>"
			method="post">

			<jsp:useBean id="dto" class="in.co.rays.project_3.dto.SmartLightDTO"
				scope="request">
			</jsp:useBean>

			<%
				int pageNo = ServletUtility.getPageNo(request);

				int pageSize = ServletUtility.getPageSize(request);

				int index = ((pageNo - 1) * pageSize) + 1;

				int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

				List list = ServletUtility.getList(request);

				Iterator<SmartLightDTO> it = list.iterator();

				if (list.size() != 0) {
			%>

			<center>

				<h1 class="text-dark">

					<b><u>Smart Light List 💡</u></b>

				</h1>

			</center>

			<div class="row">

				<div class="col-md-4"></div>

				<%
					if (!ServletUtility.getSuccessMessage(request).equals("")) {
				%>

				<div class="col-md-4 alert alert-success alert-dismissible">

					<button type="button" class="close" data-dismiss="alert">

						&times;</button>

					<h4>

						<%=ServletUtility.getSuccessMessage(request)%>

					</h4>

				</div>

				<%
					}
				%>

				<div class="col-md-4"></div>

			</div>

			<div class="row">

				<div class="col-md-4"></div>

				<%
					if (!ServletUtility.getErrorMessage(request).equals("")) {
				%>

				<div class="col-md-4 alert alert-danger alert-dismissible">

					<button type="button" class="close" data-dismiss="alert">

						&times;</button>

					<h4>

						<%=ServletUtility.getErrorMessage(request)%>

					</h4>

				</div>

				<%
					}
				%>

				<div class="col-md-4"></div>

			</div>

			<div class="row">

				<div class="col-sm-2">

					<input type="text" name="lightCode" class="form-control"
						placeholder="Light Code"
						value="<%=ServletUtility.getParameter("lightCode", request)%>">

				</div>

				<div class="col-sm-3">

					<input type="text" name="roomName" class="form-control"
						placeholder="Room Name"
						value="<%=ServletUtility.getParameter("roomName", request)%>">

				</div>

				<div class="col-sm-2">

					<input type="number" name="brightnessLevel" class="form-control"
						placeholder="Brightness"
						value="<%=ServletUtility.getParameter("brightnessLevel", request)%>">

				</div>

				<div class="col-sm-2">

					<select name="status" class="form-control">

						<option value="">--Status--</option>

						<option value="ON">ON</option>

						<option value="OFF">OFF</option>

					</select>

				</div>

				<div class="col-sm-3">

					<input type="submit" name="operation" class="btn btn-primary"
						value="<%=SmartLightListCtl.OP_SEARCH%>"> <input
						type="submit" name="operation" class="btn btn-dark"
						value="<%=SmartLightListCtl.OP_RESET%>">

				</div>

			</div>

			<br> <br>

			<div style="margin-bottom: 20px;" class="table-responsive">

				<table class="table table-bordered table-dark table-hover">

					<thead>

						<tr style="background-color: red;">

							<th width="10%"><input type="checkbox" id="select_all"
								name="Select"> Select All</th>

							<th class="text">S.No</th>

							<th class="text">Light Code 💡</th>

							<th class="text">Room Name 🏠</th>

							<th class="text">Brightness 🔆</th>

							<th class="text">Status ⚡</th>

							<th class="text">Edit</th>

						</tr>

					</thead>

					<tbody>

						<%
							while (it.hasNext()) {

									dto = it.next();
						%>

						<tr>

							<td align="center"><input type="checkbox" class="checkbox"
								name="ids" value="<%=dto.getLightId()%>"></td>

							<td class="text"><%=index++%></td>

							<td class="text"><%=dto.getLightCode()%></td>

							<td class="text"><%=dto.getRoomName()%></td>

							<td class="text"><%=dto.getBrightnessLevel()%></td>

							<td class="text"><%=dto.getStatus()%></td>

							<td class="text"><a
								href="SmartLightCtl?lightId=<%=dto.getLightId()%>"> Edit </a></td>

						</tr>

						<%
							}
						%>

					</tbody>

				</table>

			</div>
			<table width="100%">

				<tr>

					<td><input type="submit" name="operation"
						class="btn btn-warning btn-md" style="font-size: 17px"
						value="<%=SmartLightListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>

					<td align="center"><input type="submit" name="operation"
						class="btn btn-primary btn-md" style="font-size: 17px"
						value="<%=SmartLightListCtl.OP_NEW%>"></td>

					<td align="center"><input type="submit" name="operation"
						class="btn btn-danger btn-md" style="font-size: 17px"
						value="<%=SmartLightListCtl.OP_DELETE%>"></td>

					<td align="right"><input type="submit" name="operation"
						class="btn btn-warning btn-md" style="font-size: 17px"
						value="<%=SmartLightListCtl.OP_NEXT%>"
						<%=(nextPageSize > 0) ? "" : "disabled"%>></td>

				</tr>

			</table>

			<%
				}

				if (list.size() == 0) {
			%>

			<center>

				<h1 style="font-size: 40px; color: #162390;">Smart Light List
					💡</h1>

			</center>

			<br>

			<div class="row">

				<div class="col-md-4"></div>

				<%
					if (!ServletUtility.getErrorMessage(request).equals("")) {
				%>

				<div class="col-md-4 alert alert-danger alert-dismissible">

					<button type="button" class="close" data-dismiss="alert">

						&times;</button>

					<h4>

						<%=ServletUtility.getErrorMessage(request)%>

					</h4>

				</div>

				<%
					}
				%>

				<div class="col-md-4"></div>

			</div>

			<br>

			<div style="padding-left: 48%;">

				<input type="submit" name="operation" class="btn btn-primary btn-md"
					style="font-size: 17px" value="<%=SmartLightListCtl.OP_BACK%>">

			</div>

			<%
				}
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

		</form>

	</div>

</body>

<%@include file="FooterView.jsp"%>

</html>