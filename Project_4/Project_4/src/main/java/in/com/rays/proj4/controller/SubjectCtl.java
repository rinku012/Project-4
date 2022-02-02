package in.com.rays.proj4.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.com.rays.proj4.bean.CourseBean;
import in.com.rays.proj4.bean.SubjectBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.CourseModel;
import in.com.rays.proj4.model.SubjectModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.DataValidator;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;


/**
 * @author Rinku
 *
 */
@WebServlet(name = "SubjectCtl", urlPatterns = "/ctl/SubjectCtl")
public class SubjectCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(SubjectCtl.class);

	protected void preload(HttpServletRequest request) {

		System.out.println("preload enter");

		CourseModel cmodel = new CourseModel();
		 List<CourseBean> coList = new ArrayList<CourseBean>();

		try {
			List cList = cmodel.list();
			request.setAttribute("CourseList", cList);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		System.out.println("preload out");
	}

	protected boolean validate(HttpServletRequest request) {
		log.debug("validate Method of Subject Ctl start");
		System.out.println("validate Started ----------");
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Subject Name"));
			pass = false;
		} else if (!DataValidator.isValidName(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.name", "Invalid Subject"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("courseName"))) {
			request.setAttribute("courseName", PropertyReader.getValue("error.require", "Course Name"));
			pass = false;
		}
		log.debug("validate Method of Subject Ctl  End");
		System.out.println("validate finished------------");
		return pass;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#populateBean(javax.servlet.http.
	 * HttpServletRequest)
	 */
	protected SubjectBean populateBean(HttpServletRequest request) {
		log.debug("Populate bean Method of Subject Ctl start");
		System.out.println("populate bean started===========");
		SubjectBean bean = new SubjectBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setSubjectName(DataUtility.getString(request.getParameter("name")));
		bean.setDescription(DataUtility.getString(request.getParameter("description")));
		bean.setCourseId(DataUtility.getLong(request.getParameter("courseName")));

		populateDTO(bean, request);

		log.debug("PopulateBean Method of Subject Ctl End");
		System.out.println("populate bean finished ===========");
		return bean;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("Do get Method of Subject Ctl start ");
		System.out.println("do get Started           ++++++++++++++++++++");
		String op = DataUtility.getString(request.getParameter("operation"));

		SubjectModel model = new SubjectModel();
		SubjectBean bean = null;
		long id = DataUtility.getLong(request.getParameter("id"));
           System.out.println("subject       id   "+ request.getParameter("id"));
		if (id > 0 || op != null) {
			try {
				bean = model.findByPK(id);
				System.out.println("findpk"+model.findByPK(id));
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		System.out.println("do get finished--------------");
		ServletUtility.forward(getView(), request, response);
		log.debug("Do get Method of Subject Ctl End");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("Do post Method of Subject Ctl start");
		System.out.println("do post stareted (((((((((((((())))))))))");
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		
		SubjectModel model = new SubjectModel();		
		
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {	
			SubjectBean bean = (SubjectBean)populateBean(request);
		//	System.out.println("post in operaion save  ");
		try{	
			if(id > 0){
				model.update(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage(" Subject is Succesfully Updated ", request);
			
			}else{
				long pk = model.add(bean);
				ServletUtility.setSuccessMessage(" Subject is Succesfully Added ", request);
		//		bean.setId(pk);
			}
			ServletUtility.setBean(bean, request);
			//ServletUtility.setSuccessMessage(" Subject is Succesfully Added ", request);
		}catch(ApplicationException e){
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		} catch (DuplicateRecordException e) {
			ServletUtility.setBean(bean, request);
			ServletUtility.setErrorMessage("Subject name already Exsist", request);
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
			return;
		}
		else if (OP_CANCEL.equalsIgnoreCase(op) ) {
			ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
			return;
		}
/*		else if (OP_DELETE.equalsIgnoreCase(op)) {
			SubjectBean bean =  populateBean(request);
			try {
				model.delete(bean);
			ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
			return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return ; 
			}
		}*/
		
	
		ServletUtility.forward(getView(), request, response);
		log.debug("Do post Method of Subject Ctl End");
		System.out.println("dopost finished(((((((((((())))))))))))");
	}
	@Override
	protected String getView() {

		return ORSView.SUBJECT_VIEW;
	}

}
