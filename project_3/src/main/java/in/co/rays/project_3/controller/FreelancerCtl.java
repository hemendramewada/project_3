package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.FreelancerDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.model.FreelancerModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/FreelancerCtl" })
public class FreelancerCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(FreelancerCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("skill"))) {
			request.setAttribute("skill", PropertyReader.getValue("error.require", "Skill"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("experience"))) {
			request.setAttribute("experience", PropertyReader.getValue("error.require", "Experience"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("hourlyRate"))) {
			request.setAttribute("hourlyRate", PropertyReader.getValue("error.require", "Hourly Rate"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		FreelancerDTO dto = new FreelancerDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setName(DataUtility.getString(request.getParameter("name")));
		dto.setSkill(DataUtility.getString(request.getParameter("skill")));
		dto.setExperience(DataUtility.getInt(request.getParameter("experience")));
		dto.setHourlyRate(DataUtility.getDouble(request.getParameter("hourlyRate")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		long id = DataUtility.getLong(request.getParameter("id"));

		FreelancerModelInt model = ModelFactory.getInstance().getFreelancerModel();

		if (id > 0) {

			try {

				FreelancerDTO dto = model.findByPK(id);

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

		long id = DataUtility.getLong(request.getParameter("id"));

		FreelancerModelInt model = ModelFactory.getInstance().getFreelancerModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			FreelancerDTO dto = (FreelancerDTO) populateDTO(request);

			try {

				if (id > 0) {

					dto.setId(id);

					model.update(dto);

					ServletUtility.setDto(dto, request);

					ServletUtility.setSuccessMessage("Data Updated Successfully", request);

				} else {

					model.add(dto);

					ServletUtility.setDto(dto, request);

					ServletUtility.setSuccessMessage("Data Saved Successfully", request);
				}

			} catch (Exception e) {

				ServletUtility.setDto(dto, request);

				ServletUtility.setErrorMessage("Record already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			FreelancerDTO dto = (FreelancerDTO) populateDTO(request);

			try {

				model.delete(dto);

				ServletUtility.redirect(ORSView.FREELANCER_LIST_CTL, request, response);

				return;

			} catch (ApplicationException e) {

				log.error(e);

				ServletUtility.handleException(e, request, response);

				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.FREELANCER_LIST_CTL, request, response);

			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.FREELANCER_CTL, request, response);

			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {

		return ORSView.FREELANCER_VIEW;
	}
}