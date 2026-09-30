package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.OrderDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.OrderModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * Order functionality controller to perform add, delete and update operation.
 *
 * @author Rajendra Singh
 */
@WebServlet(urlPatterns = { "/ctl/OrderCtl" })
public class OrderCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(OrderCtl.class);

	/**
	 * Preload data if required.
	 */
	@Override
	protected void preload(HttpServletRequest request) {

	}

	/**
	 * Validate Order data.
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		/*
		 * Order Date Validation
		 */
		if (DataValidator.isNull(request.getParameter("orderDate"))) {

			request.setAttribute("orderDate", PropertyReader.getValue("error.require", "Order Date"));

			pass = false;

		} else if (!DataValidator.isDate(request.getParameter("orderDate"))) {

			request.setAttribute("orderDate", PropertyReader.getValue("error.date", "Order Date"));

			pass = false;
		}

		/*
		 * Status Validation
		 */
		if (DataValidator.isNull(request.getParameter("status"))) {

			request.setAttribute("status", PropertyReader.getValue("error.require", "Status"));

			pass = false;
		}

		/*
		 * Customer Id Validation
		 */
		if (DataValidator.isNull(request.getParameter("customerId"))) {

			request.setAttribute("customerId", PropertyReader.getValue("error.require", "Customer Id"));

			pass = false;

		} else if (DataUtility.getLong(request.getParameter("customerId")) <= 0) {

			request.setAttribute("customerId", "Please Enter Valid Customer Id");

			pass = false;
		}

		return pass;
	}

	/**
	 * Populate DTO from request parameters.
	 */
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		OrderDTO dto = new OrderDTO();

		dto.setOrderId(DataUtility.getLong(request.getParameter("orderId")));

		dto.setOrderDate(DataUtility.getDate(request.getParameter("orderDate")));

		dto.setStatus(DataUtility.getString(request.getParameter("status")));

		dto.setCustomerId(DataUtility.getLong(request.getParameter("customerId")));

		populateBean(dto, request);

		log.debug("OrderCtl Method populateDTO Ended");

		return dto;
	}

	/**
	 * Display Order page.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("OrderCtl Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		OrderModelInt model = ModelFactory.getInstance().getOrderModel();

		long id = DataUtility.getLong(request.getParameter("orderId"));

		if (id > 0 || op != null) {

			OrderDTO dto = null;

			try {

				dto = model.findByPK(id);

				ServletUtility.setDto(dto, request);

			} catch (Exception e) {

				e.printStackTrace();

				log.error(e);

				ServletUtility.handleException(e, request, response);

				return;
			}
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("OrderCtl Method doGet Ended");
	}

	/**
	 * Perform Save, Update, Delete, Cancel and Reset operations.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("OrderCtl Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		OrderModelInt model = ModelFactory.getInstance().getOrderModel();

		long id = DataUtility.getLong(request.getParameter("orderId"));

		/*
		 * SAVE / UPDATE
		 */
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			OrderDTO dto = (OrderDTO) populateDTO(request);

			try {

				if (id > 0) {

					model.update(dto);

					ServletUtility.setSuccessMessage("Data is successfully Updated", request);

					ServletUtility.setDto(dto, request);

				} else {

					try {

						model.add(dto);

						ServletUtility.setSuccessMessage("Data is successfully saved", request);

					} catch (ApplicationException e) {

						log.error(e);

						ServletUtility.handleException(e, request, response);

						return;

					} catch (DuplicateRecordException e) {

						ServletUtility.setDto(dto, request);

						ServletUtility.setErrorMessage("Order already exists", request);
					}
				}

			} catch (ApplicationException e) {

				log.error(e);

				ServletUtility.handleException(e, request, response);

				return;

			} catch (DuplicateRecordException e) {

				ServletUtility.setDto(dto, request);

				ServletUtility.setErrorMessage("Order already exists", request);
			}

		}

		/*
		 * DELETE
		 */
		else if (OP_DELETE.equalsIgnoreCase(op)) {

			OrderDTO dto = (OrderDTO) populateDTO(request);

			try {

				model.delete(dto);

				ServletUtility.redirect(ORSView.ORDER_LIST_CTL, request, response);

				return;

			} catch (ApplicationException e) {

				log.error(e);

				ServletUtility.handleException(e, request, response);

				return;
			}
		}

		/*
		 * CANCEL
		 */
		else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.ORDER_LIST_CTL, request, response);

			return;
		}

		/*
		 * RESET
		 */
		else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.ORDER_CTL, request, response);

			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("OrderCtl Method doPost Ended");
	}

	/**
	 * Returns Order View.
	 */
	@Override
	protected String getView() {

		return ORSView.ORDER_VIEW;
	}
}