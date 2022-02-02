package in.com.rays.proj4.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.text.ParseException;

import in.com.rays.proj4.bean.FacultyBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.FacultyModel;

public class FacultyModelTest {

	public static FacultyModel model = new FacultyModel();

	public static void main(String[] args) throws Exception {

		 testAdd();//done
		// testDelete();//done
		// testUpadate();//done
		// testFindByEmail();//done
		// testFindByPK();//done
		// testsearch();//done
		// testList();
	}

	public static void testAdd() throws ApplicationException, DuplicateRecordException {

		try {
			FacultyBean bean = new FacultyBean();

			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

			bean.setCollegeId(3L);
			bean.setCourseId(2L);
			bean.setSubjectId(1L);
			bean.setFirstName("Dfjflkg");
			bean.setLastName("Klkxfnv");
			bean.setGender("male");
			bean.setDob(sdf.parse("6/3/1987"));
			bean.setEmailId("DK94368884@gmail.com");
			bean.setMobileNo("9168454567");
			bean.setCourseName("Bba");
			bean.setCreatedBy("Admin");
			bean.setModifiedBy("Admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			model.add(bean);
			System.out.println("Test add succ");

		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public static void testDelete() {

		try {
			FacultyBean bean = new FacultyBean();

			long pk = 2L;
			bean.setId(2l);

			model.delete(bean);
			System.out.println("test Delete secc");
		} catch (ApplicationException e) {

			e.printStackTrace();
		}

	}

	public static void testUpadate() {

		try {
			FacultyBean bean = model.findByPk(2L);
			// bean.setCollegeId(2L);
			bean.setFirstName("Shyam");
			// bean.setLastName("Dave");
			model.update(bean);

			FacultyBean updatedbean = model.findByPk(2L);
			if (!"Sheekha".equals(updatedbean.getFirstName())) {
				System.out.println("Test Update fail");
			} else {
				System.out.println("Test Update Successfully");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}

	}

	public static void testFindByEmail() {
		try {
			FacultyBean bean = new FacultyBean();
			bean = model.findByEmail("ryu94364@gmail.com");
			if (bean != null) {
				System.out.println("test find by emailid fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getDob());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getEmailId());
			System.out.println(bean.getCollegeId());
			System.out.println(bean.getCourseId());
			System.out.println(bean.getSubjectId());
		} catch (ApplicationException e) {

			e.printStackTrace();
		}

	}

	public static void testFindByPK() {
		try {
			FacultyBean bean = new FacultyBean();
			bean = model.findByPk(1L);
			if (bean == null) {
				System.out.println("test find by pk fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getDob());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getEmailId());
			System.out.println(bean.getCollegeId());
			System.out.println(bean.getCourseId());

			System.out.println(bean.getSubjectId());

		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testsearch() {
		try {
			FacultyBean bean = new FacultyBean();
			List list = new ArrayList();

			bean.setCollegeName("holkar");

			list = model.search(bean, 1, 10);

			if (list.size() < 0) {
				System.out.println("test search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (FacultyBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getEmailId());
				System.out.println(bean.getMobileNo());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getModifiedDatetime());
			}
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testList() {
		try {
			FacultyBean bean = new FacultyBean();
			List list = new ArrayList();
			list = model.list(1, 10);
			if (list.size() < 0) {
				System.out.println("test list fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (FacultyBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getDob());
				System.out.println(bean.getMobileNo());
				System.out.println(bean.getEmailId());
				System.out.println(bean.getCollegeId());
				System.out.println(bean.getCourseId());
				System.out.println(bean.getSubjectId());
				System.out.println(bean.getCollegeName());
				System.out.println(bean.getCourseName());
				System.out.println(bean.getSubjectName());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getModifiedDatetime());
			}
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
