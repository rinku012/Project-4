package in.com.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.bean.CourseBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.CourseModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.DataValidator;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

/**
 * @author Rinku
 *
 */
@WebServlet(name = "CourseCtl", urlPatterns = { "/ctl/CourseCtl" })
public class CourseCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(CourseCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {
		log.debug("courseCtl validate started");
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Course Name"));
			pass = false;
		} else if (!DataValidator.isValidName(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Invalid course"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("duration"))) {
			request.setAttribute("duration", PropertyReader.getValue("error.require", "duration"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "description"));
			pass = false;
		}

		log.debug("CourseCtl validate end");

		return pass;

	}

	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("courseCtl populatedBean stareted");
		CourseBean bean = new CourseBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setCourseName(DataUtility.getString(request.getParameter("name")));
		System.out.println("pop " + request.getParameter("duration"));
		bean.setDuration(DataUtility.getString(request.getParameter("duration")));
		bean.setDescription(DataUtility.getString(request.getParameter("description")));

		populateDTO(bean, request);
		log.debug("CourseCtl PopulatedBean End");
		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("Do get method od courseCtl started");
		System.out.println("doget ok ===================");
		String op = DataUtility.getString(request.getParameter("operation"));

		// get Model
		CourseModel model = new CourseModel();
		long id = DataUtility.getLong( request.getParameter("id"));
 System.out.println("course     id     "+request.getParameter("id"));
		if (id > 0) {
			CourseBean bean;

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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("do post method of courseCtl started");
		System.out.println("dopost okkkkkkkkkk +++++++++");
		String op = DataUtility.getString(request.getParameter("operation"));

		// get model
		CourseModel model = new CourseModel();
		long id = DataUtility.getLong(request.getParameter("id"));
          System.out.println("id yes ////"+request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			CourseBean bean = (CourseBean) populateBean(request);
			try {
				if (id > 0) {
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Course is Successfully Updated", request);

				} else {
					long pk = model.add(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Course is Successfully Added", request);
                System.out.println("course Added ++++++++++++++++++");
					// bean.setId(pk);
				}
				ServletUtility.setBean(bean, request);
				// ServletUtility.setSuccessMessage("Course is Successfully Added", request);

			} catch (ApplicationException e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Course Name Already Exist", request);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} /*
			 * else if (OP_DELETE.equalsIgnoreCase(op)) { CourseBean bean =(CourseBean)
			 * populateBean(request); try { model.delete(bean);;
			 * ServletUtility.redirect(ORSView.COURSE_CTL, request, response); return; }
			 * catch (ApplicationException e) { log.error(e);
			 * ServletUtility.handleException(e, request, response); return ; } }
			 */
		else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("Do Post method CourseCtl Ended");
	}

	@Override
	protected String getView() {

		return ORSView.COURSE_VIEW;
	}

}
