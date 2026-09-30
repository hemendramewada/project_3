package in.co.rays.project_3.controller;

/**
 * ORS View Provide Loose Coupling
 * 
 * @author Rajendra Singh
 *
 */
public interface ORSView {
	public String APP_CONTEXT = "/ORSProject-03";

	public String PAGE_FOLDER = "/jsp";

	  public String JAVA_DOC_VIEW = APP_CONTEXT + "/doc/index.html";

	public String ERROR_VIEW = PAGE_FOLDER + "/ErrorView404.jsp";

	public String MARKSHEET_VIEW = PAGE_FOLDER + "/MarksheetView.jsp";

	public String JASPER_CTL = APP_CONTEXT + "/ctl/JasperCtl";

	public String MARKSHEET_LIST_VIEW = PAGE_FOLDER + "/MarksheetListView.jsp";
	public String GET_MARKSHEET_VIEW = PAGE_FOLDER + "/GetMarksheetView.jsp";
	public String USER_VIEW = PAGE_FOLDER + "/UserView.jsp";
	public String USER_LIST_VIEW = PAGE_FOLDER + "/UserListView.jsp";
	public String COLLEGE_VIEW = PAGE_FOLDER + "/CollegeView.jsp";
	public String COLLEGE_LIST_VIEW = PAGE_FOLDER + "/CollegeListView.jsp";
	public String STUDENT_VIEW = PAGE_FOLDER + "/StudentView.jsp";
	public String STUDENT_LIST_VIEW = PAGE_FOLDER + "/StudentListView.jsp";
	public String ROLE_VIEW = PAGE_FOLDER + "/RoleView.jsp";
	public String ROLE_LIST_VIEW = PAGE_FOLDER + "/RoleListView.jsp";
	public String USER_REGISTRATION_VIEW = PAGE_FOLDER + "/UserRegistrationView.jsp";
	public String LOGIN_VIEW = PAGE_FOLDER + "/LoginView.jsp";
	public String WELCOME_VIEW = PAGE_FOLDER + "/Welcome.jsp";
	public String CHANGE_PASSWORD_VIEW = PAGE_FOLDER + "/ChangePasswordView.jsp";
	public String MY_PROFILE_VIEW = PAGE_FOLDER + "/MyProfileView.jsp";
	public String FORGET_PASSWORD_VIEW = PAGE_FOLDER + "/ForgetPasswordView.jsp";
	public String MARKSHEET_MERIT_LIST_VIEW = PAGE_FOLDER + "/MarksheetMeritListView.jsp";

	public String FACULTY_VIEW = PAGE_FOLDER + "/FacultyView.jsp";
	public String FACULTY_LIST_VIEW = PAGE_FOLDER + "/FacultyListView.jsp";
	public String COURSE_VIEW = PAGE_FOLDER + "/CourseView.jsp";
	public String COURSE_LIST_VIEW = PAGE_FOLDER + "/CourseListView.jsp";
	public String TIMETABLE_VIEW = PAGE_FOLDER + "/TimeTableView.jsp";
	public String TIMETABLE_LIST_VIEW = PAGE_FOLDER + "/TimeTableListView.jsp";
	public String SUBJECT_VIEW = PAGE_FOLDER + "/SubjectView.jsp";
	public String SUBJECT_LIST_VIEW = PAGE_FOLDER + "/SubjectListView.jsp";

	public String ERROR_CTL = APP_CONTEXT + "/ErrorCtl";

	public String MARKSHEET_CTL = APP_CONTEXT + "/ctl/MarksheetCtl";
	public String MARKSHEET_LIST_CTL = APP_CONTEXT + "/ctl/MarksheetListCtl";
	public String USER_CTL = APP_CONTEXT + "/ctl/UserCtl";
	public String USER_LIST_CTL = APP_CONTEXT + "/ctl/UserListCtl";
	public String COLLEGE_CTL = APP_CONTEXT + "/ctl/CollegeCtl";
	public String COLLEGE_LIST_CTL = APP_CONTEXT + "/ctl/CollegeListCtl";
	public String STUDENT_CTL = APP_CONTEXT + "/ctl/StudentCtl";
	public String STUDENT_LIST_CTL = APP_CONTEXT + "/ctl/StudentListCtl";
	public String ROLE_CTL = APP_CONTEXT + "/ctl/RoleCtl";
	public String ROLE_LIST_CTL = APP_CONTEXT + "/ctl/RoleListCtl";
	public String USER_REGISTRATION_CTL = APP_CONTEXT + "/UserRegistrationCtl";
	public String LOGIN_CTL = APP_CONTEXT + "/LoginCtl";
	public String WELCOME_CTL = APP_CONTEXT + "/WelcomeCtl";

