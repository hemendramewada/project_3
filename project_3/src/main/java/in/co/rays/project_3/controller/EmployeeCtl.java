package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.EmployeeDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.EmployeeModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/EmployeeCtl" })
public class EmployeeCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(EmployeeCtl.class);

	// ================= VALIDATION =================
	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("EmployeeCtl validate started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("employeeName"))) {
			request.setAttribute("employeeName",
					PropertyReader.getValue("error.require", "Employee Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("employeeName"))) {
			request.setAttribute("employeeName",
					PropertyReader.getValue("error.name", "Employee Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName",
					PropertyReader.getValue("error.require", "Last Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("lastName"))) {
			request.setAttribute("lastName",
					PropertyReader.getValue("error.name", "Last Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("department"))) {
			request.setAttribute("department",
					PropertyReader.getValue("error.require", "Department"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("department"))) {
			request.setAttribute("department",
					PropertyReader.getValue("error.name", "Department"));
			pass = false;
		}

		String dob = request.getParameter("dob");

		if (DataValidator.isNull(dob)) {
			request.setAttribute("dob",
					PropertyReader.getValue("error.require", "Date Of Birth"));
			pass = false;
		} else if (!DataValidator.isDate(dob)) {
			request.setAttribute("dob",
					PropertyReader.getValue("error.date", "Date Of Birth"));
			pass = false;
		}

		log.debug("EmployeeCtl validate ended");
		return pass;
	}

	// ================= POPULATE DTO =================
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		log.debug("EmployeeCtl populateDTO started");

		EmployeeDTO dto = new EmployeeDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setEmployeeName(
				DataUtility.getString(request.getParameter("employeeName")));

		dto.setLastName(
				DataUtility.getString(request.getParameter("lastName")));

		dto.setDepartment(
				DataUtility.getString(request.getParameter("department")));

		dto.setDob(
				DataUtility.getDate(request.getParameter("dob")));

		populateBean(dto, request);

		log.debug("EmployeeCtl populateDTO ended");
		return dto;
	}

	// ================= DO GET =================
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));

		EmployeeModelInt model =
				ModelFactory.getInstance().getEmployeeModel();

		if (id > 0) {
			try {
				EmployeeDTO dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	// ================= DO POST =================
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(
				request.getParameter("operation"));

		EmployeeModelInt model =
				ModelFactory.getInstance().getEmployeeModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)
				|| OP_UPDATE.equalsIgnoreCase(op)) {

			EmployeeDTO dto =
					(EmployeeDTO) populateDTO(request);

			try {

				if (id > 0) {
					model.update(dto);
					ServletUtility.setSuccessMessage(
							"Employee Updated Successfully", request);
				} else {
					model.add(dto);
					ServletUtility.setSuccessMessage(
							"Employee Added Successfully", request);
				}

				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage(
						"Employee already exists", request);
			}
		}

		else if (OP_DELETE.equalsIgnoreCase(op)) {

			EmployeeDTO dto =
					(EmployeeDTO) populateDTO(request);

			try {
				model.delete(dto);
				ServletUtility.redirect(
						ORSView.EMPLOYEE_LIST_CTL,
						request, response);
				return;

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e,
						request, response);
				return;
			}
		}

		else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(
					ORSView.EMPLOYEE_LIST_CTL,
					request, response);
			return;
		}

		else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(
					ORSView.EMPLOYEE_CTL,
					request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.EMPLOYEE_VIEW;
	}
}