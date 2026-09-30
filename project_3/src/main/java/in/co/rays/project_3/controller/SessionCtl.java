package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.SessionDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.SessionModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "SessionCtl", urlPatterns = "/ctl/SessionCtl")
public class SessionCtl extends BaseCtl {

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;
        String op = request.getParameter("operation");

        if (OP_LOG_OUT.equalsIgnoreCase(op) || OP_RESET.equalsIgnoreCase(op)) {
            return pass;
        }

        if (DataValidator.isNull(request.getParameter("sessionToken"))) {
            request.setAttribute("sessionToken",
                    PropertyReader.getValue("error.require", "Session Token"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("userName"))) {
            request.setAttribute("userName",
                    PropertyReader.getValue("error.require", "User Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("loginTime"))) {
            request.setAttribute("loginTime",
                    PropertyReader.getValue("error.require", "Login Time"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("sessionStatus"))) {
            request.setAttribute("sessionStatus",
                    PropertyReader.getValue("error.require", "Session Status"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        SessionDTO dto = new SessionDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setSessionToken(DataUtility.getString(request.getParameter("sessionToken")));
        dto.setUserName(DataUtility.getString(request.getParameter("userName")));

        //  If using LocalDateTime, handle carefully
        dto.setLoginTime(DataUtility.getDate(request.getParameter("loginTime")));
        dto.setLogoutTime(DataUtility.getDate(request.getParameter("logoutTime")));

        dto.setSessionStatus(DataUtility.getString(request.getParameter("sessionStatus")));

        populateBean(dto, request);

        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Long id = DataUtility.getLong(req.getParameter("id"));
        SessionModelInt model = ModelFactory.getInstance().getSessionModel();

        if (id > 0) {
            try {
                SessionDTO dto = model.findByPK(id);
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
        SessionModelInt model = ModelFactory.getInstance().getSessionModel();

        if (OP_SAVE.equalsIgnoreCase(op)) {

            SessionDTO bean = (SessionDTO) populateDTO(req);

            try {
                model.add(bean);
                ServletUtility.setDto(bean, req);
                ServletUtility.setSuccessMessage("Session Added Successfully !!!", req);

            } catch (DuplicateRecordException dre) {
                ServletUtility.setDto(bean, req);
                ServletUtility.setErrorMessage("Session Token Already Exist !!!", req);

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

            SessionDTO dto = (SessionDTO) populateDTO(req);

            try {
                model.update(dto);
                ServletUtility.setDto(dto, req);
                ServletUtility.setSuccessMessage("Session Updated Successfully !!!", req);

            } catch (DuplicateRecordException dre) {
                ServletUtility.setDto(dto, req);
                ServletUtility.setErrorMessage("Session Token Already Exist !!!", req);

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

            ServletUtility.redirect(ORSView.SESSION_CTL, req, resp);
            return;

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.SESSION_LIST_CTL, req, resp);
            return;
        }

        ServletUtility.forward(getView(), req, resp);
    }

    @Override
    protected String getView() {
        return ORSView.SESSION_VIEW;
    }
}