	public String FACULTY_CTL = APP_CONTEXT + "/ctl/FacultyCtl";
	public String FACULTY_LIST_CTL = APP_CONTEXT + "/ctl/FacultyListCtl";
	public String COURSE_CTL = APP_CONTEXT + "/ctl/CourseCtl";
	public String COURSE_LIST_CTL = APP_CONTEXT + "/ctl/CourseListCtl";
	public String SUBJECT_CTL = APP_CONTEXT + "/ctl/SubjectCtl";
	public String SUBJECT_LIST_CTL = APP_CONTEXT + "/ctl/SubjectListCtl";
	public String TIMETABLE_CTL = APP_CONTEXT + "/ctl/TimeTableCtl";
	public String TIMETABLE_LIST_CTL = APP_CONTEXT + "/ctl/TimeTableListCtl";

	public String GET_MARKSHEET_CTL = APP_CONTEXT + "/ctl/GetMarksheetCtl";
	public String CHANGE_PASSWORD_CTL = APP_CONTEXT + "/ctl/ChangePasswordCtl";
	public String MY_PROFILE_CTL = APP_CONTEXT + "/ctl/MyProfileCtl";
	public String FORGET_PASSWORD_CTL = APP_CONTEXT + "/ForgetPasswordCtl";
	public String MARKSHEET_MERIT_LIST_CTL = APP_CONTEXT + "/ctl/MarksheetMeritListCtl";

	public String PROFILE_VIEW = PAGE_FOLDER + "/ProfileView.jsp";
	public String PROFILE_LIST_VIEW = PAGE_FOLDER + "/ProfileListView.jsp";
	public String PROFILE_CTL = APP_CONTEXT + "/ctl/ProfileCtl";
	public String PROFILE_LIST_CTL = APP_CONTEXT + "/ctl/ProfileListCtl";

	public String SESSION_VIEW = PAGE_FOLDER + "/SessionView.jsp";
	public String SESSION_LIST_VIEW = PAGE_FOLDER + "/SessionListView.jsp";
	public String SESSION_CTL = APP_CONTEXT + "/ctl/SessionCtl";
	public String SESSION_LIST_CTL = APP_CONTEXT + "/ctl/SessionListCtl";

	public String LANGUAGE_VIEW = PAGE_FOLDER + "/LanguageView.jsp";
	public String LANGUAGE_LIST_VIEW = PAGE_FOLDER + "/LanguageListView.jsp";
	public String LANGUAGE_CTL = APP_CONTEXT + "/ctl/LanguageCtl";
	public String LANGUAGE_LIST_CTL = APP_CONTEXT + "/ctl/LanguageListCtl";

	public String ANNOUNCEMENT_VIEW = PAGE_FOLDER + "/AnnouncementView.jsp";
	public String ANNOUNCEMENT_LIST_VIEW = PAGE_FOLDER + "/AnnouncementListView.jsp";
	public String ANNOUNCEMENT_CTL = APP_CONTEXT + "/ctl/AnnouncementCtl";
	public String ANNOUNCEMENT_LIST_CTL = APP_CONTEXT + "/ctl/AnnouncementListCtl";

	public String PLACEMENT_VIEW = PAGE_FOLDER + "/PlacementView.jsp";
	public String PLACEMENT_LIST_VIEW = PAGE_FOLDER + "/PlacementListView.jsp";
	public String PLACEMENT_CTL = APP_CONTEXT + "/ctl/PlacementCtl";
	public String PLACEMENT_LIST_CTL = APP_CONTEXT + "/ctl/PlacementListCtl";

