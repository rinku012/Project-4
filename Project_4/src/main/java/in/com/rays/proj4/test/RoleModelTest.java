package in.com.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.com.rays.proj4.bean.RoleBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.RoleModel;

public class RoleModelTest {

	public static RoleModel model = new RoleModel();

	public static void main(String[] args) throws Exception {
		// testAdd();
		// testDelete();
		// testUpdate();
		// testFindByPK();
		// testFindByName();
		 testSearch();
		// testList();
	}

	public static void testList() {
		try {
			RoleBean bean = new RoleBean();
			List list = new ArrayList();

			list = model.list(1, 10);
			if (list.size() < 0) {
				System.out.println("Test List Fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (RoleBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getCreatedDatetime());
			}
		} catch (ApplicationException e) {

			e.printStackTrace();
		}

	}

	public static void testSearch() {
		try {
			RoleBean bean = new RoleBean();
			List list = new ArrayList();
			bean.setName("Admin");
			
			list = model.search(bean, 0, 0);
			System.out.println(" search ok");
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (RoleBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getModifiedDatetime());
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testFindByName() {
		try {
			RoleBean bean = new RoleBean();

			bean = model.findByName("Rinku");
			if (bean == null) {
				System.out.println("Test FindBY Name Fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getDescription());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testFindByPK() {
		try {
			RoleBean bean = new RoleBean();
			long pk = 1L;
			bean = model.findByPK(pk);
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testUpdate() {

		try {
			RoleBean bean = model.findByPK(1L);
			bean.setName("Ragahav");
			bean.setDescription("Ejjjjjjjjng");
			bean.setModifiedBy("admin");
			model.update(bean);
			System.out.println("Updated");

			/*
			 * RoleBean updatedbean = model.findByPK(3L); if
			 * (!"12".equals(updatedbean.getName())) {
			 * System.out.println("Test Update fail"); }
			 */
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testDelete() throws Exception {
		try {

			RoleBean bean = new RoleBean();
			long pk = 5L;
			bean.setId(pk);

			RoleModel model = new RoleModel();

			model.delete(bean);
			System.out.println("deleted");
		} catch (Exception e) {

		}

//			RoleModel deletebean = model.findByPK(pk);
//		
//       if(bean==null) {
//   	   System.out.println("Test Delete fail");
//       }
//       } catch (ApplicationException e) {
//			
//			e.printStackTrace();
//		}
//				

	}

	public static void testAdd() throws Exception {
		try {
			RoleBean bean = new RoleBean();
			// bean.setId(1);
			bean.setName("Rahul");
			bean.setDescription("okthik");
			bean.setCreatedBy("Admin");
			bean.setModifiedBy("Admin");
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));

			long PK = model.add(bean);
			/*
			 * RoleModel model =new RoleModel(); model.add(bean); if(bean==null) {
			 * System.out.println("add test fail"); }
			 */
		}

		catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}
}
