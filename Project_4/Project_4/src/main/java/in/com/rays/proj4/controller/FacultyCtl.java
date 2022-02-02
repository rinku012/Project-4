package in.com.rays.proj4.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.bean.CollegeBean;
import in.com.rays.proj4.bean.CourseBean;
import in.com.rays.proj4.bean.FacultyBean;
import in.com.rays.proj4.bean.SubjectBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.CollegeModel;
import in.com.rays.proj4.model.CourseModel;
import in.com.rays.proj4.model.FacultyModel;
import in.com.rays.proj4.model.SubjectModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.DataValidator;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

/**
 * @author Rinku
 *
 */
@WebServlet(name = "FacultyCtl", urlPatterns = { "/ctl/FacultyCtl" })
public class FacultyCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(FacultyCtl.class);

	protected void preload(HttpServletRequest request) {
		System.out.println("prelaod in");

		System.out.println("Course id is :" + request.getParameter("courseId"));
		System.out.println("CollegeName is :" + request.getParameter("collegeName"));

		CourseModel cmodel = new CourseModel();
		CollegeModel comodel = new CollegeModel();
		SubjectModel smodel = new SubjectModel();

		List<CourseBean> clist = new ArrayList<CourseBean>();

		List<CollegeBean> colist = new ArrayList<CollegeBean>();
		List<SubjectBean> slist = new ArrayList<SubjectBean>();

		try {
			clist = cmodel.list();
			colist = comodel.list();
			slist = smodel.list();

			request.setAttribute("CourseList", clist);
			request.setAttribute("CollegeList", colist);
			request.setAttribute("SubjectList", slist);

			System.out.println("list wali course id: " + request.getParameter("courseId"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected boolean validate(HttpServletRequest request) {
		System.out.println("validate in");

		log.debug("valited method of faculty ctl stareted");
		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "FirstName"));
			pass = false;
		} else if (!DataValidator.isValidName(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.name", "Invalid First"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "LastName"));
			pass = false;
		} else if (!DataValidator.isValidName(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.name", "Invalid Last"));
			pass = false;

		}
		if (DataValidator.isNull(request.getParameter("Gender"))) {
			request.setAttribute("Gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("emailId"))) {
			request.setAttribute("emailId", PropertyReader.getValue("error.require", "LoginId"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("emailId"))) {
			request.setAttribute("emailId", PropertyReader.getValue("error.email", "Invalid"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "MobileNo"));
			pass = false;
		} else if (!DataValidator.isMobileNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Mobile No. must be 10 Digit and No. Series start with 6-9");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Birth"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("collegeId"))) {
			request.setAttribute("collegeId", PropertyReader.getValue("error.require", "CollegeName"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("courseId"))) {
			request.setAttribute("courseId", PropertyReader.getValue("error.require", "CourseName"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("subjectId"))) {
			request.setAttribute("subjectId", PropertyReader.getValue("error.require", "SubjectName"));
			pass = false;
		}

		System.out.println("validate out ");
		log.debug("validate Ended");
		return pass;
	}

	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("populate bean faculty ctl started");
		System.out.println(" populate bean ctl  in ");
		FacultyBean bean = new FacultyBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setGender(DataUtility.getString(request.getParameter("Gender")));
		bean.setEmailId(DataUtility.getString(request.getParameter("emailId")));
		bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
		bean.setDob(DataUtility.getDate(request.getParameter("dob")));
		bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		System.out.println("This is course id: " + request.getParameter("Id"));

		bean.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));
		bean.setCollegeId(DataUtility.getLong(request.getParameter("collegeId")));

		bean.setSubjectName(DataUtility.getString(request.getParameter("subjectName")));
		bean.setCollegeName(DataUtility.getString(request.getParameter("Name")));
		bean.setCourseName(DataUtility.getString(request.getParameter("courseName")));
		populateDTO(bean, request);
		log.debug("populate bean faculty ctl Ended");
		System.out.println(" populate bean ctl out ");
		return bean;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug(" do get faculty ctl started");

		String op = DataUtility.getString(request.getParameter("operation"));

		// get model
		FacultyModel model = new FacultyModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			FacultyBean bean;

			try {
				bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {

				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
			}

		}
		System.out.println("do get out");
		log.debug("do get of faculty ctl ended");
		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("do post of faculty ctl started");
		System.out.println("do post facultyctl start");

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		// get model

		FacultyModel model = new FacultyModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			FacultyBean bean = (FacultyBean) populateBean(request);
			try {

				if (id > 0) {
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Faculty Successfully Update", request);
				} else {
					long pk = model.add(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Faculty Successfully Added", request);
					// bean.setId(id);
				}
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Faculty already Exist", request);
			}
		}
		/*
		 * else if (OP_DELETE.equalsIgnoreCase(op)) { FacultyBean bean =(FacultyBean)
		 * populateBean(request);
		 * 
		 * try { model.delete(bean); ServletUtility.redirect(ORSView.FACULTY_CTL,
		 * request, response); } catch (ApplicationException e) { log.error(e);
		 * ServletUtility.handleException(e, request, response); return; } }
		 */
		else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
			return;
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
			return;
		}
		 System.out.println(" do post out ");
		ServletUtility.forward(getView(), request, response);
		log.debug("Do post of  faculty ctl Ended");
		System.out.println(" Do post of  faculty ctl Ended ");

	}

	@Override
	protected String getView() {

		return ORSView.FACULTY_VIEW;
	}

}
