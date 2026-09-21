package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.TrainingDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.TrainingModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * Training functionality CRUD operation
 */
@WebServlet(urlPatterns = { "/ctl/TrainingCtl" })
public class TrainingCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(TrainingCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("TrainingCtl Method validate Started");

		boolean pass = true;

		String code = request.getParameter("trainingCode");

		if (DataValidator.isNull(code)) {
			request.setAttribute("trainingCode",
					PropertyReader.getValue("error.require", "Training Code"));
			pass = false;

		} else if (!DataValidator.isInteger(code)) {   // Only numbers allowed
			request.setAttribute("trainingCode",
					"Training Code must contain numbers only");
			pass = false;
		}

		String name = request.getParameter("trainingName");

		if (DataValidator.isNull(name)) {
			request.setAttribute("trainingName",
					PropertyReader.getValue("error.require", "Training Name"));
			pass = false;

		} else if (!DataValidator.isName(name)) {   // No numbers allowed
			request.setAttribute("trainingName",
					"Training Name must contain alphabets only");
			pass = false;
		}

		String trainer = request.getParameter("trainerName");

		if (DataValidator.isNull(trainer)) {
			request.setAttribute("trainerName",
					PropertyReader.getValue("error.require", "Trainer Name"));
			pass = false;

		} else if (!DataValidator.isName(trainer)) {   // No numbers allowed
			request.setAttribute("trainerName",
					"Trainer Name must contain alphabets only");
			pass = false;
		}

		String date = request.getParameter("trainingDate");

		if (DataValidator.isNull(date)) {
			request.setAttribute("trainingDate",
					PropertyReader.getValue("error.require", "Training Date"));
			pass = false;

		} else if (!DataValidator.isDate(date)) {
			request.setAttribute("trainingDate",
					PropertyReader.getValue("error.date", "Training Date"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("trainingStatus"))) {
			request.setAttribute("trainingStatus",
					PropertyReader.getValue("error.require", "Training Status"));
			pass = false;
		}

		log.debug("TrainingCtl Method validate Ended");
		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		log.debug("TrainingCtl Method populateDTO Started");

		TrainingDTO dto = new TrainingDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setTrainingCode(DataUtility.getString(request.getParameter("trainingCode")));
		dto.setTrainingName(DataUtility.getString(request.getParameter("trainingName")));
		dto.setTrainerName(DataUtility.getString(request.getParameter("trainerName")));
		dto.setTrainingDate(DataUtility.getDate(request.getParameter("trainingDate")));
		dto.setTrainingStatus(DataUtility.getString(request.getParameter("trainingStatus")));

		populateBean(dto, request);

		log.debug("TrainingCtl Method populateDTO Ended");

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("TrainingCtl Method doGet Started");

		long id = DataUtility.getLong(request.getParameter("id"));

		TrainingModelInt model = ModelFactory.getInstance().getTrainingModel();

		if (id > 0) {
			try {
				TrainingDTO dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
		log.debug("TrainingCtl Method doGet Ended");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("TrainingCtl Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		TrainingModelInt model = ModelFactory.getInstance().getTrainingModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			TrainingDTO dto = (TrainingDTO) populateDTO(request);

			try {

				if (id > 0) {
					model.update(dto);
					ServletUtility.setSuccessMessage("Data is successfully Updated", request);
				} else {

					try {
						model.add(dto);
						ServletUtility.setSuccessMessage("Data is successfully Saved", request);
					} catch (DuplicateRecordException e) {
						ServletUtility.setDto(dto, request);
						ServletUtility.setErrorMessage("Training Code already exists", request);
					}
				}

				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Training already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			TrainingDTO dto = (TrainingDTO) populateDTO(request);

			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.TRAINING_LIST_CTL, request, response);
				return;

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.TRAINING_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.TRAINING_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("TrainingCtl Method doPost Ended");
	}

	@Override
	protected String getView() {
		return ORSView.TRAINING_VIEW;
	}
}