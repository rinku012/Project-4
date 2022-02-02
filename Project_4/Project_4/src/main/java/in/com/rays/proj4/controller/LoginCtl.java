package in.com.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.bean.RoleBean;
import in.com.rays.proj4.bean.UserBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.model.RoleModel;
import in.com.rays.proj4.model.UserModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.DataValidator;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

/**
 * Login functionality Controller. Performs operation for Login
 * 
 * @author Rinku
 *
 */
@WebServlet(name = "LoginCtl", urlPatterns = { "/LoginCtl" })
public class LoginCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;
	public static final String OP_REGISTER = "Register";
	public static final String OP_SIGN_IN = "SignIn";
	public static final String OP_SIGN_UP = "SignUp";
	public static final String OP_LOG_OUT = "logout";

	private static Logger log = Logger.getLogger(LoginCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("LoginCtl Method validate Started");

		boolean pass = true;
		System.out.println("validate of loginctl ");
		String op = request.getParameter("operation");
		if (OP_SIGN_UP.equals(op) || OP_LOG_OUT.equals(op)) {
			return pass;
		}

		String login = request.getParameter("login");

		if (DataValidator.isNull(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "Login Id"));
			pass = false;
			System.out.println(request.getParameter("login") + "validate of login is null");
		} else if (!DataValidator.isEmail(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login "));
			pass = false;

		}
		if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
			pass = false;
			System.out.println("validate of input validation on loginctl");
		}

		log.debug("LoginCtl Method validate Ended");

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("LoginCtl Method populatebean Started");

		UserBean bean = new UserBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));

		log.debug("LoginCtl Method populatebean Ended");
System.out.println("populatebean of loginctl" + bean);
		return bean;
	}

	/**
	 * Display Login form
	 *
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug(" Method doGet Started");

		HttpSession session = request.getSession(false);
		String op = DataUtility.getString(request.getParameter("operation"));
		System.out.println("login link op ");
		if (OP_LOG_OUT.equals(op) && !OP_SIGN_IN.equals(op)) {

			session.invalidate();
			ServletUtility.setSuccessMessage("User Logout Succesfully", request);
			ServletUtility.forward(getView(), request, response);
			return;
		} /*
			 * else{ ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
			 * return; }
			 */

		ServletUtility.forward(getView(), request, response);
		/*
		 * String op = DataUtility.getString(request.getParameter("operation"));
		 * 
		 * // get model UserModel model = new UserModel(); RoleModel role = new
		 * RoleModel();
		 * 
		 * long id = DataUtility.getLong(request.getParameter("id")); if (id > 0) {
		 * UserBean userbean; try { userbean = model.findByPK(id);
		 * ServletUtility.setBean(userbean, request); } catch (ApplicationException e) {
		 * log.error(e); ServletUtility.handleException(e, request, response); return; }
		 * } ServletUtility.forward(getView(), request, response);
		 * 
		 * log.debug("UserCtl Method doPost Ended");
		 */
	}

	/**
	 * Submitting or login action performing
	 *
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		log.debug(" Method doPost Started");
		System.out.println("dopost of loginctl");
		String op = DataUtility.getString(request.getParameter("operation"));
		System.out.println("(((((((((" + op);
		// get model
		UserModel model = new UserModel();
		RoleModel role = new RoleModel();

		long id = DataUtility.getInt(request.getParameter("id"));
		System.out.println("..........." + id);
		if (OP_SIGN_IN.equalsIgnoreCase(op)) {

			UserBean bean = (UserBean) populateBean(request);

			try {

				bean = model.authenticate(bean.getLogin(), bean.getPassword());
				System.out.println("///////" + bean);
				if (bean != null) {

					session.setAttribute("user", bean);

					long rollId = bean.getRoleId();
					System.out.println("my role id:- " + bean.getRoleId());
					System.out.println("roll id: " + rollId);

					RoleBean rolebean = role.findByPK(rollId);

					if (rolebean != null) {
						System.out.println(rolebean.getName() + "role  name ");
						session.setAttribute("role", rolebean.getName());
					}

					String str = (String) session.getAttribute("uri");
					System.out.println(".........." + str);
					System.out.println("URI---- " + str);

					if (str == null) {
						ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
						return;
					} else {
						ServletUtility.redirect(str, request, response);
						return;
					}

					/*
					 * ServletUtility.forward(ORSView.WELCOME_VIEW, request, response); return;
					 */
				} else {
					bean = (UserBean) populateBean(request);
					ServletUtility.setBean(bean, request);
					ServletUtility.setErrorMessage("Invalid LoginId And Password", request);
				}

			} catch (ApplicationException e) {
				// log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (Exception e) {

				e.printStackTrace();
			}

		} else if (OP_LOG_OUT.equals(op)) {

			// session = request.getSession();

			session.invalidate();

			ServletUtility.redirect(ORSView.LOGIN_CTL, request, response);

			return;

		} else if (OP_SIGN_UP.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);

		// log.debug("UserCtl Method doPost Ended");
	}

	@Override
	protected String getView() {
		return ORSView.LOGIN_VIEW;
	}

}
