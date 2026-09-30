package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.VoiceCommandDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.VoiceCommandModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * Voice Command List functionality controller.
 * To perform Search and List operation.
 *
 * @author Rajendra Singh
 */

@WebServlet(name = "VoiceCommandListCtl", urlPatterns = { "/ctl/VoiceCommandListCtl" })

public class VoiceCommandListCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(VoiceCommandListCtl.class);

	/**
	 * Preload
	 */
	@Override
	protected void preload(HttpServletRequest request) {

		// No preload required

	}

	/**
	 * Populate DTO
	 */
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		VoiceCommandDTO dto = new VoiceCommandDTO();

		dto.setCommandCode(DataUtility.getString(request.getParameter("commandCode")));

		dto.setUserName(DataUtility.getString(request.getParameter("userName")));

		dto.setCommandText(DataUtility.getString(request.getParameter("commandText")));

		dto.setStatus(DataUtility.getString(request.getParameter("status")));

		populateBean(dto, request);

		return dto;
	}

	/**
	 * Display Logic
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("VoiceCommandListCtl doGet Start");

		List list = null;
		List next = null;

		int pageNo = 1;

		int pageSize =
				DataUtility.getInt(PropertyReader.getValue("page.size"));

		VoiceCommandDTO dto =
				(VoiceCommandDTO) populateDTO(request);

		VoiceCommandModelInt model =
				ModelFactory.getInstance().getVoiceCommandModel();

		try {

			list = model.search(dto, pageNo, pageSize);

			next = model.search(dto, pageNo + 1, pageSize);

			if (list == null || list.size() == 0) {

				ServletUtility.setErrorMessage(
						"No record found",
						request);
			}

			if (next == null || next.size() == 0) {

				request.setAttribute("nextListSize", 0);

			} else {

				request.setAttribute("nextListSize",
						next.size());
			}

			ServletUtility.setList(list, request);

			ServletUtility.setPageNo(pageNo, request);

			ServletUtility.setPageSize(pageSize, request);

			ServletUtility.forward(getView(),
					request,
					response);

		} catch (ApplicationException e) {

			log.error(e);

			ServletUtility.handleException(
					e,
					request,
					response);

			return;

		} catch (Exception e) {

			e.printStackTrace();
		}

		log.debug("VoiceCommandListCtl doGet End");
	}
	/**
	 * Submit Logic
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("VoiceCommandListCtl doPost Start");

		List list = null;
		List next = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0)
				? DataUtility.getInt(PropertyReader.getValue("page.size"))
				: pageSize;

		VoiceCommandDTO dto =
				(VoiceCommandDTO) populateDTO(request);

		String op = DataUtility.getString(request.getParameter("operation"));

		String[] ids = request.getParameterValues("ids");

		VoiceCommandModelInt model =
				ModelFactory.getInstance().getVoiceCommandModel();

		try {

			if (OP_SEARCH.equalsIgnoreCase(op)
					|| OP_NEXT.equalsIgnoreCase(op)
					|| OP_PREVIOUS.equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {

					pageNo = 1;

				} else if (OP_NEXT.equalsIgnoreCase(op)) {

					pageNo++;

				} else if (OP_PREVIOUS.equalsIgnoreCase(op)
						&& pageNo > 1) {

					pageNo--;
				}

			} else if (OP_NEW.equalsIgnoreCase(op)) {

				ServletUtility.redirect(
						ORSView.VOICE_COMMAND_CTL,
						request,
						response);
				return;

			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(
						ORSView.VOICE_COMMAND_LIST_CTL,
						request,
						response);
				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {

				pageNo = 1;

				if (ids != null && ids.length > 0) {

					VoiceCommandDTO deleteDto = new VoiceCommandDTO();

					for (String id : ids) {

						deleteDto.setCommandId(
								DataUtility.getLong(id));

						model.delete(deleteDto);
					}

					ServletUtility.setSuccessMessage(
							"Data Successfully Deleted!",
							request);

				} else {

					ServletUtility.setErrorMessage(
							"Select atleast one record",
							request);
				}
			}

			if (OP_BACK.equalsIgnoreCase(op)) {

				ServletUtility.redirect(
						ORSView.VOICE_COMMAND_LIST_CTL,
						request,
						response);
				return;
			}

			dto = (VoiceCommandDTO) populateDTO(request);

			list = model.search(dto, pageNo, pageSize);

			next = model.search(dto, pageNo + 1, pageSize);

			ServletUtility.setDto(dto, request);

			if (list == null || list.size() == 0) {

				if (!OP_DELETE.equalsIgnoreCase(op)) {

					ServletUtility.setErrorMessage(
							"No record found",
							request);
				}
			}

			if (next == null || next.size() == 0) {

				request.setAttribute("nextListSize", 0);

			} else {

				request.setAttribute("nextListSize",
						next.size());
			}

			ServletUtility.setList(list, request);

			ServletUtility.setPageNo(pageNo, request);

			ServletUtility.setPageSize(pageSize, request);

			ServletUtility.forward(getView(),
					request,
					response);

		} catch (ApplicationException e) {

			log.error(e);

			ServletUtility.handleException(
					e,
					request,
					response);

			return;

		} catch (Exception e) {

			e.printStackTrace();
		}

		log.debug("VoiceCommandListCtl doPost End");
	}

	/**
	 * Return View
	 */
	@Override
	protected String getView() {

		return ORSView.VOICE_COMMAND_LIST_VIEW;
	}

}