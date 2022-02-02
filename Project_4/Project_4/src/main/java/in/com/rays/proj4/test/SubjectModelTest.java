package in.com.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.com.rays.proj4.bean.SubjectBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.SubjectModel;

public class SubjectModelTest {

	public static SubjectModel model = new SubjectModel();

	public static void main(String[] args) throws Exception {

		// testAdd();//done
		// testDelete();//done
		// testUpdate();
		// testFindBYPK();//done
		// testSearch();// done
		// testList();//done
		// testFindByName();
	}

	public static void testAdd() throws Exception {
		try {
			SubjectBean bean = new SubjectBean();

			// bean.setId(1L);
			bean.setSubjectName("Computer");
			bean.setDescription("my computer");
			bean.setCourseId(3);
			bean.setCreatedBy("Admin");
			bean.setModifiedBy("Admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			System.out.println("add method call");
			model.add(bean);
			System.out.println("data enter");
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}

	}

	public static void testDelete() {
		try {
			SubjectBean bean = new SubjectBean();
			bean.setId(7L);
			model.delete(bean);
			System.out.println("test delete secc");
		} catch (ApplicationException e) {

			e.printStackTrace();
		}

	}

	public static void testUpdate() {
		try {
			SubjectBean bean = model.findByPK(2L);
			bean.setSubjectName("Angular");
			model.update(bean);

			// SubjectBean updatebean = model.findByPK(2L);

//			  if (!"mca".equals(updatebean.getSubjectName())) {
//			 
//			  System.out.println("test update fail"); }

		} catch (DuplicateRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testFindBYPK() {
		try {
			SubjectBean bean = new SubjectBean();

			bean = model.findByPK(3L);
			if (bean == null) {
				System.out.println("test find bypk fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getCourseId());
			System.out.println(bean.getCourseName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getSubjectName());
			System.out.println(bean.getModifiedBy());
		} catch (ApplicationException e) {
			
			e.printStackTrace();
		}

	}

	public static void testSearch() {
		try {
			SubjectBean bean = new SubjectBean();
			List list = new ArrayList();
			// bean.setSubjectName("Angularhh");
			bean.setCourseName("okjava");
			list = model.search(bean, 1, 10);
			if (list.size() < 0) {
				System.out.println("test search fail");

			}
			Iterator it = list.iterator();

			while (it.hasNext()) {
				bean = (SubjectBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getSubjectName());
				System.out.println(bean.getCourseId());
				System.out.println(bean.getCourseName());
				System.out.println(bean.getDescription());
			}

		} catch (ApplicationException e) {

			e.printStackTrace();
		}
	}

	public static void testList() {
		try {
			SubjectBean bean = new SubjectBean();
			List list = new ArrayList();
			list = model.list(1, 10);
			if (list.size() < 0) {
				System.out.println("Test list fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (SubjectBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getCourseId());
				System.out.println(bean.getCourseName());
				System.out.println(bean.getDescription());
				System.out.println(bean.getSubjectName());
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testFindByName() {
		try {
			SubjectBean bean = model.findByName("Ruby");

			// model.findByName("Ruby");
			if (bean == null) {
				System.out.println("test find by name fail");
			}
			System.out.println(bean.getCourseId());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getSubjectName());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());
			System.out.println(bean.getCourseName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getModifiedBy());

		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
