package in.com.rays.proj4.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.bean.UserBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.UserModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.DataValidator;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

/**
 * @author Rinku
 *
 */
@WebServlet(name = "MyProfileCtl", urlPatterns = { "/ctl/MyProfileCtl" })
public class MyProfileCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	public static final String OP_CHANGE_MY_PASSWORD = "Change Password";

	private static Logger log = Logger.getLogger(MyProfileCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("MyProfileCtl method validate Started");
		boolean pass = true;
		String op = DataUtility.getString(request.getParameter("operation"));

		if (OP_CHANGE_MY_PASSWORD.equalsIgnoreCase(op)) {
			return pass;
		}

		if (DataValidator.isNull(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "Invalid First Name"));
			pass = false;

		} else if (!DataValidator.isValidName(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.name", "Invalid first"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastname", PropertyReader.getValue("error.require", "Last Name"));
			pass = false;
		} else if (!DataValidator.isValidName(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.name", "Invalid First"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error require", "Gender"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.mobile", "Invalid"));
			pass = false;
		} else if (!DataValidator.isMobileNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.mobile", "Invalid"));
			pass = false;

		}
		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Birth"));
			pass = false;
		}

		log.debug("MyProfileCtl Method validate Ended");
		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("MProfileCtl Method POpulatebean Started");

		UserBean bean = new UserBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
		bean.setGender(DataUtility.getString(request.getParameter("gender")));
		bean.setDob(DataUtility.getDate(request.getParameter("dob")));

		populateDTO(bean, request);
		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		log.debug("MYProfileCyl method doget started");

		UserBean UserBean = (UserBean) session.getAttribute("user");

		long id = UserBean.getId();

		String op = DataUtility.getString(request.getParameter("operation"));

		// get model

		UserModel model = new UserModel();

		if (id > 0 || op != null) {

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
		log.debug("MyporofileCtl Method doget ended");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
          System.out.println("this is dopost of myprofile//////////");
		HttpSession session = request.getSession();
		log.debug("MyprofileCtl Method dopost Started");

		UserBean UserBean = (UserBean) session.getAttribute("user");
		long id = UserBean.getId();
		String op = DataUtility.getString(request.getParameter("operation"));
		

		// get model
		UserModel model = new UserModel();

		if (OP_SAVE.equalsIgnoreCase(op)) {
			UserBean bean = (UserBean) populateBean(request);
			try {
				if (id > 0) {
					UserBean.setFirstName(bean.getFirstName());
					UserBean.setLastName(bean.getLastName());
					UserBean.setGender(bean.getGender());
					UserBean.setMobileNo(bean.getMobileNo());
					UserBean.setDob(bean.getDob());
					model.update(UserBean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Profile is updated Successfuly", request);
				ServletUtility.forward(getView(), request, response);
			} catch (ApplicationException e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("LOgin id already exists", request);
			}
		} else if (OP_CHANGE_MY_PASSWORD.equalsIgnoreCase(op)) {
			System.out.println("change password ");
			ServletUtility.redirect(ORSView.CHANGE_PASSWORD_CTL, request, response);
			return;
			
		}
		
		log.debug("MyProfileCtl Method dopost Ended");

	}

	@Override
	protected String getView() {
		
		return ORSView.MY_PROFILE_VIEW;
	}

}
