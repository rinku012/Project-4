package in.com.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.com.rays.proj4.bean.CollegeBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.CollegeModel;

public class CollegeModelTest {

	public static CollegeModel model = new CollegeModel();

	public static void main(String[] args) throws Exception {
		// testAdd();
		// testDelete();
		// testUpdate();
		// testFindByPK();
		// testFindByName();
		// testSearch();
		// testList();
	}

	public static void testAdd() throws Exception {

		try {

			CollegeBean bean = new CollegeBean();
			// bean.setId(1);
			bean.setName("Patel College");
			bean.setAddress("bypass ");
			bean.setState("MPok");
			bean.setCity("ujjian");
			bean.setPhonoNo("9455741115");
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

			long pk = model.add(bean);
			System.out.println("add test secc");
			CollegeBean addbean = model.findByPK(pk);
			if (addbean == null) {
				System.out.println("add fail");
			}
		} catch (ApplicationException e) {

			e.printStackTrace();
		}

	}

	public static void testDelete() {
		try {
			CollegeBean bean = new CollegeBean();
			bean.setId(7L);

			model.delete(bean);
			if (bean == null) {
				System.out.println("test delete fail");
			}
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testUpdate() {
		try {

			CollegeBean bean = new CollegeBean();
			bean = model.findByPK(2L);
			bean.setName("Ahiu");
			bean.setAddress("Bhavarcuvannn");

			model.update(bean);
			System.out.println("test update  secc");

		} catch (DuplicateRecordException e) {

			e.printStackTrace();
		} catch (ApplicationException e) {

			e.printStackTrace();
		}

	}

	public static void testFindByPK() {
		try {
			CollegeBean bean = new CollegeBean();
			long pk = 2L;
			bean = model.findByPK(2L);
			if (bean == null) {
				System.out.println("Test Find By Pk fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getAddress());
			System.out.println(bean.getState());
			System.out.println(bean.getCity());
			System.out.println(bean.getPhonoNo());
//		System.out.println(bean.getCreatedBy());
//		System.out.println(bean.getCreatedDateTime());
//		System.out.println(bean.getModifiedBy());
//		System.out.println(bean.getModifiedDateTime());

		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testList() {
		try {
			CollegeBean bean = new CollegeBean();
			List list = new ArrayList();
			list = model.list(1, 10);
			if (list.size() < 0) {
				System.out.println("test list fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (CollegeBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getAddress());
				System.out.println("state" + bean.getState());
				System.out.println("city" + bean.getCity());
				System.out.println(bean.getPhonoNo());
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

			CollegeBean bean = new CollegeBean();
			List list = new ArrayList();
			bean.setName("Sage");
			list = model.search(bean);
			if (list.size() < 0) {
				System.out.println("test search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (CollegeBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getAddress());
				System.out.println("State :" + bean.getState());
				System.out.println("City :" + bean.getCity());
				System.out.println(bean.getPhonoNo());
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

	public static void testFindByName() {
		try {
			CollegeBean bean = new CollegeBean();
			bean = model.findByName("Ahilya Devi");
			if (bean == null) {
				System.out.println("Test Find By Name Fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getAddress());
			System.out.println(bean.getState());
			System.out.println(bean.getCity());
			System.out.println(bean.getPhonoNo());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getModifiedDatetime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

}
