package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.SmartParkingDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.SmartParkingModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * SmartParking functionality controller To perform add, delete, update, park
 * and release operation
 *
 * @author Rajendra Singh
 *
 */
@WebServlet(urlPatterns = { "/ctl/SmartParkingCtl" })
public class SmartParkingCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(SmartParkingCtl.class);

	public static final String OP_PARK = "Park Vehicle";
	public static final String OP_RELEASE = "Release Slot";

	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("vehicalNumber"))) {

			request.setAttribute("vehicalNumber", PropertyReader.getValue("error.require", "Vehical Number"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("vehicalType"))) {

			request.setAttribute("vehicalType", PropertyReader.getValue("error.require", "Vehical Type"));

			pass = false;
		}

		return pass;
	}

	protected BaseDTO populateDTO(HttpServletRequest request) {

		SmartParkingDTO dto = new SmartParkingDTO();

		dto.setSlotId(DataUtility.getInt(request.getParameter("slotId")));

		dto.setVehicalNumber(DataUtility.getString(request.getParameter("vehicalNumber")));

		dto.setVehicalType(DataUtility.getString(request.getParameter("vehicalType")));

		dto.setOccupied(DataUtility.getBoolean(request.getParameter("occupied")));

		populateBean(dto, request);

		log.debug("SmartParkingCtl Method populateDTO Ended");

		return dto;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("SmartParkingCtl Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		SmartParkingModelInt model = ModelFactory.getInstance().getSmartParkingModel();

		long id = DataUtility.getLong(request.getParameter("slotId"));

		if (id > 0 || op != null) {

			SmartParkingDTO dto = null;

			try {

				dto = model.findByPK(id);

				ServletUtility.setDto(dto, request);

			} catch (Exception e) {

				e.printStackTrace();
				log.error(e);

				ServletUtility.handleException(e, request, response);

				return;
			}
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("SmartParkingCtl Method doGet Ended");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("SmartParkingCtl Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		SmartParkingModelInt model = ModelFactory.getInstance().getSmartParkingModel();

		long id = DataUtility.getLong(request.getParameter("slotId"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			SmartParkingDTO dto = (SmartParkingDTO) populateDTO(request);

			try {

				if (id > 0) {

					model.update(dto);

					ServletUtility.setSuccessMessage("Data is successfully Updated", request);

					ServletUtility.setDto(dto, request);

				} else {

					try {

						model.add(dto);

						ServletUtility.setSuccessMessage("Data is successfully saved", request);

					} catch (ApplicationException e) {

						log.error(e);

						ServletUtility.handleException(e, request, response);

						return;

					} catch (DuplicateRecordException e) {

						ServletUtility.setDto(dto, request);

						ServletUtility.setErrorMessage("Vehical Number already exists", request);
					}
				}

			} catch (ApplicationException e) {

				log.error(e);

				ServletUtility.handleException(e, request, response);

				return;

			} catch (DuplicateRecordException e) {

				ServletUtility.setDto(dto, request);

				ServletUtility.setErrorMessage("Vehical Number already exists", request);
			}

		} else if (OP_PARK.equalsIgnoreCase(op)) {

			SmartParkingDTO dto = (SmartParkingDTO) populateDTO(request);

			try {

				model.parkVehical(dto);

				ServletUtility.setSuccessMessage("Vehical parked successfully", request);

				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {

				log.error(e);

				ServletUtility.handleException(e, request, response);

				return;
			}

		} else if (OP_RELEASE.equalsIgnoreCase(op)) {

			SmartParkingDTO dto = (SmartParkingDTO) populateDTO(request);

			try {

				model.releaseSlot(dto);

				ServletUtility.setSuccessMessage("Parking slot released successfully", request);

				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {

				log.error(e);

				ServletUtility.handleException(e, request, response);

				return;
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			SmartParkingDTO dto = (SmartParkingDTO) populateDTO(request);

			try {

				model.delete(dto);

				ServletUtility.redirect(ORSView.SMART_PARKING_LIST_CTL, request, response);

				return;

			} catch (ApplicationException e) {

				log.error(e);

				ServletUtility.handleException(e, request, response);

				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.SMART_PARKING_LIST_CTL, request, response);

			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.SMART_PARKING_CTL, request, response);

			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("SmartParkingCtl Method doPost Ended");
	}

	@Override
	protected String getView() {

		return ORSView.SMART_PARKING_VIEW;
	}

}