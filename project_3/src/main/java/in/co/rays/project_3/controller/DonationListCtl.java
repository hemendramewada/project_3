package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.DonationDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.DonationModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "DonationListCtl", urlPatterns = "/ctl/DonationListCtl")
public class DonationListCtl extends BaseCtl {

    @Override
    protected void preload(HttpServletRequest request) {

        DonationModelInt model = ModelFactory.getInstance().getDonationModel();

        try {
            List donationList = model.list();
            request.setAttribute("donationList", donationList);

        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int pageNo = 1;
        int pageSize = DataUtility.getInt(
                request.getParameter("pageSize") == null ? "5" : request.getParameter("pageSize"));

        DonationDTO dto = new DonationDTO();

        DonationModelInt model = ModelFactory.getInstance().getDonationModel();

        try {
            List list = model.search(dto, pageNo, pageSize);
            List nextList = model.search(dto, pageNo + 1, pageSize);

            request.setAttribute("list", list);

            if (nextList == null || nextList.size() == 0) {
                request.setAttribute("nextListSize", 0);
            } else {
                request.setAttribute("nextListSize", nextList.size());
            }

            request.setAttribute("pageNo", pageNo);
            request.setAttribute("pageSize", pageSize);

        } catch (DatabaseException e) {
            e.printStackTrace();
            ServletUtility.handleExceptionDBDown(e, request, response, getView());
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int intPageSize = DataUtility.getInt(request.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        intPageSize = (intPageSize == 0) ? 5 : intPageSize;

        String op = DataUtility.getString(request.getParameter("operation"));

        DonationDTO dto = new DonationDTO();
        dto.setDonorName(DataUtility.getString(request.getParameter("donorName")));

        DonationModelInt model = ModelFactory.getInstance().getDonationModel();

        if (OP_SEARCH.equalsIgnoreCase(op)) {
            pageNo = 1;

        } else if (OP_NEXT.equalsIgnoreCase(op)) {
            pageNo++;

        } else if (OP_PREVIOUS.equalsIgnoreCase(op)) {
            pageNo--;

        } else if (OP_NEW.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.DONATION_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.DONATION_LIST_CTL, request, response);
            return;

        } else if (OP_DELETE.equalsIgnoreCase(op)) {

            String[] ids = request.getParameterValues("ids");

            if (ids != null) {

                for (String id : ids) {
                    DonationDTO deleteDto = new DonationDTO();
                    deleteDto.setId(DataUtility.getLong(id));

                    try {
                        model.delete(deleteDto);
                    } catch (DatabaseException e) {
                        e.printStackTrace();
                        ServletUtility.handleException(e, request, response);
                        return;
                    }
                }

                ServletUtility.setSuccessMessage("Donation Deleted Successfully", request);

            } else {
                ServletUtility.setErrorMessage("Select at least one record", request);
            }
        }

        try {
            List list = model.search(dto, pageNo, intPageSize);
            List nextList = model.search(dto, pageNo + 1, intPageSize);

            request.setAttribute("list", list);

            if (nextList == null || nextList.size() == 0) {
                request.setAttribute("nextListSize", 0);
            } else {
                request.setAttribute("nextListSize", nextList.size());
            }

            request.setAttribute("pageNo", pageNo);
            request.setAttribute("pageSize", intPageSize);

            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
            e.printStackTrace();
            ServletUtility.handleException(e, request, response);
        }
    }

    @Override
    protected String getView() {
        return ORSView.DONATION_LIST_VIEW;
    }
}