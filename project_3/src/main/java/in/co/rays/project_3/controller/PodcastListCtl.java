package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.PodcastDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.PodcastModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * Podcast List Controller
 * 
 * @author Rajendra Singh
 *
 */
@WebServlet(name = "PodcastListCtl", urlPatterns = { "/ctl/PodcastListCtl" })
public class PodcastListCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(PodcastListCtl.class);

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		PodcastDTO dto = new PodcastDTO();

		dto.setTitle(DataUtility.getString(request.getParameter("title")));
		dto.setHostName(DataUtility.getString(request.getParameter("hostName")));
		dto.setDuration(DataUtility.getString(request.getParameter("duration")));
		dto.setCategory(DataUtility.getString(request.getParameter("category")));

		populateBean(dto, request);

		return dto;
	}

	/**
	 * Contains Display Logic
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("Podcast List doGet Start");

		List list = null;
		List next = null;

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		PodcastDTO dto = (PodcastDTO) populateDTO(request);

		PodcastModelInt model = ModelFactory.getInstance().getPodcastModel();

		try {

			list = model.search(dto, pageNo, pageSize);
			next = model.search(dto, pageNo + 1, pageSize);

			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found", request);
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
		}

		log.debug("Podcast List doGet End");
	}

	/**
	 * Contains Submit Logic
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("Podcast List doPost Start");

		List list = null;
		List next = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;

		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		PodcastDTO dto = (PodcastDTO) populateDTO(request);

		String op = DataUtility.getString(request.getParameter("operation"));

		String[] ids = request.getParameterValues("ids");

		PodcastModelInt model = ModelFactory.getInstance().getPodcastModel();

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

				ServletUtility.redirect(ORSView.PODCAST_CTL, request, response);
				return;

			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.PODCAST_LIST_CTL, request, response);
				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {

				pageNo = 1;

				if (ids != null && ids.length > 0) {

					PodcastDTO deleteDto = new PodcastDTO();

					for (String id : ids) {

						deleteDto.setId(DataUtility.getLong(id));

						model.delete(deleteDto);
					}

					ServletUtility.setSuccessMessage("Data Deleted Successfully", request);

				} else {

					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			}

			if (OP_BACK.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.PODCAST_LIST_CTL, request, response);

				return;
			}

			dto = (PodcastDTO) populateDTO(request);

			list = model.search(dto, pageNo, pageSize);

			next = model.search(dto, pageNo + 1, pageSize);

			if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {

				ServletUtility.setErrorMessage("No record found", request);
			}

			if (next == null || next.size() == 0) {
				request.setAttribute("nextListSize", 0);
			} else {
				request.setAttribute("nextListSize", next.size());
			}

			ServletUtility.setDto(dto, request);
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);

			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {

			log.error(e);

			ServletUtility.handleException(e, request, response);

			return;
		}

		log.debug("Podcast List doPost End");
	}

	@Override
	protected String getView() {

		return ORSView.PODCAST_LIST_VIEW;
	}
}