package in.com.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.bean.CourseBean;
import in.com.rays.proj4.bean.StudentBean;
import in.com.rays.proj4.bean.SubjectBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.model.CollegeModel;
import in.com.rays.proj4.model.CourseModel;
import in.com.rays.proj4.model.StudentModel;
import in.com.rays.proj4.model.SubjectModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

/**
 * @author Rinku
 *
 */
@WebServlet(name = "SubjectListCtl", urlPatterns = "/ctl/SubjectListCtl")
public class SubjectListCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(StudentListCtl.class);

	@Override
	protected void preload(HttpServletRequest request) {

		SubjectModel smodel = new SubjectModel();
		CourseModel cmodel = new CourseModel();

		List<SubjectBean> slist = null;
		List<CourseBean> clist = null;

		try {
			clist = cmodel.list();
			slist = smodel.list();
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

		request.setAttribute("subjectList", slist);
		request.setAttribute("courseList", clist);
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
         
		SubjectBean bean = new SubjectBean();
        
		bean.setCourseId(DataUtility.getLong(request.getParameter("coursename")));
		//bean.setCourseName(DataUtility.getString(request.getParameter("coursename")));
		bean.setId(DataUtility.getLong(request.getParameter("subjectname")));
		//bean.setSubjectName(DataUtility.getString(request.getParameter("subjectname")));
	//	bean.setSubjectName(DataUtility.getString(request.getParameter("name")));
		populateDTO(bean, request);
		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		SubjectBean bean = (SubjectBean) populateBean(request);
		SubjectModel model = new SubjectModel();
		List list;
		List next;

		try {
			list = model.search(bean, pageNo, pageSize);
			System.out.println("subject search complete");
			next = model.search(bean, pageNo + 1, pageSize);
			System.out.println("subject search complete");

			ServletUtility.setList(list, request);

			if (list == null && list.size() == 0) {
				ServletUtility.setErrorMessage("No Record Found", request);
			}
			if (next == null || next.size() == 0) {
				request.setAttribute("nextlist", 0);
			} else {
				request.setAttribute("nextlist", next.size());
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		log.debug("do Get Method of SubjectList Ctl Ended ");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("SubjectListCtl doPost Start");
		List list = null;
		List next = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;

		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		SubjectBean bean = (SubjectBean) populateBean(request);
		String op = DataUtility.getString(request.getParameter("operation"));
		SubjectModel model = new SubjectModel();
		String[] ids = request.getParameterValues("ids");

		try {
			if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op) || OP_PREVIOUS.equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}
			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
				return;
			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					SubjectBean deletebean = new SubjectBean();
					for (String id : ids) {
						deletebean.setId(DataUtility.getInt(id));
						model.delete(deletebean);
						ServletUtility.setSuccessMessage("Data Delete Successfully", request);
					}
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			}
			if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
				return;
			}
			bean = (SubjectBean) populateBean(request);
			list = model.search(bean, pageNo, pageSize);
			System.out.println("serach aaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			ServletUtility.setBean(bean, request);

					List next1 = model.search(bean, pageNo + 1, pageSize);
					ServletUtility.setList(list, request);

			next1 = model.search(bean, pageNo + 1, pageSize);
			System.out.println("serachshhhhhhh");
			ServletUtility.setList(list, request);

			if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
				ServletUtility.setErrorMessage("No record Found", request);

			}
			if (next1 == null || next1.size() == 0) {
				request.setAttribute("nextlist", 0);
			} else {
				request.setAttribute("nextlist", next1.size());
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		} catch (Exception e) {

		}
System.out.println("ok");
		log.debug("SubjectListCtl doPost End");
	}

	@Override
	protected String getView() {

		return ORSView.SUBJECT_LIST_VIEW;
	}

}
