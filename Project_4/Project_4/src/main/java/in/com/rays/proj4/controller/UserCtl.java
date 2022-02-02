package in.com.rays.proj4.controller;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.bean.UserBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.RoleModel;
import in.com.rays.proj4.model.UserModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.DataValidator;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

/**
 * @author Rinku
 *
 */
@WebServlet(name = "UserCtl", urlPatterns = { "/ctl/UserCtl" })
public class UserCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(UserCtl.class);

	@Override
	protected void preload(HttpServletRequest request) {

		RoleModel model = new RoleModel();
		try {

			List l = model.list();
			request.setAttribute("roleList", l);

		} catch (ApplicationException e) {
			log.error(e);
		}

	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("userCTl method started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("firstName"))) {

			request.setAttribute("firstName", PropertyReader.getValue("error.require", "FIRST NAME"));
			pass = false;
		} else if (!DataValidator.isValidName(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "invalid first"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error require", "LAST NAME"));
			pass = false;

		} else if (!DataValidator.isNotNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error require", "invalid LAST NAME"));
			pass = false;

		}
		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "MOBILE NO"));
			pass = false;
		} else if (!DataValidator.isMobileNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "mobileNo is must be 10 digit and no. series start by 6-9");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "LOGIN"));
			pass = false;
		} else if (DataValidator.isNotNull(request.getParameter("login"))
				&& !DataValidator.isEmail(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Invalid"));
			pass = false;

		}

		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0) {

		} else {
			if (DataValidator.isNull(request.getParameter("password"))) {
				request.setAttribute("password", PropertyReader.getValue("error require", "password"));
				pass = false;
			} else if (!DataValidator.isPassword(request.getParameter("password"))) {
				request.setAttribute("password",
						"password contain 8 lettrs with alpha-numeric,capital latter & special Character");
				pass = false;
			}

			if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
				request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm Password"));
				pass = false;
			} else if (!DataValidator.isPassword(request.getParameter("password"))) {
				request.setAttribute("password", "Password contain 8 letters with alpha-numeric & special Character");
				pass = false;
			} else if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))) {
				request.setAttribute("confirmPassword", "New password and Confirm password must be same!!");
				pass = false;
			}
		}
		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}
		/*
		 * if (DataValidator.isNull(request.getParameter("roleId"))) {
		 * request.setAttribute("roleId", PropertyReader.getValue("error.require",
		 * "RoleName")); pass = false; } if (DataValidator.isNull("dob")) {
		 * 
		 * request.setAttribute("dob", PropertyReader.getValue("error.require", "dob"));
		 * pass = false; }
		 */
		
		/*
		 * else if (!DataValidator.isDate("dob")) {
		 * System.out.println("dob -- "+request.getParameter("dob"));
		 * request.setAttribute("dob", PropertyReader.getValue("error.date", "dob"));
		 * pass = false; }
		 */
		 
		if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))
				&& !" ".equals(request.getParameter("confirmPassword"))) {
			ServletUtility.setErrorMessage("Confirm Password should be matched", request);
			pass = false;
		} else if (!request.getParameter("confirmPassword").equals(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", "New password and Confirm password must be same!!");
			pass = false;
		}

		log.debug("UserCtl Method valdate Enbded");

		return pass;
	}

	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("UserCtl Method populatebean Started");

		UserBean bean = new UserBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setRoleId(DataUtility.getLong(request.getParameter("roleId")));

		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));

		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));

		bean.setLogin(DataUtility.getString(request.getParameter("login")));

		bean.setPassword(DataUtility.getString(request.getParameter("password")));

		bean.setConfirmPassword(DataUtility.getString(request.getParameter("comfirmPassword")));

		bean.setGender(DataUtility.getString(request.getParameter("gender")));

		bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));

		bean.setDob(DataUtility.getDate(request.getParameter("dob"))); 

		System.out.println("Populate bean ki dob: " + request.getParameter("dob"));

		populateDTO(bean, request);

		log.debug("UserCtl Method PopulateBean Ended");

		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("UseCtl Method doget Started");
		String op = DataUtility.getString(request.getParameter("operation"));

		// get model
		UserModel model = new UserModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			System.out.println("in id > 0 condition");
			UserBean bean;

			try {
				bean = model.findByPK(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		}
		ServletUtility.forward(getView(), request, response);
		log.debug("UseCtl Method doget Ended");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("UserCtl Method dopost Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		UserModel model = new UserModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			UserBean bean = (UserBean) populateBean(request);
			try {
				if (id > 0) {

					model.update(bean);
					ServletUtility.setBean(bean, request);

					ServletUtility.setSuccessMessage("user is successfully updated", request);
				} else {

					long pk = model.add(bean);
					System.out.println("add compalete================.............................");
					// bean.setId(pk);
				    // ServletUtility.setBean(bean, request);

					ServletUtility.setSuccessMessage("User is successfully Added", request);
					ServletUtility.forward(getView(), request, response);
					bean.setId(pk);
				}
				/*
				 * ServletUtility.setBean(bean, request);
				 * ServletUtility.setSuccessMessage("User is successfully saved", request);
				 */

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
			}
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
			return;
		}
		else if (OP_DELETE.equalsIgnoreCase(op)) {

			UserBean bean = (UserBean) populateBean(request);
			try {
				model.delete(bean);
				ServletUtility.redirect(ORSView.USER_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.USER_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

		log.debug("UserCtl Method doPostEnded");

	}

	@Override
	protected String getView() {

		return ORSView.USER_VIEW;
	}

}
