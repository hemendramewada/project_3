<%@page import="in.co.rays.project_3.controller.LoginCtl"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@page import="in.co.rays.project_3.dto.RoleDTO"%>
<%@page import="in.co.rays.project_3.dto.UserDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Header</title>

<link rel="stylesheet"
	  href="https://use.fontawesome.com/releases/v5.6.3/css/all.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>


<style>
.aj {
	background-image: linear-gradient(to bottom right, grey, black);
}

.student-nav-fix .navbar-nav {
	align-items: center !important;
}

.student-nav-fix .dropdown-menu a {
	color: #000 !important;
}

.student-nav-fix .nav-link {
	color: #fff !important;
}
</style>
</head>

<body>

	<%
		UserDTO userDto = (UserDTO) session.getAttribute("user");
		boolean userLoggedIn = userDto != null;

		String welcomeMsg = "Hi, ";
		if (userLoggedIn) {
			String role = (String) session.getAttribute("role");
			welcomeMsg += userDto.getFirstName() + " (" + role + ")";
		} else {
			welcomeMsg += "Guest";
		}
	%>

	<nav
		class="navbar navbar-expand-lg fixed-top aj 
    <%if (userLoggedIn && userDto.getRoleId() == RoleDTO.STUDENT) {%>
        student-nav-fix
    <%}%>">

		<a class="navbar-brand" href="<%=ORSView.WELCOME_CTL%>"> <img
			src="<%=ORSView.APP_CONTEXT%>/img/custom.png" width="190px"
			height="50px">
		</a>

		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarNav" aria-controls="navbarNav"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"> <i class="fa fa-bars"
				style="color: #fff; font-size: 28px;"></i>
			</span>
		</button>

		<div class="collapse navbar-collapse" id="navbarNav">
			<ul class="navbar-nav ml-auto">

				<%
					if (userLoggedIn) {
				%>

				<%
					if (userDto.getRoleId() == RoleDTO.STUDENT) {
				%>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">
						<span style="color: white;">Marksheet</span>
				</a>
					<div class="dropdown-menu">
						<a class="dropdown-item"
							href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>"> <i
							class="fa fa-file-alt"></i> Marksheet Merit List
						</a>
					</div></li>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">
						<span style="color: white;">User</span>
				</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.MY_PROFILE_CTL%>"> <i
							class="fa fa-user-tie"></i> My Profile
						</a> <a class="dropdown-item" href="<%=ORSView.CHANGE_PASSWORD_CTL%>">
							<i class="fa fa-edit"></i> Change Password
						</a>
					</div></li>

				<%
					} else if (userDto.getRoleId() == RoleDTO.ADMIN) {
				%>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">User</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.USER_CTL%>"><i
							class="fa fa-user-circle"></i>Add User</a> <a class="dropdown-item"
							href="<%=ORSView.USER_LIST_CTL%>"><i
							class="fa fa-user-friends"></i>User List</a>
					</div></li>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">Marksheet</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.MARKSHEET_CTL%>"><i
							class="fa fa-file"></i>Add Marksheet</a> <a class="dropdown-item"
							href="<%=ORSView.MARKSHEET_LIST_CTL%>"><i class="fa fa-paste"></i>Marksheet
							List</a> <a class="dropdown-item"
							href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>"><i
							class="fa fa-file-alt"></i>Marksheet Merit List</a> <a
							class="dropdown-item" href="<%=ORSView.GET_MARKSHEET_CTL%>"><i
							class="fa fa-copy"></i>Get Marksheet</a>
					</div></li>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">Role</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.ROLE_CTL%>"><i
							class="fa fa-user-tie"></i>Add Role</a> <a class="dropdown-item"
							href="<%=ORSView.ROLE_LIST_CTL%>"><i
							class="fa fa-user-friends"></i>Role List</a>
					</div></li>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">College</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.COLLEGE_CTL%>"><i
							class="fa fa-university"></i>Add College</a> <a class="dropdown-item"
							href="<%=ORSView.COLLEGE_LIST_CTL%>"><i
							class="fa fa-building"></i>College List</a>
					</div></li>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">Product</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.PRODUCT_CTL%>"><i
							class="fa fa-shopping-cart"></i>Add Product</a> <a
							class="dropdown-item" href="<%=ORSView.PRODUCT_LIST_CTL%>"><i
							class="fa fa-list"></i>Product List</a>
					</div></li> ```


				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">Course</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.COURSE_CTL%>"><i
							class="fa fa-book-open"></i>Add Course</a> <a class="dropdown-item"
							href="<%=ORSView.COURSE_LIST_CTL%>"><i
							class="fa fa-sort-amount-down"></i>Course List</a>
					</div></li>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">Student</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.STUDENT_CTL%>"><i
							class="fa fa-user-circle"></i>Add Student</a> <a
							class="dropdown-item" href="<%=ORSView.STUDENT_LIST_CTL%>"><i
							class="fa fa-users"></i>Student List</a>
					</div></li>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">Faculty</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.FACULTY_CTL%>"><i
							class="fa fa-user-tie"></i>Add Faculty</a> <a class="dropdown-item"
							href="<%=ORSView.FACULTY_LIST_CTL%>"><i class="fa fa-users"></i>Faculty
							List</a>
					</div></li>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">Time Table</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.TIMETABLE_CTL%>"><i
							class="fa fa-clock"></i>Add TimeTable</a> <a class="dropdown-item"
							href="<%=ORSView.TIMETABLE_LIST_CTL%>"><i class="fa fa-clock"></i>TimeTable
							List</a>
					</div></li>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">Subject</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.SUBJECT_CTL%>"><i
							class="fa fa-calculator"></i>Add Subject</a> <a class="dropdown-item"
							href="<%=ORSView.SUBJECT_LIST_CTL%>"><i
							class="fa fa-sort-amount-down"></i>Subject List</a>
					</div></li>


				<%-- <li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;"> Order </a>

					<div class="dropdown-menu">

						<a class="dropdown-item" href="<%=ORSView.ORDER_CTL%>"> <i
							class="fa fa-shopping-cart"></i>Add Order
						</a> <a class="dropdown-item" href="<%=ORSView.ORDER_LIST_CTL%>">
							<i class="fa fa-list"></i>Order List
						</a>

					</div></li> --%>


				<%-- 	<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">Secret</a>

					<div class="dropdown-menu">

						<a class="dropdown-item" href="<%=ORSView.SECRET_CTL%>"> <i
							class="fa fa-key"></i> Add Secret
						</a> <a class="dropdown-item" href="<%=ORSView.SECRET_LIST_CTL%>">
							<i class="fa fa-list"></i> Secret List
						</a>

					</div></li> --%>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">Customer</a>

					<div class="dropdown-menu">

						<a class="dropdown-item" href="<%=ORSView.CUSTOMER_CTL%>"> <i
							class="fa fa-user"></i> Add Customer
						</a> <a class="dropdown-item" href="<%=ORSView.CUSTOMER_LIST_CTL%>">
							<i class="fa fa-list"></i> Customer List
						</a>

					</div></li>
				<%-- 
				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;"> Freelancer </a>

					<div class="dropdown-menu">

						<a class="dropdown-item" href="<%=ORSView.FREELANCER_CTL%>"> <i
							class="fa fa-user"></i> Add Freelancer

						</a> <a class="dropdown-item" href="<%=ORSView.FREELANCER_LIST_CTL%>">

							<i class="fa fa-list"></i> Freelancer List

						</a>

					</div></li> --%>

			<%-- 	<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;"> Bug Tracker </a>

					<div class="dropdown-menu">

						<a class="dropdown-item" href="<%=ORSView.BUGTRACKER_CTL%>"> <i
							class="fa fa-bug"></i> Add Bug Tracker

						</a> <a class="dropdown-item" href="<%=ORSView.BUGTRACKER_LIST_CTL%>">

							<i class="fa fa-list"></i> Bug Tracker List

						</a>

					</div></li> --%>

				<%-- <li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;"> Smart Parking </a>

					<div class="dropdown-menu">

						<a class="dropdown-item" href="<%=ORSView.SMART_PARKING_CTL%>">
							<i class="fa fa-car"></i> Add Smart Parking
						</a> <a class="dropdown-item"
							href="<%=ORSView.SMART_PARKING_LIST_CTL%>"> <i
							class="fa fa-list"></i> Smart Parking List
						</a>

					</div></li> --%>
				<%-- <li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;"> Podcast </a>

					<div class="dropdown-menu">

						<a class="dropdown-item" href="<%=ORSView.PODCAST_CTL%>"> <i
							class="fa fa-microphone"></i> Add Podcast
						</a> <a class="dropdown-item" href="<%=ORSView.PODCAST_LIST_CTL%>">
							<i class="fa fa-list"></i> Podcast List
						</a>

					</div></li>
 --%>
				<%-- 	<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;"> Voice Command </a>

					<div class="dropdown-menu">

						<a class="dropdown-item" href="<%=ORSView.VOICE_COMMAND_CTL%>">

							<i class="fa fa-microphone"></i> Add Voice Command

						</a> <a class="dropdown-item"
							href="<%=ORSView.VOICE_COMMAND_LIST_CTL%>"> <i
							class="fa fa-list"></i> Voice Command List

						</a>

					</div></li> --%>
				<%-- <li class="nav-item dropdown px-1"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;"> Result </a>
					<div class="dropdown-menu">

						<a class="dropdown-item" href="<%=ORSView.RESULT_CTL%>"> <i
							class="fa fa-plus mr-2"></i>Add Result
						</a> <a class="dropdown-item" href="<%=ORSView.RESULT_LIST_CTL%>">
							<i class="fa fa-list mr-2"></i>Result List
						</a>
						
					</div></li>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;"> Inventory </a>

					<div class="dropdown-menu">

						<a class="dropdown-item" href="<%=ORSView.INVENTORY_CTL%>"> <i
							class="fa fa-archive"></i> Add Inventory
						</a> <a class="dropdown-item" href="<%=ORSView.INVENTORY_LIST_CTL%>">
							<i class="fa fa-list"></i> Inventory List
						</a>

					</div></li>




				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;"> Vehicle </a>

					<div class="dropdown-menu">

						<a class="dropdown-item" href="<%=ORSView.VEHICLE_CTL%>"> <i
							class="fa fa-car"></i> Add Vehicle
						</a> <a class="dropdown-item" href="<%=ORSView.VEHICLE_LIST_CTL%>">
							<i class="fa fa-list"></i> Vehicle List
						</a>

					</div></li>
 --%>
						<%-- 		<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
					style="color: white;">Product</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.PRODUCT_CTL%>"><i
							class="fa fa-file"></i>Add Product</a> <a class="dropdown-item"
							href="<%=ORSView.PRODUCT_LIST_CTL%>"><i class="fa fa-paste"></i>Product
							List</a>
					</div></li>
				<!-- Profile -->
				<li class="nav-item dropdown px-1"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">Profile</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.PROFILE_CTL%>"><i
							class="fa fa-user-plus mr-2"></i>Add Profile</a> <a
							class="dropdown-item" href="<%=ORSView.PROFILE_LIST_CTL%>"><i
							class="fa fa-list mr-2"></i>Profile List</a>
					</div></li>
 --%>
						<%--<li class="nav-item dropdown px-1"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">
						Inventory </a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.INVENTORY_CTL%>"> <i
							class="fa fa-plus mr-2"></i>Add Inventory
						</a> <a class="dropdown-item" href="<%=ORSView.INVENTORY_LIST_CTL%>">
							<i class="fa fa-list mr-2"></i>Inventory List
						</a>
					</div></li>
				</li>
				 <li class="nav-item dropdown px-1"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">
						Session </a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.SESSION_CTL%>"> <i
							class="fa fa-plus mr-2"></i>Add Session
						</a> <a class="dropdown-item" href="<%=ORSView.SESSION_LIST_CTL%>">
							<i class="fa fa-list mr-2"></i>Session List
						</a>
					</div></li>
				<li class="nav-item dropdown px-1"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">
						Language </a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.LANGUAGE_CTL%>"> <i
							class="fa fa-plus mr-2"></i>Add Language
						</a> <a class="dropdown-item" href="<%=ORSView.LANGUAGE_LIST_CTL%>">
							<i class="fa fa-list mr-2"></i>Language List
						</a>
					</div></li> --%>
						<%-- <li class="nav-item dropdown px-1"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">
						Announcement </a>
					<div class="dropdown-menu">
						<a class="dropdown-item"
							href="http://localhost:8080/ORSProject-03/ctl/AnnouncementCtl">
							<i class="fa fa-plus mr-2"></i>Add Announcement
						</a> <a class="dropdown-item"
							href="<%=ORSView.ANNOUNCEMENT_LIST_CTL%>"> <i
							class="fa fa-list mr-2"></i>Announcement List
						</a>
					</div></li>
				
				<li class="nav-item dropdown px-1"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">
						Placement </a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=ORSView.PLACEMENT_CTL%>"> <i
							class="fa fa-plus mr-2"></i>Add Placement
						</a> <a class="dropdown-item" href="<%=ORSView.PLACEMENT_LIST_CTL%>">
							<i class="fa fa-list mr-2"></i>Placement List
						</a>

					</div></li>
				<li class="nav-item dropdown px-1"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">
						Hospital </a>

					<div class="dropdown-menu">

						<a class="dropdown-item" href="<%=ORSView.HOSPITAL_CTL%>"> <i
							class="fa fa-plus mr-2"></i>Add Hospital
						</a> <a class="dropdown-item" href="<%=ORSView.HOSPITAL_LIST_CTL%>">
							<i class="fa fa-list mr-2"></i>Hospital List
						</a>

					</div></li>
				
				

 --%>
						<%-- <li class="nav-item dropdown px-1"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">
						Donation </a>

					<div class="dropdown-menu">

						<a class="dropdown-item" href="<%=ORSView.DONATION_CTL%>"> <i
							class="fa fa-plus mr-2"></i>Add Donation
						</a> <a class="dropdown-item" href="<%=ORSView.DONATION_LIST_CTL%>">
							<i class="fa fa-list mr-2"></i>Donation List
						</a>

					</div></li> --%>
						<li class="nav-item dropdown px-1"><a
							class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
							style="color: white;"> Salary </a>

							<div class="dropdown-menu">

								<a class="dropdown-item" href="<%=ORSView.SALARY_CTL%>"> <i
									class="fa fa-plus mr-2"></i>Add Salary
								</a> <a class="dropdown-item" href="<%=ORSView.SALARY_LIST_CTL%>">
									<i class="fa fa-list mr-2"></i>Salary List
								</a>

							</div></li>

						<li class="nav-item dropdown px-1"><a
							class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
							style="color: white;"> Event </a>

							<div class="dropdown-menu">

								<a class="dropdown-item" href="<%=ORSView.EVENT_CTL%>"> <i
									class="fa fa-plus mr-2"></i>Add Event
								</a> <a class="dropdown-item" href="<%=ORSView.EVENT_LIST_CTL%>">
									<i class="fa fa-list mr-2"></i>Event List
								</a>

							</div></li>

						<%-- <li class="nav-item dropdown px-1"><a
					class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">
						Warranty </a>

					<div class="dropdown-menu">

						<a class="dropdown-item" href="<%=ORSView.WARRANTY_CTL%>"> <i
							class="fa fa-plus mr-2"></i>Add Warranty
						</a> <a class="dropdown-item" href="<%=ORSView.WARRANTY_LIST_CTL%>">
							<i class="fa fa-list mr-2"></i>Warranty List
						</a>

					</div></li> --%>

						<li class="nav-item dropdown"><a
							class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"
							style="color: white;">Listener</a>

							<div class="dropdown-menu">

								<a class="dropdown-item" href="<%=ORSView.LISTENER_CTL%>"> <i
									class="fa fa-user"></i> Add Listener
								</a> <a class="dropdown-item" href="<%=ORSView.LISTENER_LIST_CTL%>">
									<i class="fa fa-list"></i> Listener List
								</a>

							</div></li>

						<%-- <li class="nav-item dropdown px-1"><a
							class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">
								Vehicle </a>

							<div class="dropdown-menu">

								<a class="dropdown-item" href="<%=ORSView.VEHICLE_CTL%>"> <i
									class="fa fa-plus mr-2"></i>Add Vehicle
								</a> <a class="dropdown-item" href="<%=ORSView.VEHICLE_LIST_CTL%>">
									<i class="fa fa-list mr-2"></i>Vehicle List
								</a>

							</div></li> --%>


						<%
							}
						%>

						<%
							}
						%>

						<!-- ✅ WELCOME DROPDOWN -->
						<li class="nav-item dropdown ml-3"><a
							class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">
								<span style="color: white;"><%=welcomeMsg%></span>
						</a>
							<div class="dropdown-menu dropdown-menu-right">
								<%
									if (userLoggedIn) {
								%>
								<a class="dropdown-item"
									href="<%=ORSView.LOGIN_CTL%>?operation=<%=LoginCtl.OP_LOG_OUT%>">
									<i class="fa fa-sign-out-alt"></i> Logout
								</a> <a class="dropdown-item" href="<%=ORSView.MY_PROFILE_CTL%>">
									<i class="fa fa-user-tie"></i> My Profile
								</a> <a class="dropdown-item"
									href="<%=ORSView.CHANGE_PASSWORD_CTL%>"> <i
									class="fa fa-edit"></i> Change Password
								</a> <a class="dropdown-item" target="blank"
									href="<%=ORSView.JAVA_DOC_VIEW%>"> <i class="fa fa-clone"></i>
									Java Doc
								</a>
								<%
									} else {
								%>
								<a class="dropdown-item" href="<%=ORSView.LOGIN_CTL%>"> <i
									class="fa fa-sign-in-alt"></i> Login
								</a> <a class="dropdown-item"
									href="<%=ORSView.USER_REGISTRATION_CTL%>"> <i
									class="fa fa-registered"></i> User Registration
								</a>
								<%
									}
								%>
							</div></li>
			</ul>
		</div>
	</nav>

</body>
</html>
