package in.com.rays.proj4.controller;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.bean.StudentBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.CollegeModel;
import in.com.rays.proj4.model.StudentModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.DataValidator;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;



/**
 *  Student functionality Controller. Performs operation for add, update, delete
 * and get Student
 * @author Rinku
 *
 */
@WebServlet(name="StudentCtl", urlPatterns = {"/ctl/StudentCtl"})
public class StudentCtl extends BaseCtl {

   
  //  private static Logger log = Logger.getLogger(StudentCtl.class);

    /* (non-Javadoc)
     * @see in.co.rays.ors.controller.BaseCtl#preload(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void preload(HttpServletRequest request) {
    	System.out.println("Preload Start...");
        CollegeModel model = new CollegeModel();
        
        try {
            List l = model.list();
            request.setAttribute("collegeList", l);
        } catch (ApplicationException e) {
       //     log.error(e);
        	System.out.println("This is    a Prlouad Method : Finished");
        }

    }

    /* (non-Javadoc)
     * @see in.co.rays.ors.controller.BaseCtl#validate(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected boolean validate(HttpServletRequest request) {
    	System.out.println("validate started ... std ctl");
     //   log.debug("StudentCtl Method validate Started");
        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("firstname"))) {
            request.setAttribute("firstname",PropertyReader.getValue("error.require", "First Name"));
            pass = false;
        }else if (!DataValidator.isValidName(request.getParameter("firstname"))) {
        	  request.setAttribute("firstname",PropertyReader.getValue("error.name", "Invalid First"));
              pass = false;
		}
        if (DataValidator.isNull(request.getParameter("lastname"))) {
            request.setAttribute("lastname",PropertyReader.getValue("error.require", "Last Name"));
            pass = false;
        }else if (!DataValidator.isValidName(request.getParameter("lastname"))) {
      	  request.setAttribute("lastname",PropertyReader.getValue("error.name", "Invalid Last"));
          pass = false;
	}
        if (DataValidator.isNull(request.getParameter("mobile"))) {
            request.setAttribute("mobile", PropertyReader.getValue("error.require", "Mobile No"));
            pass = false;
        }else if (!DataValidator.isMobileNo(request.getParameter("mobile"))) {
      	  request.setAttribute("mobile", "Mobile No. must be 10 Digit and No. Series start with 6-9");
          pass = false;
	}
        if (DataValidator.isNull(request.getParameter("email"))) {
            request.setAttribute("email", PropertyReader.getValue("error.require", "Email "));
            pass = false;
        } else if (!DataValidator.isEmail(request.getParameter("email"))) {
            request.setAttribute("email", PropertyReader.getValue("error.email", "Invalid "));
            pass = false;
        }
		/*
		 * if (DataValidator.isNull(request.getParameter("dob"))) {
		 * request.setAttribute("dob", PropertyReader.getValue("error.require",
		 * "Date Of Birth")); pass = false; }else if
		 * (!DataValidator.isvalidateAge(request.getParameter("dob"))) {
		 * request.setAttribute("dob", "Student Age must be Greater then 18 year ");
		 * pass = false; }
		 */
        
        if (DataValidator.isNull(request.getParameter("collegeId"))) {
        	System.out.println("collge id print"+request.getParameter("collegeId"));
			request.setAttribute("collegeId", PropertyReader.getValue("error.require", "College Name"));
			pass = false;
		}
        System.out.println("validate over ,.... Student ctl Finshed validate");
      //  log.debug("StudentCtl Method validate Ended");
        return pass;
    }

    /* (non-Javadoc)
     * @see in.co.rays.ors.controller.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {
        System.out.println("Student Ctl Populatebean Statarted");
       // log.debug("StudentCtl Method populatebean Started");

        StudentBean bean = new StudentBean();
             System.out.println("object of Studentnbean ");
        bean.setId(DataUtility.getLong(request.getParameter("id")));
        System.out.println("This is Id form JSP: "+ request.getParameter("id"));
        bean.setFirstName(DataUtility.getString(request.getParameter("firstname")));
        bean.setLastName(DataUtility.getString(request.getParameter("lastname")));
        bean.setDob(DataUtility.getDate(request.getParameter("dob")));        
        bean.setMobileNo(DataUtility.getString(request.getParameter("mobile")));
        bean.setEmail(DataUtility.getString(request.getParameter("email")));
        bean.setCollegeId(DataUtility.getLong(request.getParameter("collegeId")));
       System.out.println("college id: "+request.getParameter("collegeId"));
       System.out.println("student bean ok is here+++++++++++++++++++++ "+request.getParameter("collegeId"));
        populateDTO(bean, request);
      //  log.debug("StudentCtl Method populatebean Ended");
        return bean;
        
    }

    /**
     * Contains Display logics.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
              System.out.println("StudentCtl DoGet Started know");
      //  log.debug("StudentCtl Method doGet Started");

        String op = DataUtility.getString(request.getParameter("operation"));
        long id = DataUtility.getLong(request.getParameter("id"));

        // get model

        StudentModel model = new StudentModel();
        System.out.println("object StudentModel is here ");
        if (id > 0 || op != null) {
            StudentBean bean;
            try {
                bean = model.findByPK(id);
                ServletUtility.setBean(bean, request);
                System.out.println("request of Set bean ");
            } catch (ApplicationException e) {
         //       log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            }
        }
        ServletUtility.forward(getView(), request, response);
       // log.debug("StudentCtl Method doGett Ended");
    }

    /**
     * Contains Submit logics.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
              System.out.println("StudentCtl method dopost started here");
       // log.debug("StudentCtl Method doPost Started");

        String op = DataUtility.getString(request.getParameter("operation"));

        long id = DataUtility.getLong(request.getParameter("id"));
        // get model

        StudentModel model = new StudentModel();
            System.out.println(" object of StudentModel ");
        if (OP_SAVE.equalsIgnoreCase(op)|| OP_UPDATE.equalsIgnoreCase(op)) {
            StudentBean bean = (StudentBean) populateBean(request);
            try {
                if (id > 0) {
                    model.update(bean);
                    ServletUtility.setBean(bean, request);
                    ServletUtility.setSuccessMessage(" Student is successfully Updated",request);
                } else {
                    long pk =0;
                    pk=model.add(bean);
                    bean.setId(pk);
                    ServletUtility.setBean(bean, request);
                    ServletUtility.setSuccessMessage(" Student is successfully Added",request);
             //      bean.setId(pk);   
                }
                ServletUtility.setBean(bean, request);
               // ServletUtility.setSuccessMessage(" Student is successfully Added",request);
            } catch (ApplicationException e) {
         //       log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Student Email Id already exists", request);
            }
        } 
        else if ( OP_RESET.equalsIgnoreCase(op)) {
            
         	ServletUtility.redirect(ORSView.STUDENT_CTL, request, response);
             return;
         }
        else if (OP_CANCEL.equalsIgnoreCase(op) ) {
            
         	ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
             return;
         }
/*
        else if (OP_DELETE.equalsIgnoreCase(op)) {

            StudentBean bean = (StudentBean) populateBean(request);
            try {
                model.delete(bean);
                ServletUtility.redirect(ORSView.STUDENT_CTL, request, response);
                return;

            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            }
        }
*/        ServletUtility.forward(getView(), request, response);

     //   log.debug("StudentCtl Method doPost Ended");
    }

    /* (non-Javadoc)
     * @see in.co.rays.ors.controller.BaseCtl#getView()
     */
    @Override
    protected String getView() {
        return ORSView.STUDENT_VIEW;
    }
	
}
