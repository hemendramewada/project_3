package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.SalaryDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.SalaryModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "SalaryCtl", urlPatterns = "/ctl/SalaryCtl")
public class SalaryCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;
		String op = request.getParameter("operation");

		if (OP_RESET.equalsIgnoreCase(op) || OP_CANCEL.equalsIgnoreCase(op)) {
			return pass;
		}

		  if (DataValidator.isNull(request.getParameter("salaryCode"))) {
			request.setAttribute("salaryCode", PropertyReader.getValue("error.require", "Salary Code"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("employeeName"))) {
			request.setAttribute("employeeName", PropertyReader.getValue("error.require", "Employee Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("salaryAmount"))) {
			request.setAttribute("salaryAmount", PropertyReader.getValue("error.require", "Salary Amount"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("salaryStatus"))) {
			request.setAttribute("salaryStatus", PropertyReader.getValue("error.require", "Salary Status"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		SalaryDTO dto = new SalaryDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setSalaryCode(DataUtility.getString(request.getParameter("salaryCode")));
		dto.setEmployeeName(DataUtility.getString(request.getParameter("employeeName")));
		dto.setSalaryAmount(DataUtility.getLong(request.getParameter("salaryAmount")));
		dto.setSalaryStatus(DataUtility.getString(request.getParameter("salaryStatus")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Long id = DataUtility.getLong(req.getParameter("id"));
		SalaryModelInt model = ModelFactory.getInstance().getSalaryModel();

		if (id > 0) {
			try {
				SalaryDTO dto = model.findByPK(id);
				ServletUtility.setDto(dto, req);

			} catch (DatabaseException e) {
				e.printStackTrace();
				ServletUtility.handleExceptionDBDown(e, req, resp, getView());
				return;

			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, req, resp);
				return;
			}
		}

		ServletUtility.forward(getView(), req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String op = DataUtility.getString(req.getParameter("operation"));
		SalaryModelInt model = ModelFactory.getInstance().getSalaryModel();

		if (OP_SAVE.equalsIgnoreCase(op)) {

			SalaryDTO bean = (SalaryDTO) populateDTO(req);

			try {
				model.add(bean);
				ServletUtility.setDto(bean, req);
				ServletUtility.setSuccessMessage("Salary Added Successfully !!!", req);

			} catch (DuplicateRecordException dre) {
				ServletUtility.setDto(bean, req);
				ServletUtility.setErrorMessage("Salary Code Already Exist !!!", req);

			} catch (DatabaseException de) {
				de.printStackTrace();
				ServletUtility.handleExceptionDBDown(de, req, resp, getView());
				return;

			} catch (ApplicationException ae) {
				ae.printStackTrace();
				ServletUtility.handleException(ae, req, resp);
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			SalaryDTO dto = (SalaryDTO) populateDTO(req);

			try {
				model.update(dto);
				ServletUtility.setDto(dto, req);
				ServletUtility.setSuccessMessage("Salary Updated Successfully !!!", req);

			} catch (DuplicateRecordException dre) {
				ServletUtility.setDto(dto, req);
				ServletUtility.setErrorMessage("Salary Code Already Exist !!!", req);

			} catch (DatabaseException de) {
				de.printStackTrace();
				ServletUtility.handleException(de, req, resp);
				return;

			} catch (ApplicationException ae) {
				ae.printStackTrace();
				ServletUtility.handleException(ae, req, resp);
				return;
			}

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.SALARY_CTL, req, resp);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.SALARY_LIST_CTL, req, resp);
			return;
		}

		ServletUtility.forward(getView(), req, resp);
	}

	@Override
	protected String getView() {
		return ORSView.SALARY_VIEW;
	}
}   
