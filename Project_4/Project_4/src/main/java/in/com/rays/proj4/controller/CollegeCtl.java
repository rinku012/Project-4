package in.com.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.bean.CollegeBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.CollegeModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.DataValidator;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

/**
 * @author Rinku
 *
 */
@WebServlet(name = "CollegeCtl", urlPatterns = { "/ctl/CollegeCtl" })
public class CollegeCtl extends BaseCtl {
	private static final int serialVersionUID = 1;

	private static Logger log = Logger.getLogger(CollegeCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("CollegeCtl Method validate Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
			pass = false;
		}else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.name", "Invalid"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("address"))) {
			request.setAttribute("address", PropertyReader.getValue("error.require", "Address"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("state"))) {
			request.setAttribute("state", PropertyReader.getValue("error.require", "State"));
			pass = false;
		}else if (!DataValidator.isName(request.getParameter("state"))) {
			request.setAttribute("state", PropertyReader.getValue("error.name", "Invalid State"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("city"))) {
			request.setAttribute("city", PropertyReader.getValue("error.require", "city"));
			pass = false;
		}else if (!DataValidator.isName(request.getParameter("city"))) {
			request.setAttribute("city", PropertyReader.getValue("error.name", "Invalid City"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("phoneNo"))) {
			request.setAttribute("phoneNo", PropertyReader.getValue("error.require", "Phone No"));
			pass = false;
		}else if (!DataValidator.isMobileNo(request.getParameter("phoneNo"))) {
			request.setAttribute("phoneNo", "Mobile No. must be 10 Digit and No. Series start with 6-9");
			pass = false;
		}

		log.debug("CollegeCtl Method validate Ended");

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("CollegeCtl Method populatebean Started");

		CollegeBean bean = new CollegeBean();

		bean.setId(DataUtility.getInt(request.getParameter("id")));

		bean.setName(DataUtility.getString(request.getParameter("name")));

		bean.setAddress(DataUtility.getString(request.getParameter("address")));

		bean.setState(DataUtility.getString(request.getParameter("state")));

		bean.setCity(DataUtility.getString(request.getParameter("city")));

		bean.setPhonoNo(DataUtility.getString(request.getParameter("phoneNo")));

		populateDTO(bean, request);

		log.debug("CollegeCtl Method populatebean Ended");

		return bean;
	}

	/**
	 * Contains display logic
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("do get in");
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		
		CollegeModel model = new CollegeModel();
		
		if (id > 0) {
			CollegeBean bean;
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
	}


	/**
	 * Contains Submit logics
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("CollegeCtl Method doPost Started");
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		
		CollegeModel model = new CollegeModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			CollegeBean bean = (CollegeBean) populateBean(request);
			try {
				if (id > 0) {
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("College is successfully Updated ", request);
			
				} else {
					long pk = model.add(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("College is successfully Added ", request);
			System.out.println("College is successfully Added ");
			//		bean.setId(pk);
				}
				ServletUtility.setBean(bean, request);
			//	ServletUtility.setSuccessMessage("College is successfully Saved ", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("College Name already exists", request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		else if ( OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.COLLEGE_CTL, request, response);
			return;
		}		else if (OP_CANCEL.equalsIgnoreCase(op) ) {

			ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
			return;
		}
		/*else if (OP_DELETE.equalsIgnoreCase(op)) {

			CollegeBean bean = (CollegeBean) populateBean(request);
			try {
				model.delete(bean);
				ServletUtility.redirect(ORSView.COLLEGE_CTL, request, response);
				return;

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} */
	
	
	System.out.println("dopost out");
	ServletUtility.forward(getView(), request, response);
	log.debug("CollegeCtl Method doGet Ended");
	}
	@Override
	protected String getView() {
		return ORSView.COLLEGE_VIEW;
	}

}
