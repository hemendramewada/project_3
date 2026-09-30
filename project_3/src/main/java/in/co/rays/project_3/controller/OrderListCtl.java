
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
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.OrderModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * Order List functionality controller. To perform Search and List operation.
 *
 * @author Rajendra Singh
 */
@WebServlet(name = "OrderListCtl", urlPatterns = { "/ctl/OrderListCtl" })
public class OrderListCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(OrderListCtl.class);

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		OrderDTO dto = new OrderDTO();

		dto.setOrderId(DataUtility.getLong(request.getParameter("orderId")));

		dto.setOrderDate(DataUtility.getDate(request.getParameter("orderDate")));

		dto.setStatus(DataUtility.getString(request.getParameter("status")));

		dto.setCustomerId(DataUtility.getLong(request.getParameter("customerId")));

		populateBean(dto, request);

		return dto;
	}

	/**
	 * Contains Display logics
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("OrderListCtl doGet Start");

		List list;
		List next;

		int pageNo = 1;

		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		OrderDTO dto = (OrderDTO) populateDTO(request);

		OrderModelInt model = ModelFactory.getInstance().getOrderModel();

		try {

			list = model.search(dto, pageNo, pageSize);

			next = model.search(dto, pageNo + 1, pageSize);

			ServletUtility.setList(list, request);

			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}

			if (next == null || next.size() == 0) {
				request.setAttribute("nextListSize", 0);
			} else {
				request.setAttribute("nextListSize", next.size());
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);

			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {

			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;

		} catch (Exception e) {

			e.printStackTrace();
		}

		log.debug("OrderListCtl doGet End");
	}

	/**
	 * Contains Submit logics
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("OrderListCtl doPost Start");

		List list = null;
		List next = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));

		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;

		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		OrderDTO dto = (OrderDTO) populateDTO(request);

		String op = DataUtility.getString(request.getParameter("operation"));

		// Get selected checkbox ids for delete
		String[] ids = request.getParameterValues("ids");

		OrderModelInt model = ModelFactory.getInstance().getOrderModel();

		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op) || OP_PREVIOUS.equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {

					pageNo = 1;

				} else if (OP_NEXT.equalsIgnoreCase(op)) {

					pageNo++;

				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {

					pageNo--;
				}

			} else if (OP_NEW.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.ORDER_CTL, request, response);

				return;

			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.ORDER_LIST_CTL, request, response);

				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {

				pageNo = 1;

				if (ids != null && ids.length > 0) {

					OrderDTO deletedto = new OrderDTO();

					for (String id : ids) {

						deletedto.setOrderId(DataUtility.getLong(id));

						model.delete(deletedto);

						ServletUtility.setSuccessMessage("Data Successfully Deleted!", request);
					}

				} else {

					ServletUtility.setErrorMessage("Select atleast one record", request);
				}
			}

			if (OP_BACK.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.ORDER_LIST_CTL, request, response);

				return;
			}

			dto = (OrderDTO) populateDTO(request);

			list = model.search(dto, pageNo, pageSize);

			ServletUtility.setDto(dto, request);

			next = model.search(dto, pageNo + 1, pageSize);

			ServletUtility.setList(list, request);

			if (list == null || list.size() == 0) {

				if (!OP_DELETE.equalsIgnoreCase(op)) {

					ServletUtility.setErrorMessage("No record found ", request);
				}
			}

			if (next == null || next.size() == 0) {

				request.setAttribute("nextListSize", 0);

			} else {

				request.setAttribute("nextListSize", next.size());
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);

			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {

			log.error(e);

			ServletUtility.handleException(e, request, response);

			return;

		} catch (Exception e) {

			e.printStackTrace();
		}

		log.debug("OrderListCtl doPost End");
	}

	@Override
	protected String getView() {

		return ORSView.ORDER_LIST_VIEW;
	}
}
