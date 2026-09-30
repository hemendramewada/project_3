package in.co.rays.project_3.controller;

import java.io.IOException;

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
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * Podcast Controller
 * 
 * @author Rajendra Singh
 *
 */
@WebServlet(urlPatterns = { "/ctl/PodcastCtl" })
public class PodcastCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(PodcastCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("Podcast validate started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("title"))) {
			request.setAttribute("title", PropertyReader.getValue("error.require", "Title"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("hostName"))) {
			request.setAttribute("hostName", PropertyReader.getValue("error.require", "Host Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("duration"))) {
			request.setAttribute("duration", PropertyReader.getValue("error.require", "Duration"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("category"))) {
			request.setAttribute("category", PropertyReader.getValue("error.require", "Category"));
			pass = false;
		}

		log.debug("Podcast validate End");

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		log.debug("Podcast populateDTO Start");

		PodcastDTO dto = new PodcastDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setTitle(DataUtility.getString(request.getParameter("title")));
		dto.setHostName(DataUtility.getString(request.getParameter("hostName")));
		dto.setDuration(DataUtility.getString(request.getParameter("duration")));
		dto.setCategory(DataUtility.getString(request.getParameter("category")));

		populateBean(dto, request);

		log.debug("Podcast populateDTO End");

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("Podcast doGet Start");

		String op = DataUtility.getString(request.getParameter("operation"));

		long id = DataUtility.getLong(request.getParameter("id"));

		PodcastModelInt model = ModelFactory.getInstance().getPodcastModel();

		if (id > 0 || op != null) {

			try {

				PodcastDTO dto = model.findByPK(id);

				ServletUtility.setDto(dto, request);

			} catch (Exception e) {

				log.error(e);

				ServletUtility.handleException(e, request, response);

				return;
			}
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("Podcast doGet End");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("Podcast doPost Start");

		String op = DataUtility.getString(request.getParameter("operation"));

		long id = DataUtility.getLong(request.getParameter("id"));

		PodcastModelInt model = ModelFactory.getInstance().getPodcastModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			PodcastDTO dto = (PodcastDTO) populateDTO(request);

			try {

				if (id > 0) {

					dto.setId(id);

					model.update(dto);

					ServletUtility.setDto(dto, request);

					ServletUtility.setSuccessMessage("Data is successfully Updated", request);

				} else {

					model.add(dto);

					ServletUtility.setDto(dto, request);

					ServletUtility.setSuccessMessage("Data is successfully Saved", request);
				}

			} catch (Exception e) {

				ServletUtility.setDto(dto, request);

				ServletUtility.setErrorMessage(e.getMessage(), request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			PodcastDTO dto = (PodcastDTO) populateDTO(request);

			try {

				model.delete(dto);

				ServletUtility.redirect(ORSView.PODCAST_LIST_CTL, request, response);

				return;

			} catch (ApplicationException e) {

				log.error(e);

				ServletUtility.handleException(e, request, response);

				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.PODCAST_LIST_CTL, request, response);

			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.PODCAST_CTL, request, response);

			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("Podcast doPost End");
	}

	@Override
	protected String getView() {

		return ORSView.PODCAST_VIEW;
	}
}