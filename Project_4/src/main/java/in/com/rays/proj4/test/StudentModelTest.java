package in.com.rays.proj4.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.com.rays.proj4.bean.StudentBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.StudentModel;

public class StudentModelTest {

	public static StudentModel model = new StudentModel();

	public static void main(String[] args) throws ParseException, ApplicationException, DuplicateRecordException {
		// testAdd();
		// testDelete();
		// testUpdate();
		// testFindByPK();
		// tsetFindByEmail();
		// testSearch();
		// testList();

	}

	public static void testList() {

		try {
			StudentBean bean = new StudentBean();
			List list = new ArrayList();
			list = model.list(1, 10);
			if (list.size() < 0) {
				System.out.println("test list fail");
			}

			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (StudentBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getDob());
				System.out.println(bean.getMobileNo());
				System.out.println(bean.getEmail());
				System.out.println(bean.getCollegeId());
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
			StudentBean bean = new StudentBean();

			List list = new ArrayList();
			bean.setId(1);
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (StudentBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getDob());
				System.out.println(bean.getMobileNo());
				System.out.println(bean.getEmail());
				System.out.println(bean.getCollegeId());

			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void tsetFindByEmail() {
		try {
			StudentBean bean = new StudentBean();
			bean = model.findByEmailId("Sy@gmail.com");
			if (bean != null) {
				System.out.println("Test findBy Email id fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getDob());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getEmail());
			System.out.println(bean.getCollegeId());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testFindByPK() {
		try {
			StudentBean bean = new StudentBean();
			long pk = 2L;

			bean = model.findByPK(pk);
			if (bean == null) {
				System.out.println("Tast find bypk fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getDob());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getCollegeName());
			System.out.println(bean.getCollegeId());

		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testUpdate() throws ApplicationException, DuplicateRecordException, ParseException {

		StudentBean bean = new StudentBean();

		bean = model.findByPK(2L);
		System.out.println("updated");
		SimpleDateFormat sdf = new SimpleDateFormat();
		// bean.setId(3);
		bean.setFirstName("Ajjd;jad");
		// bean.setDob(sdf.parse("02/09/1989"));
		bean.setLastName("Parmar");
		bean.setCollegeId(2L);
		// bean.setCollegeName("Prestige");
		bean.setCreatedBy("Master");
		bean.setModifiedBy("Master");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		model.update(bean);
	}

	public static void testDelete() throws ApplicationException {

		StudentBean bean = new StudentBean();
		bean.setId(1);

		model.delete(bean);
		System.out.println("deleted");

	}

	public static void testAdd() throws ParseException {
		StudentBean bean = new StudentBean();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");

		try {
			// bean.setId(1);
			bean.setFirstName("Rajajja");
			bean.setLastName("rat");
			bean.setDob(sdf.parse("18/02/2004"));
			bean.setMobileNo("9755741189");
			bean.setEmail("rjaytjf@gmail.com");
			bean.setCollegeId(3L);
			// bean.setCollegeName("Dr.Apj");
			bean.setCreatedBy("Admin");
			bean.setModifiedBy("Admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

			long pk = model.add(bean);
			bean = model.findByPK(pk);
			// model.add(bean);
			if (bean == null) {
				System.out.println("Test add fail");
			}
		} catch (ApplicationException e) {

			e.printStackTrace();
		} catch (DuplicateRecordException e) {

			e.printStackTrace();
		}
	}
}
