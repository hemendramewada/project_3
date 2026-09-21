package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.EmployeeDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.model.EmployeeModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * Employee List Controller (Search + Pagination + Delete)
 * 
 * @author Hemendra mewada
 */

@WebServlet(name = "EmployeeListCtl", urlPatterns = { "/ctl/EmployeeListCtl" })
public class EmployeeListCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(EmployeeListCtl.class);

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		EmployeeDTO dto = new EmployeeDTO();

		dto.setEmployeeName(
				DataUtility.getString(request.getParameter("employeeName")));

		dto.setLastName(
				DataUtility.getString(request.getParameter("lastName")));

		dto.setDepartment(
				DataUtility.getString(request.getParameter("department")));

		populateBean(dto, request);

		return dto;
	}

	// ====================== DO GET ======================
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		List list = null;
		List next = null;

		int pageNo = 1;
		int pageSize =
				DataUtility.getInt(PropertyReader.getValue("page.size"));

		EmployeeDTO dto =
				(EmployeeDTO) populateDTO(request);

		EmployeeModelInt model =
				ModelFactory.getInstance().getEmployeeModel();

		try {

			list = model.search(dto, pageNo, pageSize);
			next = model.search(dto, pageNo + 1, pageSize);

			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage(
						"No record found", request);
			}

			request.setAttribute("nextListSize",
					(next == null) ? 0 : next.size());

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);

			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e,
					request, response);
		}
	}

	// ====================== DO POST ======================
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		List list = null;
		List next = null;

		int pageNo =
				DataUtility.getInt(request.getParameter("pageNo"));

		int pageSize =
				DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0)
				? DataUtility.getInt(
						PropertyReader.getValue("page.size"))
				: pageSize;

		EmployeeDTO dto =
				(EmployeeDTO) populateDTO(request);

		EmployeeModelInt model =
				ModelFactory.getInstance().getEmployeeModel();

		String op =
				DataUtility.getString(
						request.getParameter("operation"));

		String[] ids =
				request.getParameterValues("ids");

		try {

			if (OP_SEARCH.equalsIgnoreCase(op)) {
				pageNo = 1;

			} else if (OP_NEXT.equalsIgnoreCase(op)) {
				pageNo++;

			} else if (OP_PREVIOUS.equalsIgnoreCase(op)
					&& pageNo > 1) {
				pageNo--;

			} else if (OP_NEW.equalsIgnoreCase(op)) {

				ServletUtility.redirect(
						ORSView.EMPLOYEE_CTL,
						request, response);
				return;

			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(
						ORSView.EMPLOYEE_LIST_CTL,
						request, response);
				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {

				pageNo = 1;

				if (ids != null && ids.length > 0) {

					for (String id : ids) {

						EmployeeDTO deleteDTO =
								new EmployeeDTO();

						deleteDTO.setId(
								DataUtility.getLong(id));

						model.delete(deleteDTO);
					}

					ServletUtility.setSuccessMessage(
							"Data deleted successfully",
							request);

				} else {
					ServletUtility.setErrorMessage(
							"Select at least one record",
							request);
				}
			}

			list = model.search(dto, pageNo, pageSize);
			next = model.search(dto, pageNo + 1, pageSize);

			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage(
						"No record found", request);
			}

			request.setAttribute("nextListSize",
					(next == null) ? 0 : next.size());

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setDto(dto, request);

			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e,
					request, response);
		}
	}

	@Override
	protected String getView() {
		return ORSView.EMPLOYEE_LIST_VIEW;
	}
}