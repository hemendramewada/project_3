package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.PlacementDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.PlacementModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "PlacementCtl", urlPatterns = "/ctl/PlacementCtl")
public class PlacementCtl extends BaseCtl {

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;
        String op = request.getParameter("operation");

        if (OP_CANCEL.equalsIgnoreCase(op) || OP_RESET.equalsIgnoreCase(op)) {
            return pass;
        }

        if (DataValidator.isNull(request.getParameter("placementCode"))) {
            request.setAttribute("placementCode",
                    PropertyReader.getValue("error.require", "Placement Code"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("studentName"))) {
            request.setAttribute("studentName",
                    PropertyReader.getValue("error.require", "Student Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("companyName"))) {
            request.setAttribute("companyName",
                    PropertyReader.getValue("error.require", "Company Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("placementStatus"))) {
            request.setAttribute("placementStatus",
                    PropertyReader.getValue("error.require", "Placement Status"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        PlacementDTO dto = new PlacementDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setPlacementCode(DataUtility.getString(request.getParameter("placementCode")));
        dto.setStudentName(DataUtility.getString(request.getParameter("studentName")));
        dto.setCompanyName(DataUtility.getString(request.getParameter("companyName")));
        dto.setPlacementStatus(DataUtility.getString(request.getParameter("placementStatus")));

        populateBean(dto, request);

        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Long id = DataUtility.getLong(req.getParameter("id"));
        PlacementModelInt model = ModelFactory.getInstance().getPlacementModel();

        if (id > 0) {
            try {
                PlacementDTO dto = model.findByPK(id);
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String op = DataUtility.getString(req.getParameter("operation"));
        PlacementModelInt model = ModelFactory.getInstance().getPlacementModel();

        if (OP_SAVE.equalsIgnoreCase(op)) {

            PlacementDTO bean = (PlacementDTO) populateDTO(req);

            try {
                model.add(bean);
                ServletUtility.setDto(bean, req);
                ServletUtility.setSuccessMessage("Placement Added Successfully !!!", req);

            } catch (DuplicateRecordException dre) {
                ServletUtility.setDto(bean, req);
                ServletUtility.setErrorMessage("Placement Code Already Exist !!!", req);

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

            PlacementDTO dto = (PlacementDTO) populateDTO(req);

            try {
                model.update(dto);
                ServletUtility.setDto(dto, req);
                ServletUtility.setSuccessMessage("Placement Updated Successfully !!!", req);

            } catch (DuplicateRecordException dre) {
                ServletUtility.setDto(dto, req);
                ServletUtility.setErrorMessage("Placement Code Already Exist !!!", req);

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

            ServletUtility.redirect(ORSView.PLACEMENT_CTL, req, resp);
            return;

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.PLACEMENT_LIST_CTL, req, resp);
            return;
        }

        ServletUtility.forward(getView(), req, resp);
    }

    @Override
    protected String getView() {
        return ORSView.PLACEMENT_VIEW;
    }
}