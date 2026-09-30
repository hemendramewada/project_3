package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.InventoryDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.InventoryModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "InventoryCtl", urlPatterns = "/ctl/InventoryCtl")
public class InventoryCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;
		String op = request.getParameter("operation");

		if (OP_LOG_OUT.equalsIgnoreCase(op) || OP_RESET.equalsIgnoreCase(op)) {
			return pass;
		}

		if (DataValidator.isNull(request.getParameter("supplierName"))) {
			request.setAttribute("supplierName", PropertyReader.getValue("error.require", "Supplier Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("product"))) {
			request.setAttribute("product", PropertyReader.getValue("error.require", "Product"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("quantity"))) {
			request.setAttribute("quantity", PropertyReader.getValue("error.require", "Quantity"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Purchase"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.date", "Date Of Purchase"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		InventoryDTO dto = new InventoryDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setSupplierName(DataUtility.getString(request.getParameter("supplierName")));
		dto.setProduct(DataUtility.getString(request.getParameter("product")));
		dto.setQuantity(DataUtility.getLong(request.getParameter("quantity")));
		dto.setDob(DataUtility.getDate(request.getParameter("dob")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Long id = DataUtility.getLong(req.getParameter("id"));
		InventoryModelInt model = ModelFactory.getInstance().getInventoryModel();

		if (id > 0) {
			try {
				InventoryDTO dto = model.findByPK(id);
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
		InventoryModelInt model = ModelFactory.getInstance().getInventoryModel();

		if (OP_SAVE.equalsIgnoreCase(op)) {

			InventoryDTO bean = (InventoryDTO) populateDTO(req);

			try {
				model.add(bean);
				ServletUtility.setDto(bean, req);
				ServletUtility.setSuccessMessage("Inventory Added Successfully !!!", req);

			} catch (DuplicateRecordException dre) {
				ServletUtility.setDto(bean, req);
				ServletUtility.setErrorMessage("Product Already Exist !!!", req);

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

			InventoryDTO dto = (InventoryDTO) populateDTO(req);

			try {
				model.update(dto);
				ServletUtility.setDto(dto, req);
				ServletUtility.setSuccessMessage("Inventory Updated Successfully !!!", req);

			} catch (DuplicateRecordException dre) {
				ServletUtility.setDto(dto, req);
				ServletUtility.setErrorMessage("Product Already Exist !!!", req);

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

			ServletUtility.redirect(ORSView.INVENTORY_CTL, req, resp);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.INVENTORY_LIST_CTL, req, resp);
			return;
		}

		ServletUtility.forward(getView(), req, resp);
	}

	@Override
	protected String getView() {
		return ORSView.INVENTORY_VIEW;
	}
}
