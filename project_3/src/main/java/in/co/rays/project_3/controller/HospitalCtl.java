package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.HospitalDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.HospitalModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "HospitalCtl", urlPatterns = "/ctl/HospitalCtl")
public class HospitalCtl extends BaseCtl {

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;
        String op = request.getParameter("operation");

        if (OP_RESET.equalsIgnoreCase(op) || OP_CANCEL.equalsIgnoreCase(op)) {
            return pass;
        }

        if (DataValidator.isNull(request.getParameter("hospitalId"))) {
            request.setAttribute("hospitalId",
                    PropertyReader.getValue("error.require", "Hospital ID"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("hospitalName"))) {
            request.setAttribute("hospitalName",
                    PropertyReader.getValue("error.require", "Hospital Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("city"))) {
            request.setAttribute("city",
                    PropertyReader.getValue("error.require", "City"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("contactNumber"))) {
            request.setAttribute("contactNumber",
                    PropertyReader.getValue("error.require", "Contact Number"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        HospitalDTO dto = new HospitalDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setHospitalId(DataUtility.getString(request.getParameter("hospitalId")));
        dto.setHospitalName(DataUtility.getString(request.getParameter("hospitalName")));
        dto.setCity(DataUtility.getString(request.getParameter("city")));
        dto.setContactNumber(DataUtility.getString(request.getParameter("contactNumber")));

        populateBean(dto, request);

        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Long id = DataUtility.getLong(req.getParameter("id"));
        HospitalModelInt model = ModelFactory.getInstance().getHospitalModel();

        if (id > 0) {
            try {
                HospitalDTO dto = model.findByPK(id);
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
        HospitalModelInt model = ModelFactory.getInstance().getHospitalModel();

        if (OP_SAVE.equalsIgnoreCase(op)) {

            HospitalDTO bean = (HospitalDTO) populateDTO(req);

            try {
                model.add(bean);
                ServletUtility.setDto(bean, req);
                ServletUtility.setSuccessMessage("Hospital Added Successfully !!!", req);

            } catch (DuplicateRecordException dre) {
                ServletUtility.setDto(bean, req);
                ServletUtility.setErrorMessage("Hospital ID Already Exist !!!", req);

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

            HospitalDTO dto = (HospitalDTO) populateDTO(req);

            try {
                model.update(dto);
                ServletUtility.setDto(dto, req);
                ServletUtility.setSuccessMessage("Hospital Updated Successfully !!!", req);

            } catch (DuplicateRecordException dre) {
                ServletUtility.setDto(dto, req);
                ServletUtility.setErrorMessage("Hospital ID Already Exist !!!", req);

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

            ServletUtility.redirect(ORSView.HOSPITAL_CTL, req, resp);
            return;

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.HOSPITAL_LIST_CTL, req, resp);
            return;
        }

        ServletUtility.forward(getView(), req, resp);
    }

    @Override
    protected String getView() {
        return ORSView.HOSPITAL_VIEW;
    }
}