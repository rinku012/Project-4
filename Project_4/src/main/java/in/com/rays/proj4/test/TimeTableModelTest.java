package in.com.rays.proj4.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.com.rays.proj4.bean.TimeTableBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.TimeTableModel;

public class TimeTableModelTest {

	public static TimeTableModel model = new TimeTableModel();

	public static void main(String[] args) throws ParseException {
		// testAdd();
		// testDelete();
		// testUpdate();
		// testFindByPK();
		// testFindByName();
		// testList();
		testSearch();
	}

	public static void testAdd() throws ParseException {
		try {
			TimeTableBean bean = new TimeTableBean();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			bean.setCourseId(2L);
			bean.setSubjectId(2L);
			// bean.setCourseName("");
			bean.setExamDate(sdf.parse("14/07/2020"));
			bean.setExamTime("08:00 AM to 11:00 AM");
			bean.setSemester("5th");
			// bean.setSubjectName("Java");
			bean.setCreatedBy("Admin");
			bean.setModifiedBy("Admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			long pk = model.add(bean);
			TimeTableBean addedbean = model.findByPk(pk);
			System.out.println("ok timetable succe");
			if (addedbean == null) {
				System.out.println("Test add fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}

	}

	public static void testDelete() {
		try {
			TimeTableBean bean = new TimeTableBean();
			bean.setId(2L);

			model.delete(bean);
			System.out.println("test delete secc");

		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testUpdate() {
		try {
			TimeTableBean bean = model.findByPk(1L);
			// bean.setSubjectId(3L);
			bean.setSubjectName("Material Technology");
			model.update(bean);

			TimeTableBean updatedbean = model.findByPk(1L);
			if (!"Material Technology".equals(updatedbean.getSubjectName())) {
				System.out.println("Test Update fail");
			} else {
				System.out.println("Test Update Successfully");
			}
			System.out.println("-------------------------------");
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}

	}

	public static void testFindByPK() {
		try {
			TimeTableBean bean = new TimeTableBean();
			long pk = 1L;
			bean = model.findByPk(pk);
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getCourseId());
			System.out.println(bean.getCourseName());
			System.out.println(bean.getExamTime());
			System.out.println(bean.getSemester());
			System.out.println(bean.getSubjectId());
			System.out.println(bean.getSubjectName());
			System.out.println(bean.getExamDate());

			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testFindByName() {
		try {
			TimeTableBean bean = new TimeTableBean();
			bean = model.findByName("Java");
			if (bean == null) {
				System.out.println("test timetable fail");
			}
			System.out.println(bean.getCourseId());
			System.out.println(bean.getCourseName());

			System.out.println(bean.getExamTime());
			System.out.println(bean.getSemester());
			System.out.println(bean.getSubjectId());
			System.out.println(bean.getSubjectName());
			System.out.println(bean.getExamDate());

			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());

		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testList() {
		try {

			TimeTableBean bean = new TimeTableBean();
			List list = new ArrayList();
			list = model.list(1, 10);
			if (list.size() < 0) {
				System.out.println("test list fail");
			}

			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (TimeTableBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getCourseId());
				System.out.println(bean.getCourseName());
				System.out.println(bean.getExamTime());
				System.out.println(bean.getSemester());
				System.out.println(bean.getSubjectId());
				System.out.println(bean.getSubjectName());
				System.out.println(bean.getExamDate());
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

	public static void testSearch()  {
		
		try {
			TimeTableBean bean = new TimeTableBean();
			List list = new ArrayList();
			SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
			 try {
				bean.setExamDate(date.parse("10-11-2022"));
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (TimeTableBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getCourseId());
				System.out.println(bean.getCourseName());
				System.out.println(bean.getExamTime());
				System.out.println(bean.getSemester());
				System.out.println(bean.getSubjectId());
				System.out.println(bean.getSubjectName());
				System.out.println(bean.getExamDate());
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

}
