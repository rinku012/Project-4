package in.com.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import in.com.rays.proj4.bean.CourseBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.CourseModel;

public class CourseModelTest {

	public static CourseModel model = new CourseModel();

	public static void main(String[] args) {
		// testAdd();
		// testDelete();
		// testUpdate();
		// tsetFindByName();
		// testFindByPK();
	 testSearch();
//		 testList();

	}

	public static void testList() {
		try {
			CourseBean bean = new CourseBean();
			List list = new ArrayList();
			list = model.list(1, 5);
			if (list.size() < 0) {
				System.out.println("test list fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (CourseBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getCourseName());
				System.out.println(bean.getDescription());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getModifiedDatetime());
			}
		} catch (ApplicationException e) {

			e.printStackTrace();
		}

	}

	public static void testSearch() {
		try {
			CourseBean bean = new CourseBean();
			List list = new ArrayList();
			//bean.setCourseName("BE");
			bean.setDuration("1 Year");
			list = model.search(bean);
			if (list.size() < 0) {
				System.out.println("test search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (CourseBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getCourseName());
				System.out.println(bean.getDescription());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getModifiedDatetime());
			}
		} catch (ApplicationException e) {

			e.printStackTrace();
		}

	}

	public static void testFindByPK() {
		try {
			CourseBean bean = new CourseBean();
			long pk = 1L;
			bean = model.findByPK(2);
			if (bean == null) {
				System.out.println("test find by pk fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getCourseName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getDuration());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getModifiedDatetime());
		} catch (ApplicationException e) {

			e.printStackTrace();
		}

	}

	public static void tsetFindByName() {
		try {
			CourseBean bean = new CourseBean();
			bean = model.findByName("java");
			if (bean == null) {
				System.out.println("test find by name fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getCourseName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getDuration());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getModifiedDatetime());
		} catch (ApplicationException e) {

			e.printStackTrace();
		}

	}

	public static void testUpdate() {
		try {
			CourseBean bean = model.findByPK(1L);
			bean.setCourseName("Mca");
			bean.setDescription("Degree");
			model.update(bean);
			System.out.println("Test Update success");
			CourseBean updateBean = model.findByPK(2L);
			if (!"MSC".equals(updateBean.getCourseName())) {
				System.out.println("Test Update fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}

	}

	public static void testDelete() {
		try {
			CourseBean bean = new CourseBean();
			long pk = 1L;
			bean.setId(1);
			model.delete(bean);
			System.out.println("test Delete success");
			CourseBean deleteBean = model.findByPK(pk);

			if (deleteBean == null) {
				System.out.println("test Delete fail");
			}
		} catch (ApplicationException e) {

			e.printStackTrace();
		}

	}

	public static void testAdd() {
		try {
			CourseBean bean = new CourseBean();
			// bean.setId(2);
			bean.setCourseName("CoreJava ");
			bean.setDescription("bechelor degree");
			bean.setDuration("3 year");
			bean.setCreatedBy("ry@gmail.com");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

			long pk = model.add(bean);
			System.out.println("add tessted successfully");
			CourseBean addedBean = model.findByPK(pk);
			if (addedBean == null) {
				System.out.println("fail to add");
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