	public String HOSPITAL_VIEW = PAGE_FOLDER + "/HospitalView.jsp";
	public String HOSPITAL_LIST_VIEW = PAGE_FOLDER + "/HospitalListView.jsp";
	public String HOSPITAL_CTL = APP_CONTEXT + "/ctl/HospitalCtl";
	public String HOSPITAL_LIST_CTL = APP_CONTEXT + "/ctl/HospitalListCtl";

	public String DONATION_VIEW = PAGE_FOLDER + "/DonationView.jsp";
	public String DONATION_LIST_VIEW = PAGE_FOLDER + "/DonationListView.jsp";
	public String DONATION_CTL = APP_CONTEXT + "/ctl/DonationCtl";
	public String DONATION_LIST_CTL = APP_CONTEXT + "/ctl/DonationListCtl";

	public String WARRANTY_VIEW = PAGE_FOLDER + "/WarrantyView.jsp";
	public String WARRANTY_LIST_VIEW = PAGE_FOLDER + "/WarrantyListView.jsp";
	public String WARRANTY_CTL = APP_CONTEXT + "/ctl/WarrantyCtl";
	public String WARRANTY_LIST_CTL = APP_CONTEXT + "/ctl/WarrantyListCtl";

	public String PORTFOLIO_VIEW = PAGE_FOLDER + "/PortfolioView.jsp";
	public String PORTFOLIO_LIST_VIEW = PAGE_FOLDER + "/PortfolioListView.jsp";
	public String PORTFOLIO_CTL = APP_CONTEXT + "/ctl/PortfolioCtl";
	public String PORTFOLIO_LIST_CTL = APP_CONTEXT + "/ctl/PortfolioListCtl";

	public String SECRET_VIEW = PAGE_FOLDER + "/SecretView.jsp";
	public String SECRET_LIST_VIEW = PAGE_FOLDER + "/SecretListView.jsp";
	public String SECRET_CTL = APP_CONTEXT + "/ctl/SecretCtl";
	public String SECRET_LIST_CTL = APP_CONTEXT + "/ctl/SecretListCtl";

	public String LISTENER_VIEW = PAGE_FOLDER + "/ListenerView.jsp";
	public String LISTENER_LIST_VIEW = PAGE_FOLDER + "/ListenerListView.jsp";
	public String LISTENER_CTL = APP_CONTEXT + "/ctl/ListenerCtl";
	public String LISTENER_LIST_CTL = APP_CONTEXT + "/ctl/ListenerListCtl";

	public String FREELANCER_VIEW = PAGE_FOLDER + "/FreelancerView.jsp";
	public String FREELANCER_LIST_VIEW = PAGE_FOLDER + "/FreelancerListView.jsp";
	public String FREELANCER_CTL = APP_CONTEXT + "/ctl/FreelancerCtl";
	public String FREELANCER_LIST_CTL = APP_CONTEXT + "/ctl/FreelancerListCtl";

	public String PODCAST_VIEW = PAGE_FOLDER + "/PodcastView.jsp";
	public String PODCAST_LIST_VIEW = PAGE_FOLDER + "/PodcastListView.jsp";
	public String PODCAST_CTL = APP_CONTEXT + "/ctl/PodcastCtl";
	public String PODCAST_LIST_CTL = APP_CONTEXT + "/ctl/PodcastListCtl";

	public String VOICE_COMMAND_VIEW = PAGE_FOLDER + "/VoiceCommandView.jsp";
	public String VOICE_COMMAND_LIST_VIEW = PAGE_FOLDER + "/VoiceCommandListView.jsp";
	public String VOICE_COMMAND_CTL = APP_CONTEXT + "/ctl/VoiceCommandCtl";
	public String VOICE_COMMAND_LIST_CTL = APP_CONTEXT + "/ctl/VoiceCommandListCtl";

	public String RESULT_VIEW = PAGE_FOLDER + "/ResultView.jsp";
	public String RESULT_LIST_VIEW = PAGE_FOLDER + "/ResultListView.jsp";
	public String RESULT_CTL = APP_CONTEXT + "/ctl/ResultCtl";
	public String RESULT_LIST_CTL = APP_CONTEXT + "/ctl/ResultListCtl";

