package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.ResultDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.ResultModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "ResultCtl", urlPatterns = "/ctl/ResultCtl")
public class ResultCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;
		String op = request.getParameter("operation");

		if (OP_CANCEL.equalsIgnoreCase(op) || OP_RESET.equalsIgnoreCase(op)) {
			return pass;
		}

		if (DataValidator.isNull(request.getParameter("resultCode"))) {
			request.setAttribute("resultCode", PropertyReader.getValue("error.require", "Result Code"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("studentName"))) {
			request.setAttribute("studentName", PropertyReader.getValue("error.require", "Student Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("marks"))) {
			request.setAttribute("marks", PropertyReader.getValue("error.require", "Marks"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("grade"))) {
			request.setAttribute("grade", PropertyReader.getValue("error.require", "Grade"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		ResultDTO dto = new ResultDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setResultCode(DataUtility.getString(request.getParameter("resultCode")));
		dto.setStudentName(DataUtility.getString(request.getParameter("studentName")));
		dto.setMarks(DataUtility.getInt(request.getParameter("marks")));
		dto.setGrade(DataUtility.getString(request.getParameter("grade")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Long id = DataUtility.getLong(req.getParameter("id"));
		ResultModelInt model = ModelFactory.getInstance().getResultModel();

		if (id > 0) {
			try {
				ResultDTO dto = model.findByPK(id);
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
		ResultModelInt model = ModelFactory.getInstance().getResultModel();

		if (OP_SAVE.equalsIgnoreCase(op)) {

			ResultDTO bean = (ResultDTO) populateDTO(req);

			try {
				model.add(bean);
				ServletUtility.setDto(bean, req);
				ServletUtility.setSuccessMessage("Result Added Successfully !!!", req);

			} catch (DuplicateRecordException dre) {
				ServletUtility.setDto(bean, req);
				ServletUtility.setErrorMessage("Result Code Already Exist !!!", req);

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

			ResultDTO dto = (ResultDTO) populateDTO(req);

			try {
				model.update(dto);
				ServletUtility.setDto(dto, req);
				ServletUtility.setSuccessMessage("Result Updated Successfully !!!", req);

			} catch (DuplicateRecordException dre) {
				ServletUtility.setDto(dto, req);
				ServletUtility.setErrorMessage("Result Code Already Exist !!!", req);

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

			ServletUtility.redirect(ORSView.RESULT_CTL, req, resp);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.RESULT_LIST_CTL, req, resp);
			return;
		}

		ServletUtility.forward(getView(), req, resp);
	}

	@Override
	protected String getView() {
		return ORSView.RESULT_VIEW;
	}
}