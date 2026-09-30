package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.PortfolioDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.PortfolioModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "PortfolioCtl", urlPatterns = "/ctl/PortfolioCtl")
public class PortfolioCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;
		String op = request.getParameter("operation");

		if (OP_RESET.equalsIgnoreCase(op) || OP_CANCEL.equalsIgnoreCase(op)) {
			return pass;
		}

		if (DataValidator.isNull(request.getParameter("portfolioName"))) {
			request.setAttribute("portfolioName",
					PropertyReader.getValue("error.require", "Portfolio Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("totalValue"))) {
			request.setAttribute("totalValue",
					PropertyReader.getValue("error.require", "Total Value"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("createdDate"))) {
			request.setAttribute("createdDate",
					PropertyReader.getValue("error.require", "Created Date"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		PortfolioDTO dto = new PortfolioDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setPortfolioName(DataUtility.getString(request.getParameter("portfolioName")));
		dto.setTotalValue(DataUtility.getInt(request.getParameter("totalValue")));
		dto.setCreatedDate(DataUtility.getDate(request.getParameter("createdDate")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Long id = DataUtility.getLong(req.getParameter("id"));
		PortfolioModelInt model = ModelFactory.getInstance().getPortfolioModel();

		if (id > 0) {
			try {
				PortfolioDTO dto = model.findByPK(id);
				ServletUtility.setDto(dto, req);

			}  catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, req, resp);
				return;
			}
		}

		ServletUtility.forward(getView(), req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String op = DataUtility.getString(req.getParameter("operation"));
		PortfolioModelInt model = ModelFactory.getInstance().getPortfolioModel();

		if (OP_SAVE.equalsIgnoreCase(op)) {

			PortfolioDTO bean = (PortfolioDTO) populateDTO(req);

			try {
				model.add(bean);
				ServletUtility.setDto(bean, req);
				ServletUtility.setSuccessMessage("Portfolio Added Successfully !!!", req);

			} catch (DuplicateRecordException dre) {
				ServletUtility.setDto(bean, req);
				ServletUtility.setErrorMessage("Portfolio Name Already Exist !!!", req);

			}  catch (ApplicationException ae) {
				ae.printStackTrace();
				ServletUtility.handleException(ae, req, resp);
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			PortfolioDTO dto = (PortfolioDTO) populateDTO(req);

			try {
				model.update(dto);
				ServletUtility.setDto(dto, req);
				ServletUtility.setSuccessMessage("Portfolio Updated Successfully !!!", req);

			} catch (DuplicateRecordException dre) {
				ServletUtility.setDto(dto, req);
				ServletUtility.setErrorMessage("Portfolio Name Already Exist !!!", req);

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

			ServletUtility.redirect(ORSView.PORTFOLIO_CTL, req, resp);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.PORTFOLIO_LIST_CTL, req, resp);
			return;
		}

		ServletUtility.forward(getView(), req, resp);
	}

	@Override
	protected String getView() {
		return ORSView.PORTFOLIO_VIEW;
	}
}