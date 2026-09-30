package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.BugTrackerDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.model.BugTrackerModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * BugTracker functionality Controller
 * 
 * @author Rajendra Singh
 *
 */
@WebServlet(urlPatterns = { "/ctl/BugTrackerCtl" })
public class BugTrackerCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(BugTrackerCtl.class);

	@Override
	protected void preload(HttpServletRequest request) {
		// No preload required
	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("BugTrackerCtl validate start");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("bugId"))) {
			request.setAttribute("bugId", PropertyReader.getValue("error.require", "Bug ID"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("title"))) {
			request.setAttribute("title", PropertyReader.getValue("error.require", "Title"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("severity"))) {
			request.setAttribute("severity", PropertyReader.getValue("error.require", "Severity"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("assignedTo"))) {
			request.setAttribute("assignedTo", PropertyReader.getValue("error.require", "Assigned To"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("status"))) {
			request.setAttribute("status", PropertyReader.getValue("error.require", "Status"));
			pass = false;
		}

		log.debug("BugTrackerCtl validate end");
		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		log.debug("BugTrackerCtl populateDTO start");

		BugTrackerDTO dto = new BugTrackerDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setBugId(DataUtility.getInt(request.getParameter("bugId")));
		dto.setTitle(DataUtility.getString(request.getParameter("title")));
		dto.setSeverity(DataUtility.getString(request.getParameter("severity")));
		dto.setAssignedTo(DataUtility.getString(request.getParameter("assignedTo")));
		dto.setStatus(DataUtility.getString(request.getParameter("status")));

		populateBean(dto, request);

		log.debug("BugTrackerCtl populateDTO end");

		return dto;
	}

	/**
	 * Display Logic
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("BugTrackerCtl doGet start");

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		BugTrackerModelInt model = ModelFactory.getInstance().getBugTrackerModel();

		if (id > 0 || op != null) {

			try {

				BugTrackerDTO dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);

			} catch (Exception e) {

				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("BugTrackerCtl doGet end");
	}

	/**
	 * Submit Logic
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("BugTrackerCtl doPost start");

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		BugTrackerModelInt model = ModelFactory.getInstance().getBugTrackerModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			BugTrackerDTO dto = (BugTrackerDTO) populateDTO(request);

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

				log.error(e);

				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Record already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			BugTrackerDTO dto = (BugTrackerDTO) populateDTO(request);

			try {

				model.delete(dto);

				ServletUtility.redirect(ORSView.BUGTRACKER_LIST_CTL, request, response);
				return;

			} catch (ApplicationException e) {

				log.error(e);

				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.BUGTRACKER_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.BUGTRACKER_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("BugTrackerCtl doPost end");
	}

	@Override
	protected String getView() {
		return ORSView.BUGTRACKER_VIEW;
	}
}