package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.LanguageDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.LanguageModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "LanguageCtl", urlPatterns = "/ctl/LanguageCtl")
public class LanguageCtl extends BaseCtl {

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;
        String op = request.getParameter("operation");

        if (OP_CANCEL.equalsIgnoreCase(op) || OP_RESET.equalsIgnoreCase(op)) {
            return pass;
        }

        if (DataValidator.isNull(request.getParameter("languageCode"))) {
            request.setAttribute("languageCode",
                    PropertyReader.getValue("error.require", "Language Code"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("languageName"))) {
            request.setAttribute("languageName",
                    PropertyReader.getValue("error.require", "Language Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("direction"))) {
            request.setAttribute("direction",
                    PropertyReader.getValue("error.require", "Direction"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("languageStatus"))) {
            request.setAttribute("languageStatus",
                    PropertyReader.getValue("error.require", "Language Status"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        LanguageDTO dto = new LanguageDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setLanguageCode(DataUtility.getString(request.getParameter("languageCode")));
        dto.setLanguageName(DataUtility.getString(request.getParameter("languageName")));
        dto.setDirection(DataUtility.getString(request.getParameter("direction")));
        dto.setLanguageStatus(DataUtility.getString(request.getParameter("languageStatus")));

        populateBean(dto, request);

        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Long id = DataUtility.getLong(req.getParameter("id"));
        LanguageModelInt model = ModelFactory.getInstance().getLanguageModel();

        if (id > 0) {
            try {
                LanguageDTO dto = model.findByPK(id);
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
        LanguageModelInt model = ModelFactory.getInstance().getLanguageModel();

        if (OP_SAVE.equalsIgnoreCase(op)) {

            LanguageDTO bean = (LanguageDTO) populateDTO(req);

            try {
                model.add(bean);
                ServletUtility.setDto(bean, req);
                ServletUtility.setSuccessMessage("Language Added Successfully !!!", req);

            } catch (DuplicateRecordException dre) {
                ServletUtility.setDto(bean, req);
                ServletUtility.setErrorMessage("Language Code Already Exist !!!", req);

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

            LanguageDTO dto = (LanguageDTO) populateDTO(req);

            try {
                model.update(dto);
                ServletUtility.setDto(dto, req);
                ServletUtility.setSuccessMessage("Language Updated Successfully !!!", req);

            } catch (DuplicateRecordException dre) {
                ServletUtility.setDto(dto, req);
                ServletUtility.setErrorMessage("Language Code Already Exist !!!", req);

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

            ServletUtility.redirect(ORSView.LANGUAGE_CTL, req, resp);
            return;

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.LANGUAGE_LIST_CTL, req, resp);
            return;
        }

        ServletUtility.forward(getView(), req, resp);
    }

    @Override
    protected String getView() {
        return ORSView.LANGUAGE_VIEW;
    }
}