package in.com.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.bean.MarksheetBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.model.MarksheetModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.DataValidator;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

/**
 * @author Rinku
 *
 */
@WebServlet (name="GetMarksheetCtl",urlPatterns = "/ctl/GetMarksheetCtl")
public class GetMarksheetCtl extends BaseCtl{

	private static Logger log = Logger.getLogger(GetMarksheetCtl.class);
	
	 @Override
	    protected boolean validate(HttpServletRequest request) {

	        log.debug("GetMarksheetCTL Method validate Started");

	        boolean pass = true;

	        if (DataValidator.isNull(request.getParameter("rollNo"))) {
	            request.setAttribute("rollNo", PropertyReader.getValue("error.require", "Roll Number"));
	            pass = false;
	        }
	        else if (!DataValidator.isRollNo(request.getParameter("rollNo"))) {
				request.setAttribute("rollNo", "Roll No. must be in Formate (XX0000)");//XX0000
				pass = false;
	        }
	        
	        log.debug("GetMarksheetCTL Method validate Ended");
	        return pass;
	    }

	 
	 protected BaseBean populateBean(HttpServletRequest request) {

	        log.debug("GetMarksheetCtl Method populatebean Started");

	        MarksheetBean bean = new MarksheetBean();

	    //    bean.setId(DataUtility.getLong(request.getParameter("id")));
	        bean.setRollNo(DataUtility.getString(request.getParameter("rollNo")));
	/*        bean.setName(DataUtility.getString(request.getParameter("name")));
	        bean.setPhysics(DataUtility.getInt(request.getParameter("physics")));
	        bean.setChemistry(DataUtility.getInt(request.getParameter("chemistry")));
	        bean.setMaths(DataUtility.getInt(request.getParameter("maths")));*/

	        log.debug("GetMarksheetCtl Method populatebean Ended");
	        return bean;
	    }
	 protected void doGet(HttpServletRequest request,
	            HttpServletResponse response) throws ServletException, IOException {
	     
	    	ServletUtility.forward(getView(), request, response);
	    }
	 
	 protected void doPost(HttpServletRequest request,
	            HttpServletResponse response) throws ServletException, IOException {

	        log.debug("GetMarksheetCtl Method doGet Started");
	        String op = DataUtility.getString(request.getParameter("operation"));
	        long id = DataUtility.getLong(request.getParameter("id"));

	        // get model
	        MarksheetModel model = new MarksheetModel();
	        MarksheetBean bean = (MarksheetBean) populateBean(request);


	        if (OP_GO.equalsIgnoreCase(op)) {

	            try {
	                bean = model.findByRollNo(bean.getRollNo());
	        //        ServletUtility.setList(list, request);
	                
	             
	      
	                if (bean != null) {
	                    ServletUtility.setBean(bean, request);
	                }else {
	                    ServletUtility.setErrorMessage("RollNo Does Not Exists",request);
	                    
	                }
	            } catch (ApplicationException e) {
	               e.printStackTrace();
	            	log.error(e);
	                ServletUtility.handleException(e, request, response);
	                return;
	            }
	        }
	            else if (OP_RESET.equalsIgnoreCase(op)) {
	            	ServletUtility.redirect(ORSView.GET_MARKSHEET_CTL, request, response);
	            	return ;
				}
	        ServletUtility.forward(getView(), request, response);
	        log.debug("MarksheetCtl Method doGet Ended");
	    }
	
	 
	@Override
	protected String getView() {
		
		return ORSView.GET_MARKSHEET_VIEW;
	}

}
