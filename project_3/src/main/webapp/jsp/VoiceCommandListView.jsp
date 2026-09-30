<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>

<%@page import="in.co.rays.project_3.dto.VoiceCommandDTO"%>
<%@page import="in.co.rays.project_3.controller.VoiceCommandListCtl"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>

<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Voice Command List</title>

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

		<form class="pb-5" action="<%=ORSView.VOICE_COMMAND_LIST_CTL%>"
			method="post">

			<jsp:useBean id="dto"
				class="in.co.rays.project_3.dto.VoiceCommandDTO" scope="request">
			</jsp:useBean>

			<%
				int pageNo = ServletUtility.getPageNo(request);

				int pageSize = ServletUtility.getPageSize(request);

				int index = ((pageNo - 1) * pageSize) + 1;

				int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

				List list = ServletUtility.getList(request);

				Iterator<VoiceCommandDTO> it = list.iterator();

				if (list.size() != 0) {
			%>

			<center>

				<h1 class="text-dark">

					<b><u>Voice Command List</u></b>

				</h1>

			</center>

			<div class="row">

				<div class="col-md-4"></div>

				<%
					if (!ServletUtility.getSuccessMessage(request).equals("")) {
				%>

				<div class="col-md-4 alert alert-success alert-dismissible"
					style="background-color: #80ff80">

					<button type="button" class="close" data-dismiss="alert">

						&times;</button>

					<h4>

						<font color="#008000"> <%=ServletUtility.getSuccessMessage(request)%>

						</font>

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

						<font color="red"> <%=ServletUtility.getErrorMessage(request)%>

						</font>

					</h4>

				</div>

				<%
					}
				%>

				<div class="col-md-4"></div>

			</div>
			<div class="row">

				<div class="col-sm-2"></div>

				<div class="col-sm-2">

					<input type="text" name="commandCode"
						placeholder="Enter Command Code" class="form-control"
						value="<%=ServletUtility.getParameter("commandCode", request)%>">

				</div>

				&nbsp;&nbsp;

				<div class="col-sm-2">

					<input type="text" name="userName" placeholder="Enter User Name"
						class="form-control"
						value="<%=ServletUtility.getParameter("userName", request)%>">

				</div>

				&nbsp;&nbsp;

				<div class="col-sm-2">

					<select name="status" class="form-control">

						<option value="">--Select Status--</option>

						<option value="Active"
							<%=("Active".equals(dto.getStatus())) ? "selected" : ""%>>
							Active</option>

						<option value="Inactive"
							<%=("Inactive".equals(dto.getStatus())) ? "selected" : ""%>>
							Inactive</option>

					</select>

				</div>

				<div class="col-sm-3">

					<input type="submit" class="btn btn-primary btn-md"
						name="operation" value="<%=VoiceCommandListCtl.OP_SEARCH%>">

					&nbsp; <input type="submit" class="btn btn-dark btn-md"
						name="operation" value="<%=VoiceCommandListCtl.OP_RESET%>">

				</div>

			</div>

			<br>

			<div style="margin-bottom: 20px;" class="table-responsive">

				<table class="table table-bordered table-dark table-hover">

					<thead>

						<tr style="background-color: red;">

							<th width="10%"><input type="checkbox" id="select_all"
								name="Select"> Select All</th>

							<th class="text">S.NO</th>

							<th class="text">Command Id</th>

							<th class="text">Command Code</th>

							<th class="text">User Name</th>

							<th class="text">Command Text</th>

							<th class="text">Status</th>

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
								name="ids" value="<%=dto.getCommandId()%>"></td>

							<td class="text"><%=index++%></td>

							<td class="text"><%=dto.getCommandId()%></td>

							<td class="text"><%=dto.getCommandCode()%></td>

							<td class="text"><%=dto.getUserName()%></td>

							<td class="text"><%=dto.getCommandText()%></td>

							<td class="text"><%=dto.getStatus()%></td>

							<td class="text"><a
								href="VoiceCommandCtl?commandId=<%=dto.getCommandId()%>">

									Edit </a></td>

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
						value="<%=VoiceCommandListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>

					<td><input type="submit" name="operation"
						class="btn btn-primary btn-md" style="font-size: 17px"
						value="<%=VoiceCommandListCtl.OP_NEW%>"></td>

					<td><input type="submit" name="operation"
						class="btn btn-danger btn-md" style="font-size: 17px"
						value="<%=VoiceCommandListCtl.OP_DELETE%>"></td>

					<td align="right"><input type="submit" name="operation"
						class="btn btn-warning btn-md" style="font-size: 17px"
						value="<%=VoiceCommandListCtl.OP_NEXT%>"
						<%=(nextPageSize > 0) ? "" : "disabled"%>></td>

				</tr>

			</table>

			<%
				}

				if (list.size() == 0) {
			%>

			<center>

				<h1 style="font-size: 40px; color: #162390;">Voice Command List

				</h1>

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

						<font color="red"> <%=ServletUtility.getErrorMessage(request)%>

						</font>

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
					style="font-size: 17px" value="<%=VoiceCommandListCtl.OP_BACK%>">

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