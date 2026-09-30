package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.ListenerDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ListenerModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "ListenerCtl", urlPatterns = "/ctl/ListenerCtl")
public class ListenerCtl extends BaseCtl {

  
    @Override
    protected void preload(HttpServletRequest request) {

        Map<String, String> map = new HashMap<>();
        map.put("ACTIVE", "ACTIVE");
        map.put("INACTIVE", "INACTIVE");

        request.setAttribute("statusList", map);
    }

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;
        String op = request.getParameter("operation");

        if (OP_RESET.equalsIgnoreCase(op) || OP_CANCEL.equalsIgnoreCase(op)) {
            return pass;
        }

        if (DataValidator.isNull(request.getParameter("listenerCode"))) {
            request.setAttribute("listenerCode",
                    PropertyReader.getValue("error.require", "Listener Code"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("queueName"))) {
            request.setAttribute("queueName",
                    PropertyReader.getValue("error.require", "Queue Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("consumerGroup"))) {
            request.setAttribute("consumerGroup",
                    PropertyReader.getValue("error.require", "Consumer Group"));
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

        ListenerDTO dto = new ListenerDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setListenerCode(DataUtility.getString(request.getParameter("listenerCode")));
        dto.setQueueName(DataUtility.getString(request.getParameter("queueName")));
        dto.setConsumerGroup(DataUtility.getString(request.getParameter("consumerGroup")));
        dto.setStatus(DataUtility.getString(request.getParameter("status")));

        populateBean(dto, request);

        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Long id = DataUtility.getLong(req.getParameter("id"));
        ListenerModelInt model = ModelFactory.getInstance().getListenerModel();

        if (id > 0) {
            try {
                ListenerDTO dto = model.findByPK(id);
                ServletUtility.setDto(dto, req);

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
        ListenerModelInt model = ModelFactory.getInstance().getListenerModel();

        if (OP_SAVE.equalsIgnoreCase(op)) {

            ListenerDTO dto = (ListenerDTO) populateDTO(req);

            try {
                model.add(dto);

                ServletUtility.setDto(dto, req);
                ServletUtility.setSuccessMessage("Listener Added Successfully !!!", req);

            } catch (DuplicateRecordException dre) {

                ServletUtility.setDto(dto, req);
                ServletUtility.setErrorMessage("Listener Code Already Exist !!!", req);

            } catch (ApplicationException ae) {

                ae.printStackTrace();
                ServletUtility.handleException(ae, req, resp);
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            ListenerDTO dto = (ListenerDTO) populateDTO(req);

            try {
                model.update(dto);

                ServletUtility.setDto(dto, req);
                ServletUtility.setSuccessMessage("Listener Updated Successfully !!!", req);

            } catch (DuplicateRecordException dre) {

                ServletUtility.setDto(dto, req);
                ServletUtility.setErrorMessage("Listener Code Already Exist !!!", req);

            } catch (ApplicationException ae) {

                ae.printStackTrace();
                ServletUtility.handleException(ae, req, resp);
                return;
            }

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.LISTENER_CTL, req, resp);
            return;

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.LISTENER_LIST_CTL, req, resp);
            return;
        }

        ServletUtility.forward(getView(), req, resp);
    }

    @Override
    protected String getView() {
        return ORSView.LISTENER_VIEW;
    }
}