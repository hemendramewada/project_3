package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.DonationDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.DonationModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "DonationCtl", urlPatterns = "/ctl/DonationCtl")
public class DonationCtl extends BaseCtl {

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;
        String op = request.getParameter("operation");

        if (OP_RESET.equalsIgnoreCase(op) || OP_CANCEL.equalsIgnoreCase(op)) {
            return pass;
        }
        if (DataValidator.isName(request.getParameter("donorName"))) {
			request.setAttribute("donorName", PropertyReader.getValue("error.require","Donor Name"));
			pass = false ;
		}

        if (DataValidator.isNull(request.getParameter("donorName"))) {
            request.setAttribute("donorName",
                    PropertyReader.getValue("error.require", "Donor Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("amount"))) {
            request.setAttribute("amount",
                    PropertyReader.getValue("error.require", "Amount"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("donationDate"))) {
            request.setAttribute("donationDate",
                    PropertyReader.getValue("error.require", "Donation Date"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        DonationDTO dto = new DonationDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setDonorName(DataUtility.getString(request.getParameter("donorName")));
        dto.setAmount(DataUtility.getLong(request.getParameter("amount")));
        dto.setDonationDate(DataUtility.getDate(request.getParameter("donationDate")));

        populateBean(dto, request);

        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Long id = DataUtility.getLong(req.getParameter("id"));
        DonationModelInt model = ModelFactory.getInstance().getDonationModel();

        if (id > 0) {
            try {
                DonationDTO dto = model.findByPK(id);
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
        DonationModelInt model = ModelFactory.getInstance().getDonationModel();

        if (OP_SAVE.equalsIgnoreCase(op)) {

            DonationDTO bean = (DonationDTO) populateDTO(req);

            try {
                model.add(bean);
                ServletUtility.setDto(bean, req);
                ServletUtility.setSuccessMessage("Donation Added Successfully !!!", req);

            } catch (DuplicateRecordException dre) {
                ServletUtility.setDto(bean, req);
                ServletUtility.setErrorMessage("Donor Name Already Exist !!!", req);

            }  catch (ApplicationException ae) {
                ae.printStackTrace();
                ServletUtility.handleException(ae, req, resp);
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            DonationDTO dto = (DonationDTO) populateDTO(req);

            try {
                model.update(dto);
                ServletUtility.setDto(dto, req);
                ServletUtility.setSuccessMessage("Donation Updated Successfully !!!", req);

            } catch (DuplicateRecordException dre) {
                ServletUtility.setDto(dto, req);
                ServletUtility.setErrorMessage("Donor Name Already Exist !!!", req);

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

            ServletUtility.redirect(ORSView.DONATION_CTL, req, resp);
            return;

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.DONATION_LIST_CTL, req, resp);
            return;
        }

        ServletUtility.forward(getView(), req, resp);
    }

    @Override
    protected String getView() {
        return ORSView.DONATION_VIEW;
    }
}