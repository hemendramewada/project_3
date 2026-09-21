package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.SettingsDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.SettingsModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/SettingsCtl" })
public class SettingsCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(SettingsCtl.class);

	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("settingName"))) {
			request.setAttribute("settingName",
					PropertyReader.getValue("error.require", "settingName"));
			pass = false;
		}
		else if(!DataValidator.isName(request.getParameter("settingName"))) {
			request.setAttribute("settingName",
					PropertyReader.getValue("error.require", "settingName"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("settingValue"))) {
			request.setAttribute("settingValue",
					PropertyReader.getValue("error.require", "settingValue"));
			pass = false;
		}
		else if(!DataValidator.isName(request.getParameter("settingValue"))) {
			request.setAttribute("settingValue",
					PropertyReader.getValue("error.require", "settingValue"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("settingType"))) {
			request.setAttribute("settingType",
					PropertyReader.getValue("error.require", "settingType"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("settingStatus"))) {
			request.setAttribute("settingStatus",
					PropertyReader.getValue("error.require", "settingStatus"));
			pass = false;
		}

		return pass;
	}
	
	@Override
	protected void preload(HttpServletRequest request) {

	    Map<String, String> typeMap = new HashMap<>();
	    typeMap.put("Male", "Male");
	    typeMap.put("Female", "Female");

	    Map<String, String> statusMap = new HashMap<>();
	    statusMap.put("Committed", "Committed");
	    statusMap.put("One Sided", "One Sided");

	    request.setAttribute("typeMap", typeMap);
	    request.setAttribute("statusMap", statusMap);
	}


	protected BaseDTO populateDTO(HttpServletRequest request) {

		SettingsDTO dto = new SettingsDTO();

		dto.setSettingName(request.getParameter("settingName"));
		dto.setSettingValue(request.getParameter("settingValue"));
		dto.setSettingType(request.getParameter("settingType"));
		dto.setSettingStatus(request.getParameter("settingStatus"));

		populateBean(dto, request);

		return dto;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String op = request.getParameter("operation");

		long id = DataUtility.getLong(request.getParameter("id"));

		SettingsModelInt model = ModelFactory.getInstance().getSettingsModel();

		if (id > 0 || op != null) {

			SettingsDTO dto;
			try {
				dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {
				log.error(e);
//				ServletUtility.handleException(e, request, response, getView());
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String op = request.getParameter("operation");

		long id = DataUtility.getLong(request.getParameter("id"));

		SettingsModelInt model = ModelFactory.getInstance().getSettingsModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			SettingsDTO dto = (SettingsDTO) populateDTO(request);

			try {
				if (id > 0) {

					dto.setId(id);
					model.update(dto);
					ServletUtility.setSuccessMessage("Record Successfully Updated", request);

				} else {

					model.add(dto);
					ServletUtility.setSuccessMessage("Record Successfully Saved", request);
				}

				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {

				log.error(e);
//				ServletUtility.handleException(e, request, response, getView());
				return;

			} catch (DuplicateRecordException e) {

				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Setting Name Already Exists", request);
			}

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.SETTINGS_CTL, request, response);
			return;

//		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
//
//			ServletUtility.redirect(ORSView.SETTINGS_LIST_CTL, request, response);
//			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.SETTINGS_VIEW;
	}
}
