package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.SecretDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.SecretModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "SecretCtl", urlPatterns = "/ctl/SecretCtl")
public class SecretCtl extends BaseCtl {

    @Override
    protected void preload(HttpServletRequest request) {

        Map<String, String> map = new HashMap<>();
        map.put("Active", "Active");
        map.put("Inactive", "Inactive");

        request.setAttribute("statusList", map);
    }

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;
        String op = request.getParameter("operation");

        if (OP_RESET.equalsIgnoreCase(op) || OP_CANCEL.equalsIgnoreCase(op)) {
            return pass;
        }

        if (DataValidator.isNull(request.getParameter("secretCode"))) {
            request.setAttribute("secretCode",
                    PropertyReader.getValue("error.require", "Secret Code"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("keyName"))) {
            request.setAttribute("keyName",
                    PropertyReader.getValue("error.require", "Key Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("secretValue"))) {
            request.setAttribute("secretValue",
                    PropertyReader.getValue("error.require", "Secret Value"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("status"))) {
            request.setAttribute("status",
                    PropertyReader.getValue("error.require", "Status"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        SecretDTO dto = new SecretDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setSecretCode(DataUtility.getString(request.getParameter("secretCode")));
        dto.setKeyName(DataUtility.getString(request.getParameter("keyName")));
        dto.setSecretValue(DataUtility.getString(request.getParameter("secretValue")));
        dto.setStatus(DataUtility.getString(request.getParameter("status")));

        populateBean(dto, request);

        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Long id = DataUtility.getLong(req.getParameter("id"));
        SecretModelInt model = ModelFactory.getInstance().getSecretModel();

        if (id > 0) {
            try {
                SecretDTO dto = model.findByPK(id);
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
        SecretModelInt model = ModelFactory.getInstance().getSecretModel();

        if (OP_SAVE.equalsIgnoreCase(op)) {

            SecretDTO dto = (SecretDTO) populateDTO(req);

            try {
                model.add(dto);

                ServletUtility.setDto(dto, req);
                ServletUtility.setSuccessMessage("Secret Added Successfully !!!", req);

            } catch (DuplicateRecordException dre) {

                ServletUtility.setDto(dto, req);
                ServletUtility.setErrorMessage("Secret Already Exist !!!", req);

            } catch (ApplicationException ae) {

                ae.printStackTrace();
                ServletUtility.handleException(ae, req, resp);
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            SecretDTO dto = (SecretDTO) populateDTO(req);

            try {
                model.update(dto);

                ServletUtility.setDto(dto, req);
                ServletUtility.setSuccessMessage("Secret Updated Successfully !!!", req);

            } catch (DuplicateRecordException dre) {

                ServletUtility.setDto(dto, req);
                ServletUtility.setErrorMessage("Secret Already Exist !!!", req);

            }  catch (ApplicationException ae) {

                ae.printStackTrace();
                ServletUtility.handleException(ae, req, resp);
                return;
            }

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.SECRET_CTL, req, resp);
            return;

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.SECRET_LIST_CTL, req, resp);
            return;
        }

        ServletUtility.forward(getView(), req, resp);
    }

    @Override
    protected String getView() {
        return ORSView.SECRET_VIEW;
    }
}