package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.WarrantyDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.WarrantyModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "WarrantyCtl", urlPatterns = "/ctl/WarrantyCtl")
public class WarrantyCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;
		String op = request.getParameter("operation");

		if (OP_RESET.equalsIgnoreCase(op) || OP_CANCEL.equalsIgnoreCase(op)) {
			return pass;
		}

		if (DataValidator.isNull(request.getParameter("productName"))) {
			request.setAttribute("productName",
					PropertyReader.getValue("error.require", "Product Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("startDate"))) {
			request.setAttribute("startDate",
					PropertyReader.getValue("error.require", "Start Date"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("endDate"))) {
			request.setAttribute("endDate",
					PropertyReader.getValue("error.require", "End Date"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		WarrantyDTO dto = new WarrantyDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setProductName(DataUtility.getString(request.getParameter("productName")));
		dto.setStartDate(DataUtility.getDate(request.getParameter("startDate")));
		dto.setEndDate(DataUtility.getDate(request.getParameter("endDate")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Long id = DataUtility.getLong(req.getParameter("id"));
		WarrantyModelInt model = ModelFactory.getInstance().getWarrantyModel();

		if (id > 0) {
			try {
				WarrantyDTO dto = model.findByPK(id);
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
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String op = DataUtility.getString(req.getParameter("operation"));
		WarrantyModelInt model = ModelFactory.getInstance().getWarrantyModel();

		if (OP_SAVE.equalsIgnoreCase(op)) {

			WarrantyDTO bean = (WarrantyDTO) populateDTO(req);

			try {
				model.add(bean);
				ServletUtility.setDto(bean, req);
				ServletUtility.setSuccessMessage("Warranty Added Successfully !!!", req);

			} catch (DuplicateRecordException dre) {
				ServletUtility.setDto(bean, req);
				ServletUtility.setErrorMessage("Product Name Already Exist !!!", req);

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

			WarrantyDTO dto = (WarrantyDTO) populateDTO(req);

			try {
				model.update(dto);
				ServletUtility.setDto(dto, req);
				ServletUtility.setSuccessMessage("Warranty Updated Successfully !!!", req);

			} catch (DuplicateRecordException dre) {
				ServletUtility.setDto(dto, req);
				ServletUtility.setErrorMessage("Product Name Already Exist !!!", req);

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

			ServletUtility.redirect(ORSView.WARRANTY_CTL, req, resp);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.WARRANTY_LIST_CTL, req, resp);
			return;
		}

		ServletUtility.forward(getView(), req, resp);
	}

	@Override
	protected String getView() {
		return ORSView.WARRANTY_VIEW;
	}
}