	public String INVENTORY_VIEW = PAGE_FOLDER + "/InventoryView.jsp";
	public String INVENTORY_LIST_VIEW = PAGE_FOLDER + "/InventoryListView.jsp";
	public String INVENTORY_CTL = APP_CONTEXT + "/ctl/InventoryCtl";
	public String INVENTORY_LIST_CTL = APP_CONTEXT + "/ctl/InventoryListCtl";

	public String SMARTLIGHT_VIEW = PAGE_FOLDER + "/SmartLightView.jsp";
	public String SMARTLIGHT_LIST_VIEW = PAGE_FOLDER + "/SmartLightListView.jsp";
	public String SMARTLIGHT_CTL = APP_CONTEXT + "/ctl/SmartLightCtl";
	public String SMARTLIGHT_LIST_CTL = APP_CONTEXT + "/ctl/SmartLightListCtl";

	public String CUSTOMER_VIEW = PAGE_FOLDER + "/CustomerView.jsp";
	public String CUSTOMER_LIST_VIEW = PAGE_FOLDER + "/CustomerListView.jsp";
	public String CUSTOMER_CTL = APP_CONTEXT + "/ctl/CustomerCtl";
	public String CUSTOMER_LIST_CTL = APP_CONTEXT + "/ctl/CustomerListCtl";

	public String ORDER_VIEW = PAGE_FOLDER + "/OrderView.jsp";
	public String ORDER_LIST_VIEW = PAGE_FOLDER + "/OrderListView.jsp";
	public String ORDER_CTL = APP_CONTEXT + "/ctl/OrderCtl";
	public String ORDER_LIST_CTL = APP_CONTEXT + "/ctl/OrderListCtl";

	public String SALARY_VIEW = PAGE_FOLDER + "/SalaryView.jsp";
	public String SALARY_LIST_VIEW = PAGE_FOLDER + "/SalaryListView.jsp";
	public String SALARY_CTL = APP_CONTEXT + "/ctl/SalaryCtl";
	public String SALARY_LIST_CTL = APP_CONTEXT + "/ctl/SalaryListCtl";

	public String VEHICLE_VIEW = PAGE_FOLDER + "/VehicleView.jsp";
	public String VEHICLE_LIST_VIEW = PAGE_FOLDER + "/VehicleListView.jsp";
	public String VEHICLE_CTL = APP_CONTEXT + "/ctl/VehicleCtl";
	public String VEHICLE_LIST_CTL = APP_CONTEXT + "/ctl/VehicleListCtl";

	public String BUGTRACKER_VIEW = PAGE_FOLDER + "/BugTrackerView.jsp";
	public String BUGTRACKER_LIST_VIEW = PAGE_FOLDER + "/BugTrackerListView.jsp";
	public String BUGTRACKER_CTL = APP_CONTEXT + "/ctl/BugTrackerCtl";
	public String BUGTRACKER_LIST_CTL = APP_CONTEXT + "/ctl/BugTrackerListCtl";

	public String EVENT_VIEW = PAGE_FOLDER + "/EventView.jsp";
	public String EVENT_LIST_VIEW = PAGE_FOLDER + "/EventListView.jsp";
	public String EVENT_CTL = APP_CONTEXT + "/ctl/EventCtl";
	public String EVENT_LIST_CTL = APP_CONTEXT + "/ctl/EventListCtl";

	public String PRODUCT_VIEW = PAGE_FOLDER + "/ProductView.jsp";
	public String PRODUCT_LIST_VIEW = PAGE_FOLDER + "/ProductListView.jsp";
	public String PRODUCT_CTL = APP_CONTEXT + "/ctl/ProductCtl";
	public String PRODUCT_LIST_CTL = APP_CONTEXT + "/ctl/ProductListCtl";

	public String SMART_PARKING_VIEW = PAGE_FOLDER + "/SmartParkingView.jsp";
	public String SMART_PARKING_LIST_VIEW = PAGE_FOLDER + "/SmartParkingListView.jsp";
	public String SMART_PARKING_CTL = APP_CONTEXT + "/ctl/SmartParkingCtl";
	public String SMART_PARKING_LIST_CTL = APP_CONTEXT + "/ctl/SmartParkingListCtl";

}
