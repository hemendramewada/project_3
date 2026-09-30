package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.CustomerDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.CustomerModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "CustomerCtl", urlPatterns = "/ctl/CustomerCtl")
public class CustomerCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request) {

		Map<String, String> map = new HashMap<>();
		map.put("High", "High");
		map.put("Medium", "Medium");
		map.put("Low", "Low");

		request.setAttribute("importanceList", map);
	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;
		String op = request.getParameter("operation");

		if (OP_RESET.equalsIgnoreCase(op) || OP_CANCEL.equalsIgnoreCase(op)) {
			return pass;
		}

		if (DataValidator.isNull(request.getParameter("clientName"))) {
			request.setAttribute("clientName", PropertyReader.getValue("error.require", "Client Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("location"))) {
			request.setAttribute("location", PropertyReader.getValue("error.require", "Location"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("contactNumber"))) {
			request.setAttribute("contactNumber", PropertyReader.getValue("error.require", "Contact Number"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("importance"))) {
			request.setAttribute("importance", PropertyReader.getValue("error.require", "Importance"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		CustomerDTO dto = new CustomerDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setClientName(DataUtility.getString(request.getParameter("clientName")));
		dto.setLocation(DataUtility.getString(request.getParameter("location")));
		dto.setContactNumber(DataUtility.getString(request.getParameter("contactNumber")));
		dto.setImportance(DataUtility.getString(request.getParameter("importance")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Long id = DataUtility.getLong(req.getParameter("id"));
		CustomerModelInt model = ModelFactory.getInstance().getCustomerModel();

		if (id > 0) {
			try {
				CustomerDTO dto = model.findByPK(id);
				ServletUtility.setDto(dto, req);

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
		CustomerModelInt model = ModelFactory.getInstance().getCustomerModel();

		if (OP_SAVE.equalsIgnoreCase(op)) {

			CustomerDTO dto = (CustomerDTO) populateDTO(req);

			try {
				model.add(dto);

				ServletUtility.setDto(dto, req);
				ServletUtility.setSuccessMessage("Customer Added Successfully !!!", req);

			} catch (DuplicateRecordException dre) {

				ServletUtility.setDto(dto, req);
				ServletUtility.setErrorMessage("Customer Already Exist !!!", req);

			} catch (ApplicationException ae) {

				ae.printStackTrace();
				ServletUtility.handleException(ae, req, resp);
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			CustomerDTO dto = (CustomerDTO) populateDTO(req);

			try {
				model.update(dto);

				ServletUtility.setDto(dto, req);
				ServletUtility.setSuccessMessage("Customer Updated Successfully !!!", req);

			} catch (DuplicateRecordException dre) {

				ServletUtility.setDto(dto, req);
				ServletUtility.setErrorMessage("Customer Already Exist !!!", req);

			} catch (ApplicationException ae) {

				ae.printStackTrace();
				ServletUtility.handleException(ae, req, resp);
				return;
			}

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.CUSTOMER_CTL, req, resp);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.CUSTOMER_LIST_CTL, req, resp);
			return;
		}

		ServletUtility.forward(getView(), req, resp);
	}

	@Override
	protected String getView() {
		return ORSView.CUSTOMER_VIEW;
	}
}
