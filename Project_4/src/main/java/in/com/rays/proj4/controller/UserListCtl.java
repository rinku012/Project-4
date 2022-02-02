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
import in.com.rays.proj4.model.RoleModel;
import in.com.rays.proj4.model.UserModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

/**
 * @author Rinku
 *
 */
@WebServlet(name="/UserListCtl",urlPatterns = "/ctl/UserListCtl")
public class UserListCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(UserListCtl.class);

	@Override
	protected void preload(HttpServletRequest request) {

		RoleModel rmodel = new RoleModel();

		try {
			List rlist = rmodel.list();

			request.setAttribute("RoleList", rlist);
		} catch (ApplicationException e) {

			e.printStackTrace();
		}

	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		UserBean bean = new UserBean();

		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));

		bean.setRoleId(DataUtility.getLong(request.getParameter("roleid")));

		bean.setLogin(DataUtility.getString(request.getParameter("login")));

		return bean;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doget");
		log.debug("userlistctl doget start");
		List list = null;
		List nextList = null;

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
               System.out.println(PropertyReader.getValue("page.size"));
		UserBean bean = (UserBean) populateBean(request);
		String op = DataUtility.getString(request.getParameter("operation"));

		// get the selected checkbox ids arrar for delete list

		//String[] ids = request.getParameterValues("ids");
		UserModel model = new UserModel();

		try {
			list = model.search(bean, pageNo, pageSize);

			nextList = model.search(bean, pageNo + 1, pageSize);
			if (nextList.size() > 0) {
				request.setAttribute("nextList", nextList.size());
			} else {
				request.setAttribute("nextList", "0");
			}
			System.out.println("list ok");
			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			log.error(e);
			System.out.println("err");
			ServletUtility.handleException(e, request, response);
			return;
		}
		log.debug("userlistctl doget end");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("userlistctl dopost start");

		List list;
		List nextList = null;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		System.out.println((request.getParameter("pageNo")));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		System.out.println(request.getParameter("pageSize"));
		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		String op = DataUtility.getString(request.getParameter("operation"));
		UserBean bean = (UserBean) populateBean(request);
		// get the selected checkbox ids arrey for delete list
	     String[] ids = request.getParameterValues("ids");
		UserModel model = new UserModel();

		if (OP_SEARCH.equalsIgnoreCase(op)) {
			pageNo = 1;
			
		}else if (OP_NEXT.equalsIgnoreCase(op)) {
			pageNo++;
		} 
		else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
			pageNo--;
		} else if (OP_NEW.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.USER_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op) || OP_BACK.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
			return;
		} else if (OP_NEXT.equalsIgnoreCase(op)) {
			pageNo++;
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			if(ids != null && ids.length > 0) {
			UserBean deletbean = new UserBean();
			for (String id : ids) {
				deletbean.setId(DataUtility.getInt(id));
				try {
					model.delete(deletbean);
				} catch (ApplicationException e) {
					log.error(e);
					ServletUtility.handleException(e, request, response);
					return;
				}

				ServletUtility.setSuccessMessage("User is Deleted Successfully", request);
			}
		} else  {
			ServletUtility.setErrorMessage("Select at least one record", request);
		}
		}
	

		try {

			list = model.search(bean, pageNo, pageSize);

			nextList = model.search(bean, pageNo + 1, pageSize);

			request.setAttribute("nextList", nextList.size());
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
			ServletUtility.setErrorMessage("No record found ", request);
		}
		ServletUtility.setList(list, request);
		ServletUtility.setBean(bean, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);
		log.debug("UserListCtl doGet End");

	}

	@Override
	protected String getView() {

		return ORSView.USER_LIST_VIEW;
	}

}
