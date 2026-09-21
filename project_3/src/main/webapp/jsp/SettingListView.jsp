<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.dto.SettingsDTO"%>
<%@page import="in.co.rays.project_3.controller.SettingsListCtl"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Settings List</title>

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>

</head>

<%@include file="Header.jsp"%>

<body>

<div>
<form action="<%=ORSView.SETTINGS_LIST_CTL%>" method="post">

<jsp:useBean id="dto"
	class="in.co.rays.project_3.dto.SettingsDTO"
	scope="request"></jsp:useBean>

<%
int pageNo = ServletUtility.getPageNo(request);
int pageSize = ServletUtility.getPageSize(request);
int index = ((pageNo - 1) * pageSize) + 1;
int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

List list = ServletUtility.getList(request);
Iterator<SettingsDTO> it = list.iterator();
%>

<% if (list.size() != 0) { %>

<center>
<h1>Settings List</h1>
</center>

<!-- Search Section -->
<div class="row">

<div class="col-sm-3">
<input type="text" name="settingName"
placeholder="Enter Setting Name"
class="form-control"
value="<%=ServletUtility.getParameter("settingName", request)%>">
</div>

<div class="col-sm-3">
<%
HashMap map = new HashMap();
map.put("Active", "Active");
map.put("Inactive", "Inactive");
String htmlList = HTMLUtility.getList("settingStatus", dto.getSettingStatus(), map);
%>
<%=htmlList%>
</div>

<div class="col-sm-3">
<input type="submit" class="btn btn-primary"
name="operation"
value="<%=SettingsListCtl.OP_SEARCH%>">

<input type="submit" class="btn btn-dark"
name="operation"
value="<%=SettingsListCtl.OP_RESET%>">
</div>

</div>

<br>

<!-- Table -->
<table class="table table-bordered table-dark table-hover">

<thead>
<tr>
<th><input type="checkbox" id="select_all"> Select All</th>
<th>S.NO</th>
<th>Setting Name</th>
<th>Setting Value</th>
<th>Setting Type</th>
<th>Setting Status</th>
<th>Edit</th>
</tr>
</thead>

<tbody>
<%
while (it.hasNext()) {
	dto = it.next();
%>
<tr>
<td align="center">
<input type="checkbox" class="checkbox"
name="ids" value="<%=dto.getId()%>">
</td>

<td><%=index++%></td>
<td><%=dto.getSettingName()%></td>
<td><%=dto.getSettingValue()%></td>
<td><%=dto.getSettingType()%></td>
<td><%=dto.getSettingStatus()%></td>

<td>
<a href="<%=ORSView.SETTINGS_CTL%>?id=<%=dto.getId()%>">Edit</a>
</td>

</tr>
<% } %>
</tbody>

</table>

<!-- Pagination Buttons -->
<table width="100%">
<tr>

<td>
<input type="submit" name="operation"
class="btn btn-warning"
value="<%=SettingsListCtl.OP_PREVIOUS%>"
<%=pageNo > 1 ? "" : "disabled"%>>
</td>

<td>
<input type="submit" name="operation"
class="btn btn-primary"
value="<%=SettingsListCtl.OP_NEW%>">
</td>

<td>
<input type="submit" name="operation"
class="btn btn-danger"
value="<%=SettingsListCtl.OP_DELETE%>">
</td>

<td align="right">
<input type="submit" name="operation"
class="btn btn-warning"
value="<%=SettingsListCtl.OP_NEXT%>"
<%=(nextPageSize != 0) ? "" : "disabled"%>>
</td>

</tr>
</table>

<% } else { %>

<center>
<h1>Settings List</h1>
</center>

<br>

<center>
<font color="red">
<%=ServletUtility.getErrorMessage(request)%>
</font>
</center>

<br>

<center>
<input type="submit" name="operation"
class="btn btn-primary"
value="<%=SettingsListCtl.OP_BACK%>">
</center>

<% } %>

<input type="hidden" name="pageNo" value="<%=pageNo%>">
<input type="hidden" name="pageSize" value="<%=pageSize%>">

</form>
</div>

<%@include file="FooterView.jsp"%>

</body>
</html>
