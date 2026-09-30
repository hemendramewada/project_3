package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.VoiceCommandDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.VoiceCommandModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * Voice Command Controller
 *
 * @author Rajendra Singh
 */
@WebServlet(urlPatterns = { "/ctl/VoiceCommandCtl" })
public class VoiceCommandCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(VoiceCommandCtl.class);

	/**
	 * Preload Method
	 */
	@Override
	protected void preload(HttpServletRequest request) {

		// No preload required

	}

	/**
	 * Validation
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("commandCode"))) {
			request.setAttribute("commandCode", PropertyReader.getValue("error.require", "Command Code"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("userName"))) {
			request.setAttribute("userName", PropertyReader.getValue("error.require", "User Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("commandText"))) {
			request.setAttribute("commandText", PropertyReader.getValue("error.require", "Command Text"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("status"))) {
			request.setAttribute("status", PropertyReader.getValue("error.require", "Status"));
			pass = false;
		}

		return pass;
	}

	/**
	 * Populate DTO
	 */
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		VoiceCommandDTO dto = new VoiceCommandDTO();

		dto.setCommandId(DataUtility.getLong(request.getParameter("commandId")));
		dto.setCommandCode(DataUtility.getString(request.getParameter("commandCode")));
		dto.setUserName(DataUtility.getString(request.getParameter("userName")));
		dto.setCommandText(DataUtility.getString(request.getParameter("commandText")));
		dto.setStatus(DataUtility.getString(request.getParameter("status")));

		populateBean(dto, request);

		log.debug("VoiceCommandCtl populateDTO End");

		return dto;
	}

	/**
	 * doGet
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("VoiceCommandCtl doGet Start");

		String op = DataUtility.getString(request.getParameter("operation"));

		VoiceCommandModelInt model = ModelFactory.getInstance().getVoiceCommandModel();

		long id = DataUtility.getLong(request.getParameter("commandId"));

		if (id > 0 || op != null) {

			VoiceCommandDTO dto = null;

			try {

				dto = model.findByPK(id);

				ServletUtility.setDto(dto, request);

			} catch (Exception e) {

				log.error(e);

				ServletUtility.handleException(e, request, response);

				return;
			}
		}

		ServletUtility.forward(getView(), request, response);

	}

	/**
	 * doPost
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String op = DataUtility.getString(request.getParameter("operation"));

		VoiceCommandModelInt model = ModelFactory.getInstance().getVoiceCommandModel();

		long id = DataUtility.getLong(request.getParameter("commandId"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			VoiceCommandDTO dto = (VoiceCommandDTO) populateDTO(request);

			try {

				if (id > 0) {

					model.update(dto);

					ServletUtility.setSuccessMessage("Data is successfully Updated", request);

					ServletUtility.setDto(dto, request);

				} else {

					try {

						model.add(dto);

						ServletUtility.setSuccessMessage("Data is successfully Saved", request);

					} catch (ApplicationException e) {

						log.error(e);

						ServletUtility.handleException(e, request, response);

						return;

					} catch (DuplicateRecordException e) {

						ServletUtility.setDto(dto, request);

						ServletUtility.setErrorMessage("Command Code already exists", request);
					}
				}

			} catch (ApplicationException e) {

				log.error(e);

				ServletUtility.handleException(e, request, response);

				return;

			} catch (DuplicateRecordException e) {

				ServletUtility.setDto(dto, request);

				ServletUtility.setErrorMessage("Command Code already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			VoiceCommandDTO dto = (VoiceCommandDTO) populateDTO(request);

			try {

				model.delete(dto);

				ServletUtility.redirect(ORSView.VOICE_COMMAND_LIST_CTL, request, response);

				return;

			} catch (ApplicationException e) {

				log.error(e);

				ServletUtility.handleException(e, request, response);

				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.VOICE_COMMAND_LIST_CTL, request, response);

			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.VOICE_COMMAND_CTL, request, response);

			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("VoiceCommandCtl doPost End");
	}

	/**
	 * Return View
	 */
	@Override
	protected String getView() {

		return ORSView.VOICE_COMMAND_VIEW;
	}

}