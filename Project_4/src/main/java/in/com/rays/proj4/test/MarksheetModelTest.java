package in.com.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.com.rays.proj4.bean.MarksheetBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.MarksheetModel;

public class MarksheetModelTest {

	public static MarksheetModel model = new MarksheetModel();

	public static void main(String[] args) {

		 testAdd();
		// testDelete();
		 //testUpdate();
		// testFindBYRollNo();
		// testFindByPK();
		// testSearch();
		// testMeritList();
		// testList();
	}

	public static void testList() {
		try {
			MarksheetBean bean = new MarksheetBean();
			List list = new ArrayList();

			list = model.list(1, 6);

			if (list.size() < 0) {
				System.out.println("test list fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (MarksheetBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getRollNo());
				System.out.println(bean.getName());
				System.out.println(bean.getPhysics());
				System.out.println(bean.getChemistry());
				System.out.println(bean.getMaths());
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

	public static void testMeritList() {
		try {
			MarksheetBean bean = new MarksheetBean();
			List list = new ArrayList();
			list = model.getMeritList(1, 4);
			if (list.size() < 0) {
				System.out.println("test list fail");

			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (MarksheetBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getRollNo());
				System.out.println(bean.getName());
				System.out.println(bean.getPhysics());
				System.out.println(bean.getChemistry());
				System.out.println(bean.getMaths());
			}

		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testSearch() {
		try {
			MarksheetBean bean = new MarksheetBean();
			List list = new ArrayList();
			bean.setName("Rinku");
			list = model.search(bean);
			if (list.size() < 0) {
				System.out.println("test search fail");

			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (MarksheetBean) it.next();

				System.out.println(bean.getId());
				System.out.println(bean.getRollNo());
				System.out.println(bean.getName());
				System.out.println(bean.getPhysics());
				System.out.println(bean.getChemistry());
				System.out.println(bean.getMaths());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testFindByPK() {
		try {
			MarksheetBean bean = new MarksheetBean();
			long pk = 2L;
			bean = model.findByPK(pk);
			if (bean == null) {
				System.out.println("find by pk fail");

			}
			System.out.println(bean.getId());
			System.out.println(bean.getRollNo());
			System.out.println(bean.getName());
			System.out.println(bean.getPhysics());
			System.out.println(bean.getChemistry());
			System.out.println(bean.getMaths());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testFindBYRollNo() {
		try {
			MarksheetBean bean = new MarksheetBean();

			bean = model.findByRollNo("101");
			if (bean == null) {
				System.out.println("tset find by rollno fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getRollNo());
			System.out.println(bean.getName());
			System.out.println(bean.getPhysics());
			System.out.println(bean.getChemistry());
			System.out.println(bean.getMaths());
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testUpdate() {
		try {
			MarksheetBean bean = model.findByPK(2L);
           //bean.setName("DAnishhhhh");
			bean.setChemistry(97);
			bean.setMaths(90);
			bean.setStudentId(2);
			model.update(bean);
			System.out.println("Test update successfull");
			MarksheetBean updateBean = model.findByPK(2L);
			if(!"DAnishhhhh".equals(updateBean.getName())){
			  System.out.println("Test Update Fail"); }
			 
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testDelete() {
		try {
			MarksheetBean bean = new MarksheetBean();
			long pk = 3L;
			bean.setId(pk);
			model.delete(bean);
			MarksheetBean deletedBean = model.findByPK(pk);
			if (deletedBean != null) {
				System.out.println("test delete fail ");
			}

		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testAdd() {
		try {

			MarksheetBean bean = new MarksheetBean();
			bean.setRollNo("Ac1234");
			bean.setPhysics(82);
			bean.setChemistry(99);
			bean.setMaths(75);
			bean.setStudentId(2L);
			bean.setCreatedBy("Faculty");
			bean.setModifiedBy("Faculty");
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			long pk = model.add(bean);

			MarksheetBean addedBean = model.findByPK(pk);
			if (addedBean == null) {
				System.out.println("test add fail");
			}
		} catch (ApplicationException e) {

			e.printStackTrace();
		} catch (DuplicateRecordException e) {

			e.printStackTrace();
		}

	}

}
