package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.VehicleDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.VehicleModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/VehicleCtl" })
public class VehicleCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(VehicleCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("vehicleName"))) {
			request.setAttribute("vehicleName", PropertyReader.getValue("error.require", "Vehicle Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("model"))) {
			request.setAttribute("model", PropertyReader.getValue("error.require", "Model"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("color"))) {
			request.setAttribute("color", PropertyReader.getValue("error.require", "Color"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("price"))) {
			request.setAttribute("price", PropertyReader.getValue("error.require", "Price"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		VehicleDTO dto = new VehicleDTO();

		dto.setVechicleId(DataUtility.getLong(request.getParameter("vechicleId")));

		dto.setVehicleName(DataUtility.getString(request.getParameter("vehicleName")));

		dto.setModel(DataUtility.getString(request.getParameter("model")));

		dto.setColor(DataUtility.getString(request.getParameter("color")));

		dto.setPrice(DataUtility.getDouble(request.getParameter("price")));

		populateBean(dto, request);

		log.debug("VehicleCtl Method populateDTO Ended");

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("VehicleCtl Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		VehicleModelInt model = ModelFactory.getInstance().getVehicleModel();

		long id = DataUtility.getLong(request.getParameter("vechicleId"));

		if (id > 0 || op != null) {

			VehicleDTO dto = null;

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

		log.debug("VehicleCtl Method doGet Ended");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String op = DataUtility.getString(request.getParameter("operation"));

		VehicleModelInt model = ModelFactory.getInstance().getVehicleModel();

		long id = DataUtility.getLong(request.getParameter("vechicleId"));

		/*
		 * SAVE / UPDATE
		 */
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			VehicleDTO dto = (VehicleDTO) populateDTO(request);

			try {

				if (id > 0) {

					model.update(dto);

					ServletUtility.setSuccessMessage("Data is successfully Updated", request);

				} else {

					model.add(dto);

					ServletUtility.setSuccessMessage("Data is successfully saved", request);
				}

				/*
				 * IMPORTANT: Save/Update ke baad Vehicle List par redirect
				 */
				ServletUtility.redirect(ORSView.VEHICLE_LIST_CTL, request, response);

				return;

			} catch (ApplicationException e) {

				log.error(e);

				ServletUtility.handleException(e, request, response);

				return;

			} catch (DuplicateRecordException e) {

				ServletUtility.setDto(dto, request);

				ServletUtility.setErrorMessage("Vehicle already exists", request);
			}

		}

		/*
		 * DELETE
		 */
		else if (OP_DELETE.equalsIgnoreCase(op)) {

			VehicleDTO dto = (VehicleDTO) populateDTO(request);

			try {

				model.delete(dto);

				ServletUtility.redirect(ORSView.VEHICLE_LIST_CTL, request, response);

				return;

			} catch (ApplicationException e) {

				log.error(e);

				ServletUtility.handleException(e, request, response);

				return;
			}
		}

		/*
		 * CANCEL
		 */
		else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.VEHICLE_LIST_CTL, request, response);

			return;
		}

		/*
		 * RESET
		 */
		else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.VEHICLE_CTL, request, response);

			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("VehicleCtl Method doPost Ended");
	}

	@Override
	protected String getView() {

		return ORSView.VEHICLE_VIEW;
	}
}