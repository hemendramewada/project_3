package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.EventDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.EventModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "EventCtl", urlPatterns = "/ctl/EventCtl")
public class EventCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;
		String op = request.getParameter("operation");
		String eventCode = request.getParameter("eventCode");

		if (OP_RESET.equalsIgnoreCase(op) || OP_CANCEL.equalsIgnoreCase(op)) {
			return pass;
		}

		if (DataValidator.isNull(request.getParameter("eventCode"))) {
			request.setAttribute("eventCode", PropertyReader.getValue("error.require", "Event Code"));
			pass = false;
		}
		if (!eventCode.matches("^EVE-\\d+$")) {
			request.setAttribute("eventCode", "eventcode must be started in format 123 ");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("eventName"))) {
			request.setAttribute("eventName", PropertyReader.getValue("error.require", "Event Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("eventDate"))) {
			request.setAttribute("eventDate", PropertyReader.getValue("error.require", "Event Date"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("organizer"))) {
			request.setAttribute("organizer", PropertyReader.getValue("error.require", "Organizer"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("eventStatus"))) {
			request.setAttribute("eventStatus", PropertyReader.getValue("error.require", "Event Status"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		EventDTO dto = new EventDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setEventCode(DataUtility.getString(request.getParameter("eventCode")));
		dto.setEventName(DataUtility.getString(request.getParameter("eventName")));
		dto.setEventDate(DataUtility.getDate(request.getParameter("eventDate")));
		dto.setOrganizer(DataUtility.getString(request.getParameter("organizer")));
		dto.setEventStatus(DataUtility.getString(request.getParameter("eventStatus")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Long id = DataUtility.getLong(req.getParameter("id"));
		EventModelInt model = ModelFactory.getInstance().getEventModel();

		if (id > 0) {
			try {
				EventDTO dto = model.findByPK(id);
				ServletUtility.setDto(dto, req);

			} catch (DatabaseException e) {
				e.printStackTrace();
				ServletUtility.handleExceptionDBDown(e, req, resp, getView());
				return;

			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, req, resp);
				return;
			}
		}

		ServletUtility.forward(getView(), req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String op = DataUtility.getString(req.getParameter("operation"));
		EventModelInt model = ModelFactory.getInstance().getEventModel();

		if (OP_SAVE.equalsIgnoreCase(op)) {

			EventDTO bean = (EventDTO) populateDTO(req);

			try {
				model.add(bean);
				ServletUtility.setDto(bean, req);
				ServletUtility.setSuccessMessage("Event Added Successfully !!!", req);

			} catch (DuplicateRecordException dre) {
				ServletUtility.setDto(bean, req);
				ServletUtility.setErrorMessage("Event Code Already Exist !!!", req);

			} catch (DatabaseException de) {
				de.printStackTrace();
				ServletUtility.handleExceptionDBDown(de, req, resp, getView());
				return;

			} catch (ApplicationException ae) {
				ae.printStackTrace();
				ServletUtility.handleException(ae, req, resp);
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			EventDTO dto = (EventDTO) populateDTO(req);

			try {
				model.update(dto);
				ServletUtility.setDto(dto, req);
				ServletUtility.setSuccessMessage("Event Updated Successfully !!!", req);

			} catch (DuplicateRecordException dre) {
				ServletUtility.setDto(dto, req);
				ServletUtility.setErrorMessage("Event Code Already Exist !!!", req);

			} catch (DatabaseException de) {
				de.printStackTrace();
				ServletUtility.handleException(de, req, resp);
				return;

			} catch (ApplicationException ae) {
				ae.printStackTrace();
				ServletUtility.handleException(ae, req, resp);
				return;
			}

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.EVENT_CTL, req, resp);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.EVENT_LIST_CTL, req, resp);
			return;
		}

		ServletUtility.forward(getView(), req, resp);
	}

	@Override
	protected String getView() {
		return ORSView.EVENT_VIEW;
	}
}