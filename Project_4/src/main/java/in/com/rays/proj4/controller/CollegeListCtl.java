package in.com.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.bean.CollegeBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.model.CollegeModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

/**
 * @author Rinku
 *
 */
@WebServlet (name="collegeListCtl",urlPatterns = "/ctl/CollegeListCtl")
public class CollegeListCtl extends BaseCtl{

	 private static Logger log = Logger.getLogger(CollegeListCtl.class);
	
	 @Override
	    protected void preload(HttpServletRequest request){
	    	CollegeModel cmodel=new CollegeModel();
	    	try{
	    		List clist=cmodel.list();
	    		
	    		request.setAttribute("CollegeList", clist);
	    	}
	    	catch(ApplicationException e){
	    		e.printStackTrace();
	    	}
	    }
	 
	 @Override
	    protected BaseBean populateBean(HttpServletRequest request) {
	        CollegeBean bean = new CollegeBean();

	       // bean.setName(DataUtility.getString(request.getParameter("name")));
	        bean.setCity(DataUtility.getString(request.getParameter("city")));
	        
	        bean.setId(DataUtility.getLong(request.getParameter("collegeid")));
	       

	        return bean;
	    }
	 
	 @Override
	    protected void doGet(HttpServletRequest request,
	            HttpServletResponse response) throws ServletException, IOException {

	        int pageNo = 1;
	        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

	        CollegeBean bean = (CollegeBean) populateBean(request);

	        String [] ids = request.getParameterValues("ids");
	        CollegeModel model = new CollegeModel();

	        List list = null;
	        
	        List nextList=null;

	        try {
	            list = model.search(bean, pageNo, pageSize);
	            
	            nextList=model.search(bean,pageNo+1,pageSize);
	            
	            request.setAttribute("nextlist", nextList.size());
	        
	        if (list == null || list.size() == 0) {
	            ServletUtility.setErrorMessage("No record found ", request);
	        }

	        ServletUtility.setList(list, request);
	        ServletUtility.setPageNo(pageNo, request);
	        ServletUtility.setPageSize(pageSize, request);
	        ServletUtility.forward(getView(), request, response);
	    }
	        catch (ApplicationException e) {
	            log.error(e);
	            ServletUtility.handleException(e, request, response);
	            return;
	        }
	    }
	 @Override
	    protected void doPost(HttpServletRequest request,
	            HttpServletResponse response) throws ServletException, IOException {

	        log.debug("CollegeListCtl doPost Start");

	        List list = null;
	        
	        List  nextList=null;

	        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
	        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

	        pageNo = (pageNo == 0) ? 1 : pageNo;
	        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

	       
	        String op = DataUtility.getString(request.getParameter("operation"));
	        
	        String [] ids = request.getParameterValues("ids");
	        CollegeModel model = new CollegeModel();
	        CollegeBean bean = (CollegeBean) populateBean(request);

	                if (OP_SEARCH.equalsIgnoreCase(op)) {
	                    pageNo = 1;
	                } 
	                else if (OP_NEXT.equalsIgnoreCase(op)) {
	                    pageNo++;
	                } 
	                else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
	                    pageNo--;
	                }
	                else if (OP_NEW.equalsIgnoreCase(op)) {
	    			ServletUtility.redirect(ORSView.COLLEGE_CTL, request, response);
	    			return;
	    		}else if (OP_RESET.equalsIgnoreCase(op)) {
	    			ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
	    			return;
	    		}  
	            else if (OP_DELETE.equalsIgnoreCase(op)) {
	                pageNo = 1;
	                if (ids != null && ids.length > 0) {
	                   CollegeBean deletebean = new CollegeBean();
	               // 	UserBean deletebean = new UserBean();
	                    for (String id : ids) {
	                        deletebean.setId(DataUtility.getInt(id));
	                        try {
								model.delete(deletebean);
							} catch (ApplicationException e) {
								ServletUtility.handleException(e, request, response);
								return;
							}ServletUtility.setSuccessMessage("College Data Successfully Deleted", request);
	                    }
	                } 
	                else {
	                    ServletUtility.setErrorMessage(
	                            "Select at least one record", request);
	                }
	            }
	            try {
					
	            	list = model.search(bean, pageNo, pageSize);
					
	            	nextList=model.search(bean,pageNo+1,pageSize);
					
	            	request.setAttribute("nextlist", nextList.size());
					
				} catch (ApplicationException e) {
					log.error(e);
					ServletUtility.handleException(e, request, response);
					return;
				}
	         //   ServletUtility.setList(list, request);
	            
	            if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
	                ServletUtility.setErrorMessage("No record found ", request);
	            }
	            ServletUtility.setList(list, request);
	            ServletUtility.setBean(bean, request);
	            ServletUtility.setPageNo(pageNo, request);
	            ServletUtility.setPageSize(pageSize, request);
	            ServletUtility.forward(getView(), request, response);
	            log.debug("CollegeListCtl doPost End");
	    }
	 
	 
	 
	@Override
	protected String getView() {
		
		return ORSView.COLLEGE_LIST_VIEW;
	}

}
