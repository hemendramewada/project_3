package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.DecorationDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.DecorationModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/DecorationCtl" })
public class DecorationCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(DecorationCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("DecorationCtl Method validate Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("theme"))) {
			request.setAttribute("theme", PropertyReader.getValue("error.require", "Theme"));
			pass = false;
		}else if (!DataValidator.isName(request.getParameter("theme"))) {
			request.setAttribute("theme", PropertyReader.getValue("error.name", "Theme"));
			pass = false;

		}

		if (DataValidator.isNull(request.getParameter("vendorName"))) {
			request.setAttribute("vendorName", PropertyReader.getValue("error.require", "Vendor Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("cost"))) {
			request.setAttribute("cost", PropertyReader.getValue("error.require", "Cost"));
			pass = false;
		}

		log.debug("DecorationCtl Method validate Ended");

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		log.debug("DecorationCtl Method populatebean Started");

		DecorationDTO dto = new DecorationDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setTheme(DataUtility.getString(request.getParameter("theme")));
		dto.setVendorName(DataUtility.getString(request.getParameter("vendorName")));
		dto.setCost(DataUtility.getString(request.getParameter("cost")));

		populateBean(dto, request);

		log.debug("DecorationCtl Method populatebean Ended");

		return dto;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("DecorationCtl Method doGet Started");

		long id = DataUtility.getLong(request.getParameter("id"));

		DecorationModelInt model = ModelFactory.getInstance().getDecorationModel();

		if (id > 0) {
			DecorationDTO dto;
			try {
				dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		log.debug("DecorationCtl Method doGet Ended");

		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("DecorationCtl Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		DecorationModelInt model = ModelFactory.getInstance().getDecorationModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			DecorationDTO dto = (DecorationDTO) populateDTO(request);

			try {

				if (id > 0) {

					model.update(dto);
					ServletUtility.setSuccessMessage("Data is successfully Update", request);

				} else {

					try {

						model.add(dto);
						ServletUtility.setSuccessMessage("Data is successfully saved", request);

					} catch (DuplicateRecordException e) {

						ServletUtility.setDto(dto, request);
						ServletUtility.setErrorMessage("Decoration already exists", request);
					}
				}

				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {

				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;

			} catch (DuplicateRecordException e) {

				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Theme already exists", request);
			}

		}

		else if (OP_DELETE.equalsIgnoreCase(op)) {

			DecorationDTO dto = (DecorationDTO) populateDTO(request);

			try {

				model.delete(dto);

				ServletUtility.redirect(ORSView.DECORATION_LIST_CTL, request, response);

				return;

			} catch (ApplicationException e) {

				log.error(e);

				ServletUtility.handleException(e, request, response);

				return;
			}

		}

		else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.DECORATION_LIST_CTL, request, response);

			return;

		}

		else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.DECORATION_CTL, request, response);

			return;

		}

		ServletUtility.forward(getView(), request, response);

		log.debug("DecorationCtl Method doPost Ended");
	}

	@Override
	protected String getView() {

		return ORSView.DECORATION_VIEW;

	}

}