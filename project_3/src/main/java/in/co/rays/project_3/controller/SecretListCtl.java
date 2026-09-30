package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.SecretDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.SecretModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "SecretListCtl", urlPatterns = "/ctl/SecretListCtl")
public class SecretListCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request) {

		Map<String, String> map = new HashMap<>();
		map.put("Active", "Active");
		map.put("Inactive", "Inactive");

		request.setAttribute("statusList", map);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int pageNo = 1;
		int pageSize = DataUtility.getInt(
				request.getParameter("pageSize") == null ? "5" : request.getParameter("pageSize"));

		SecretDTO dto = new SecretDTO();
		SecretModelInt model = ModelFactory.getInstance().getSecretModel();

		try {

			List list = model.search(dto, pageNo, pageSize);
			List nextList = model.search(dto, pageNo + 1, pageSize);

			request.setAttribute("list", list);

			if (nextList == null || nextList.size() == 0) {
				request.setAttribute("nextListSize", 0);
			} else {
				request.setAttribute("nextListSize", nextList.size());
			}

			request.setAttribute("pageNo", pageNo);
			request.setAttribute("pageSize", pageSize);

		} catch (ApplicationException e) {
			
			ServletUtility.handleException(e, request, response);
			return;
		}
		

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? 5 : pageSize;

		String op = DataUtility.getString(request.getParameter("operation"));

		SecretDTO dto = new SecretDTO();

		dto.setSecretCode(DataUtility.getString(request.getParameter("secretCode")));
		dto.setKeyName(DataUtility.getString(request.getParameter("keyName")));
		dto.setStatus(DataUtility.getString(request.getParameter("status")));

		SecretModelInt model = ModelFactory.getInstance().getSecretModel();

		if (OP_SEARCH.equalsIgnoreCase(op)) {
			pageNo = 1;

		} else if (OP_NEXT.equalsIgnoreCase(op)) {
			pageNo++;

		} else if (OP_PREVIOUS.equalsIgnoreCase(op)) {
			pageNo--;

		} else if (OP_NEW.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.SECRET_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.SECRET_LIST_CTL, request, response);
			return;

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			String[] ids = request.getParameterValues("ids");

			if (ids != null) {

				for (String id : ids) {
					SecretDTO deleteDto = new SecretDTO();
					deleteDto.setId(DataUtility.getLong(id));

					try {
						model.delete(deleteDto);

					}  catch (ApplicationException e) {
						e.printStackTrace();
						ServletUtility.handleException(e, request, response);
						return;
					}
				}

				ServletUtility.setSuccessMessage("Secret Deleted Successfully", request);

			} else {
				ServletUtility.setErrorMessage("Select at least one record", request);
			}
		}

		if (OP_BACK.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.SECRET_LIST_CTL, request, response);
			return;
		}

		try {

			List list = model.search(dto, pageNo, pageSize);
			List nextList = model.search(dto, pageNo + 1, pageSize);

			request.setAttribute("list", list);

			if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
				ServletUtility.setErrorMessage("No record found", request);
			}

			if (nextList == null || nextList.size() == 0) {
				request.setAttribute("nextListSize", 0);
			} else {
				request.setAttribute("nextListSize", nextList.size());
			}

			request.setAttribute("pageNo", pageNo);
			request.setAttribute("pageSize", pageSize);

			ServletUtility.forward(getView(), request, response);

		}  catch (ApplicationException e) {
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
		}
	}

	@Override
	protected String getView() {
		return ORSView.SECRET_LIST_VIEW;
	}
}