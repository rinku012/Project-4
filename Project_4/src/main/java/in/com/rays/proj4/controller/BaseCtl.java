package in.com.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.bean.UserBean;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.DataValidator;
import in.com.rays.proj4.util.ServletUtility;

/**
 * Base controller class of project. It contain (1) Generic operations (2)
 * Generic constants (3) Generic work flow
 * 
 * @author Rinku
 * @version 1.0 Copyright (c) SunilOS
 */

public abstract class BaseCtl extends HttpServlet {

	/** The Constant OP_SAVE. */
	public static final String OP_SAVE = "Save";

	/** The Constant OP_CANCEL. */
	public static final String OP_CANCEL = "Cancel";

	/** The Constant OP_UPDATE. */
	public static final String OP_UPDATE = "Update";

	/** The Constant OP_RESET. */
	public static final String OP_RESET = "Reset";

	/** The Constant OP_LIST. */
	public static final String OP_LIST = "List";
	/** The Constant OP_SEARCH. */
	public static final String OP_SEARCH = "Search";

	/** The Constant OP_VIEW. */
	public static final String OP_VIEW = "View";

	/** The Constant OP_NEXT. */
	public static final String OP_NEXT = "Next";

	/** The Constant OP_PREVIOUS. */
	public static final String OP_PREVIOUS = "Previous";

	/** The Constant OP_NEW. */
	public static final String OP_NEW = "New";

	/** The Constant OP_DELETE. */
	public static final String OP_DELETE = "Delete";

	/** The Constant OP_GO. */
	public static final String OP_GO = "Go";

	/** The Constant OP_BACK. */
	public static final String OP_BACK = "Back";
//	public static final String OP_LOG_OUT = "Logout";

	/** Success message key constant. */
	public static final String MSG_SUCCESS = "success";

	/** Error message key constant. */
	public static final String MSG_ERROR = "error";

	/**
	 * Validates input data entered by User.
	 *
	 * @param request the request
	 * @return true, if successful
	 */
	protected boolean validate(HttpServletRequest request) {

		System.out.println("This is Validate method of BaseCtl");

		return true;
	}

	/**
	 * Loads list and other data required to display at HTML form.
	 *
	 * @param request the request
	 */
	protected void preload(HttpServletRequest request) {
		System.out.println("Preload method of baseCtl");
	}

	/**
	 * Populates bean object from request parameters.
	 *
	 * @param request the request
	 * @return the base bean
	 */
	protected BaseBean populateBean(HttpServletRequest request) {
		System.out.println("Populate bean of baseCtl");

		return null;
	}

	/**
	 * Populates Generic attributes in DTO.
	 *
	 * @param dto     the dto
	 * @param request the request
	 * @return object of the bean
	 */
	protected BaseBean populateDTO(BaseBean dto, HttpServletRequest request) {
		System.out.println("PopulateDto of BaseCtl");
		String createdBy = request.getParameter("createdBy");
		String modifiedBy = null;

		UserBean userbean = (UserBean) request.getSession().getAttribute("user");
		if (userbean == null) {
			// If record is created without login
			createdBy = "root";
			modifiedBy = "root";
		} else {
			modifiedBy = userbean.getLogin();
			// If record is created first time
			if ("null".equalsIgnoreCase(createdBy) || DataValidator.isNull(createdBy)) {
				createdBy = modifiedBy;
			}
		}
		dto.setCreatedBy(createdBy);
		dto.setModifiedBy(modifiedBy);

		long cdt = DataUtility.getLong(request.getParameter("createdDatetime"));
		if (cdt > 0) {
			dto.setCreatedDatetime(DataUtility.getTimestamp(cdt));
		} else {
			dto.setCreatedDatetime(DataUtility.getCurrentTimestamp());
		}
		dto.setModifiedDatetime(DataUtility.getCurrentTimestamp());
		return dto;
	}

	/**
	 * Call the service method of this Controller.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println(" service mathod of basectl");
		// Load the preloaded data required to display at HTML form
		preload(request);
		System.out.println(" preload of service mathod");
		String op = DataUtility.getString(request.getParameter("operation"));
		System.out.println("-----------" + op + "operation get in basectl");
		// Check if operation is not DELETE, VIEW, CANCEL, and NULL then
		// perform input data validation
		System.out.println(" operation of  basectl");
		if (DataValidator.isNotNull(op) && !OP_CANCEL.equalsIgnoreCase(op) && !OP_VIEW.equalsIgnoreCase(op)
				&& !OP_DELETE.equalsIgnoreCase(op) && !OP_RESET.equalsIgnoreCase(op)) {
			// Check validation, If fail then send back to page with error
			// messages
			System.out.println(" operation cheks of basctl");
			if (!validate(request)) {

				BaseBean bean = (BaseBean) populateBean(request);
				System.out.println("=======-----======) populate of service))" + bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.forward(getView(), request, response);
				return;

			}
		}
		System.out.println("before super service------------------->>>>>>>>");
		super.service(request, response);
		System.out.println("after super service------------------->>>>>>>>");

	}

	/**
	 * Returns the VIEW page of this Controller.
	 *
	 * @return the view
	 */

	protected abstract String getView();

}
