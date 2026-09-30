package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.SmartLightDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.SmartLightModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/SmartLightCtl" })
public class SmartLightCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(SmartLightCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("lightCode"))) {
			request.setAttribute("lightCode", PropertyReader.getValue("error.require", "Light Code"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("roomName"))) {
			request.setAttribute("roomName", PropertyReader.getValue("error.require", "Room Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("brightnessLevel"))) {
			request.setAttribute("brightnessLevel", PropertyReader.getValue("error.require", "Brightness Level"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("status"))) {
			request.setAttribute("status", PropertyReader.getValue("error.require", "Status"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		SmartLightDTO dto = new SmartLightDTO();

		dto.setLightId(DataUtility.getLong(request.getParameter("lightId")));

		dto.setLightCode(DataUtility.getString(request.getParameter("lightCode")));

		dto.setRoomName(DataUtility.getString(request.getParameter("roomName")));

		dto.setBrightnessLevel(DataUtility.getInt(request.getParameter("brightnessLevel")));

		dto.setStatus(DataUtility.getString(request.getParameter("status")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("SmartLightCtl doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		SmartLightModelInt model = ModelFactory.getInstance().getSmartLightModel();

		long lightId = DataUtility.getLong(request.getParameter("lightId"));

		if (lightId > 0 || op != null) {

			SmartLightDTO dto = null;

			try {

				dto = model.findByPK(lightId);

				ServletUtility.setDto(dto, request);

			} catch (Exception e) {

				log.error(e);

				ServletUtility.handleException(e, request, response);

				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String op = DataUtility.getString(request.getParameter("operation"));

		SmartLightModelInt model = ModelFactory.getInstance().getSmartLightModel();

		long lightId = DataUtility.getLong(request.getParameter("lightId"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			SmartLightDTO dto = (SmartLightDTO) populateDTO(request);

			try {

				if (lightId > 0) {

					model.update(dto);

					ServletUtility.setSuccessMessage("Data is successfully Updated", request);

					ServletUtility.setDto(dto, request);

				} else {

					try {

						model.add(dto);

						ServletUtility.setSuccessMessage("Data is successfully Saved", request);

					} catch (DuplicateRecordException e) {

						ServletUtility.setDto(dto, request);

						ServletUtility.setErrorMessage("Light Code already exists", request);
					}
				}

			} catch (ApplicationException e) {

				log.error(e);

				ServletUtility.handleException(e, request, response);

				return;

			} catch (DuplicateRecordException e) {

				ServletUtility.setDto(dto, request);

				ServletUtility.setErrorMessage("Light Code already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			SmartLightDTO dto = (SmartLightDTO) populateDTO(request);

			try {

				model.delete(dto);

				ServletUtility.redirect(ORSView.SMARTLIGHT_LIST_CTL, request, response);

				return;

			} catch (ApplicationException e) {

				log.error(e);

				ServletUtility.handleException(e, request, response);

				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.SMARTLIGHT_LIST_CTL, request, response);

			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.SMARTLIGHT_CTL, request, response);

			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("SmartLightCtl doPost Ended");
	}

	@Override
	protected String getView() {

		return ORSView.SMARTLIGHT_VIEW;
	}

}