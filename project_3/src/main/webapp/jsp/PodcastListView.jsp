<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.dto.PodcastDTO"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.controller.PodcastListCtl"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<form action="<%=ORSView.PODCAST_LIST_CTL%>" method="post">

	<jsp:useBean id="dto" class="in.co.rays.project_3.dto.PodcastDTO"
		scope="request">
	</jsp:useBean>

	<%
		int pageNo = ServletUtility.getPageNo(request);
		int pageSize = ServletUtility.getPageSize(request);
		int index = ((pageNo - 1) * pageSize) + 1;

		int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

		List list = ServletUtility.getList(request);

		Iterator<PodcastDTO> it = list.iterator();
	%>
	<center>
		<h1 class="text-primary font-weight-bold pt-3">
			<font color="black"> Podcast List </font>
		</h1>
	</center>
	<div class="row">

		<div class="col-sm-2">

			<input type="text" class="form-control" name="title"
				placeholder="Enter Title"
				value="<%=DataUtility.getStringData(dto.getTitle())%>">

		</div>

		<div class="col-sm-2">

			<input type="text" class="form-control" name="hostName"
				placeholder="Enter Host Name"
				value="<%=DataUtility.getStringData(dto.getHostName())%>">

		</div>

		<div class="col-sm-2">

			<input type="text" class="form-control" name="duration"
				placeholder="Enter Duration"
				value="<%=DataUtility.getStringData(dto.getDuration())%>">

		</div>

		<div class="col-sm-2">

			<input type="text" class="form-control" name="category"
				placeholder="Enter Category"
				value="<%=DataUtility.getStringData(dto.getCategory())%>">

		</div>

		<div class="col-sm-2">

			<input type="submit" class="btn btn-primary btn-md" name="operation"
				value="<%=PodcastListCtl.OP_SEARCH%>"> <input type="submit"
				class="btn btn-dark btn-md" name="operation"
				value="<%=PodcastListCtl.OP_RESET%>">

		</div>

	</div>
	<table class="table table-dark table-bordered table-hover">

		<thead>

			<tr style="background-color: #8C8C8C;">

				<th width="10%"><input type="checkbox" id="select_all"
					name="Select"> Select All</th>

				<th>S.NO</th>
				<th>Title</th>
				<th>Host Name</th>
				<th>Duration</th>
				<th>Category</th>
				<th>Edit</th>

			</tr>

		</thead>
		<%
			while (it.hasNext()) {

				dto = it.next();
		%>

		<tbody>

			<tr>

				<td align="center"><input type="checkbox" class="checkbox"
					name="ids" value="<%=dto.getId()%>"></td>

				<td align="center"><%=index++%></td>

				<td align="center"><%=dto.getTitle()%></td>

				<td align="center"><%=dto.getHostName()%></td>

				<td align="center"><%=dto.getDuration()%></td>

				<td align="center"><%=dto.getCategory()%></td>

				<td align="center"><a href="PodcastCtl?id=<%=dto.getId()%>">
						Edit </a></td>

			</tr>

		</tbody>

		<%
			}
		%>

	</table>
	<table width="100%">

		<tr>

			<td><input type="submit" name="operation"
				class="btn btn-secondary btn-md"
				value="<%=PodcastListCtl.OP_PREVIOUS%>"
				<%=pageNo > 1 ? "" : "disabled"%>></td>

			<td><input type="submit" name="operation"
				class="btn btn-primary btn-md" value="<%=PodcastListCtl.OP_NEW%>">

			</td>

			<td><input type="submit" name="operation"
				class="btn btn-danger btn-md" value="<%=PodcastListCtl.OP_DELETE%>">

			</td>

			<td align="right"><input type="submit" name="operation"
				class="btn btn-secondary btn-md" value="<%=PodcastListCtl.OP_NEXT%>"
				<%=(nextPageSize != 0) ? "" : "disabled"%>></td>

		</tr>

	</table>
	<center>
		<h1 class="text-primary font-weight-bold pt-3">Podcast List</h1>
	</center>

	<div style="padding-left: 48%;">

		<input type="submit" name="operation" class="btn btn-primary btn-md"
			value="<%=PodcastListCtl.OP_BACK%>">

	</div>