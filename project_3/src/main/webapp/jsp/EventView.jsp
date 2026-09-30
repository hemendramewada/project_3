<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.controller.EventCtl"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<title>Event View</title>

<style type="text/css">
.i.css {
	border: 2px solid #8080803b;
	padding-left: 10px;
	padding-bottom: 11px;
	background-color: #ebebe0;
}

$(
function

 

()
{
$("
.datepicker4

 

")
.datepicker

 

({
dateFormat


:

 

'
dd
/mm/yy'
,
changeMonth


:

 

true
,
changeYear


:

 

true
,
yearRange


:


		 

'1950
:


2030'
,
//
auto-updatesyears
//uptotoday'sdate




}
);
}
);
.input-group-addon {
	box-shadow: 9px 8px 7px #001a33;
}

.hm {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/user1.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	padding-top: 75px;
}
</style>
</head>

<body class="hm">

	<div class="header">
		<%@include file="Header.jsp"%>
		<%@include file="calendar.jsp"%>
	</div>


	<main>
		<form action="<%=ORSView.EVENT_CTL%>" method="post">

			<jsp:useBean id="dto" class="in.co.rays.project_3.dto.EventDTO"
				scope="request"></jsp:useBean>

			<div class="row pt-3">
				<div class="col-md-4 mb-4"></div>

				<div class="col-md-4 mb-4">
					<div class="card input-group-addon">
						<div class="card-body">

							<%
								if (dto.getId() != null && dto.getId() > 0) {
							%>
							<h3 class="text-center text-primary">Update Event</h3>
							<%
								} else {
							%>
							<h3 class="text-center text-primary">Add Event</h3>
							<%
								}
							%>

							<!-- Success Message -->
							<%
								if (!ServletUtility.getSuccessMessage(request).equals("")) {
							%>
							<div class="alert alert-success alert-dismissible">
								<button type="button" class="close" data-dismiss="alert">&times;</button>
								<%=ServletUtility.getSuccessMessage(request)%>
							</div>
							<%
								}
							%>

							<!-- Error Message -->
							<%
								if (!ServletUtility.getErrorMessage(request).equals("")) {
							%>
							<div class="alert alert-danger alert-dismissible">
								<button type="button" class="close" data-dismiss="alert">&times;</button>
								<%=ServletUtility.getErrorMessage(request)%>
							</div>
							<%
								}
							%>

							<input type="hidden" name="id" value="<%=dto.getId()%>">

							<!-- Event Code -->
							<span><b>Event Code</b> <span style="color: red">*</span></span>
							<div class="col-sm-12">
								<div class="input-group">
									<div class="input-group-prepend">
										<div class="input-group-text">
											<i class="fa fa-code grey-text"></i>
										</div>
									</div>
									<input type="text" class="form-control" name="eventCode"
										placeholder="Event Code"
										value="<%=DataUtility.getStringData(dto.getEventCode())%>">
								</div>
							</div>
							<font color="red"> <%=ServletUtility.getErrorMessage("eventCode", request)%>
							</font><br>

							<!-- Event Name -->
							<span><b>Event Name</b> <span style="color: red">*</span></span>
							<div class="col-sm-12">
								<div class="input-group">
									<div class="input-group-prepend">
										<div class="input-group-text">
											<i class="fa fa-user grey-text"></i>
										</div>
									</div>
									<input type="text" class="form-control" name="eventName"
										placeholder="Event Name"
										value="<%=DataUtility.getStringData(dto.getEventName())%>">
								</div>
							</div>
							<font color="red"> <%=ServletUtility.getErrorMessage("eventName", request)%>
							</font><br>

							<!-- Event Date -->
							<span><b>Event Date</b> <span style="color: red">*</span></span>
							<div class="col-sm-12">
								<div class="input-group">
									<div class="input-group-prepend">
										<div class="input-group-text">
											<i class="fa fa-calendar grey-text"></i>
										</div>
									</div>
									<input type="text" name="eventDate" id="datepicker4"
										placeholder="enter date" class="form-control"
										value="<%=DataUtility.getDateString(dto.getEventDate())%>">

								</div>
							</div>
							<font color="red"> <%=ServletUtility.getErrorMessage("eventDate", request)%>
							</font><br>

							<!-- Organizer -->
							<span><b>Organizer</b> <span style="color: red">*</span></span>
							<div class="col-sm-12">
								<div class="input-group">
									<div class="input-group-prepend">
										<div class="input-group-text">
											<i class="fa fa-user grey-text"></i>
										</div>
									</div>
									<input type="text" class="form-control" name="organizer"
										placeholder="Organizer"
										value="<%=DataUtility.getStringData(dto.getOrganizer())%>">
								</div>
							</div>
							<font color="red"> <%=ServletUtility.getErrorMessage("organizer", request)%>
							</font><br>

							<!-- Event Status Static Dropdown -->
							<span><b>Event Status</b> <span style="color: red">*</span></span>
							<div class="col-sm-12">
								<div class="input-group">
									<div class="input-group-prepend">
										<div class="input-group-text">
											<i class="fa fa-info-circle grey-text"></i>
										</div>
									</div>

									<%
										HashMap statusMap = new HashMap();
										statusMap.put("Active", "Active");
										statusMap.put("Inactive", "Inactive");
										statusMap.put("Canceled", "Canceled");

										String statusList = HTMLUtility.getList("eventStatus", dto.getEventStatus(), statusMap);
									%>

									<%=statusList%>

								</div>
							</div>
							<font color="red"> <%=ServletUtility.getErrorMessage("eventStatus", request)%>
							</font><br>

							<!-- Buttons -->
							<div class="text-center">

								<%
									if (dto.getId() != null && dto.getId() > 0) {
								%>
								<input type="submit" name="operation"
									class="btn btn-success btn-md" value="<%=EventCtl.OP_UPDATE%>">

								<input type="submit" name="operation"
									class="btn btn-warning btn-md" value="<%=EventCtl.OP_CANCEL%>">
								<%
									} else {
								%>
								<input type="submit" name="operation"
									class="btn btn-success btn-md" value="<%=EventCtl.OP_SAVE%>">

								<input type="submit" name="operation"
									class="btn btn-warning btn-md" value="<%=EventCtl.OP_RESET%>">
								<%
									}
								%>

							</div>

						</div>
					</div>
				</div>

				<div class="col-md-4 mb-4"></div>
			</div>

		</form>
	</main>

	<%@include file="FooterView.jsp"%>

</body>
</html>