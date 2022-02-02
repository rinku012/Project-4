package in.com.rays.proj4.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.com.rays.proj4.bean.UserBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.exception.RecordNotFoundException;
import in.com.rays.proj4.model.UserModel;

public class UserModelTest {

	public static UserModel model = new UserModel();

	public static void main(String[] args) throws ParseException {
		// testAdd();
		// testDelete();
		// testUpdate();
		// testFindByPK();
		// testFindByLogin();
		 //testSearch();
		// testGetRoles();
		// testList();
		// testAuthenticate();
		// testRegisterUser();
		// testChangePassword();
		// testForgetPassword();
		// testResetPassword();

	}

	public static void testAdd() {
		try {
			UserBean bean = new UserBean();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");

			bean.setFirstName("rahul");
			bean.setLastName("sharma");
			bean.setLogin("rao@gmail.com");
			bean.setConfirmPassword("Asdfgh@12345");
			bean.setPassword("Asdfgh@12345");
			bean.setDob(sdf.parse("12-12-2067"));

			bean.setMobileNo("123456678");
			bean.setModifiedBy("admin");
			bean.setGender("male");
			bean.setLastLoginIP("2332");
			// bean.setLastLogin(new Timestamp(new Date().getTime()));
			bean.setRegisteredIP("riku");
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			bean.setRoleId(2L);
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));

			long pk = model.add(bean);
			UserBean addedbean = model.findByPK(pk);
			System.out.println("Test Add Secc");
			if (addedbean == null) {
				System.out.println("Test Add fail");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void testDelete() {
		try {
			UserBean bean = new UserBean();
			bean.setId(9L);
			model.delete(bean);
			System.out.println("Test delet succes" + bean.getId());
			UserBean deletedbean = model.findByPK(9L);

			if (deletedbean == null) {
				System.out.println("Test Delet fail");
			}

		} catch (ApplicationException e) {

			e.printStackTrace();
		}
	}

	public static void testUpdate() {

		try {

			UserBean bean = new UserBean();
			bean = model.findByPK(2L);
			bean.setFirstName("Rinku3");
			 bean.setLastName("luthra");
			// bean.setLogin("gm@gmail.com");
			// bean.setPassword("password");
			// bean.setRoleId(4L);
			// bean.setPassword("pappuyadav@gamil.com");

			model.update(bean);
			System.out.println("updated secc");
			// UserBean updatebean = model.findByPK(8L);

		} catch (ApplicationException e) {

			e.printStackTrace();
		} catch (DuplicateRecordException e) {

			e.printStackTrace();
		}

	}

	public static void testFindByPK() {
		try {
			UserBean bean = model.findByPK(3L);
			if (bean == null) {
				System.out.println("test find by pk fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getLogin());
			System.out.println(bean.getPassword());
			System.out.println(bean.getDob());
			System.out.println(bean.getRoleId());
			System.out.println(bean.getUnSuccessfulLogin());
			System.out.println(bean.getGender());
			System.out.println(bean.getLastLogin());
			System.out.println(bean.getLock());
		} catch (ApplicationException e) {

			e.printStackTrace();
		}

	}

	public static void testFindByLogin() {
		try {
			UserBean bean = new UserBean();

			bean = model.findByLogin("yv@gmail.com");
			if (bean == null) {
				System.out.println("test findbylogin fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getGender());
			System.out.println(bean.getLogin());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getDob());
			System.out.println(bean.getRoleId());
			System.out.println(bean.getUnSuccessfulLogin());
			System.out.println(bean.getGender());
			System.out.println(bean.getLastLogin());
			System.out.println(bean.getLock());
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testSearch() {
		try {

			UserBean bean = new UserBean();
			List list = new ArrayList();
			bean.setFirstName("Rinku");
			list = model.search(bean);
			if (list.size() < 0) {
				System.out.println("test search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (UserBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getLogin());
				System.out.println(bean.getPassword());
				System.out.println(bean.getDob());
				System.out.println(bean.getRoleId());
				System.out.println(bean.getUnSuccessfulLogin());
				System.out.println(bean.getGender());
				System.out.println(bean.getLastLogin());
				System.out.println(bean.getLock());
			}
		} catch (ApplicationException e) {

			e.printStackTrace();
		}

	}

	public static void testResetPassword() {

		UserBean bean = new UserBean();
		try {
			bean = model.findByLogin("ranjitchoudhary20@gmail.com");
			if (bean != null) {
				boolean pass = model.resetPassword(bean);
				if (pass = false) {
					System.out.println("Test Update fail");
				}
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testForgetPassword() {

		try {
			boolean b = model.forgetPassword("py@gmail.com");

			System.out.println("Suucess : Test Forget Password Success");

		} catch (RecordNotFoundException e) {
			e.printStackTrace();
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testChangePassword() {
		try {
			UserBean bean = model.findByLogin("As@gmail.com");
			String oldPassword = bean.getPassword();
			bean.setId(2L);
			bean.setPassword("Asdfgh@909090");
			// bean.setConfirmPassword("9755741125");
			String newPassword = bean.getPassword();

			try {
				model.changePassword(2L, oldPassword, newPassword);
				System.out.println("Password hasbeen change succesfully");
			} catch (RecordNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testRegisterUser() throws ParseException {
		try {

			UserBean bean = new UserBean();
			SimpleDateFormat sdf = new SimpleDateFormat("mm-dd-yyyy");

			bean.setFirstName("Rakesh");
			bean.setLastName("raikvar");
			bean.setLogin("Rr@gmail.com");
			bean.setPassword("okrakesh");
			bean.setConfirmPassword("okrakesh");
			bean.setDob(sdf.parse("12-01-2002"));
			bean.setGender("Male");
			bean.setMobileNo("9826556541");
			bean.setRoleId(2);

			long pk = model.registerUser(bean);

			System.out.println("Successfully register");
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLogin());
			System.out.println(bean.getLastName());
			System.out.println(bean.getDob());

			UserBean registerbean = model.findByPK(pk);
			if (registerbean != null) {
				System.out.println("test register fail");
			}

		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testAuthenticate() {
		try {
			UserBean bean = new UserBean();
			bean.setLogin("yv@gmail.com");
			bean.setPassword("password");
			bean = model.authenticate(bean.getLogin(), bean.getPassword());
			if (bean != null) {
				System.out.println("Successfully Login");
			} else {
				System.out.println("Invailid login id & password");
			}

		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testList() {
		try {
			UserBean bean = new UserBean();
			List list = new ArrayList();

			list = model.list(1, 2);
			if (list.size() < 0) {
				System.out.println("test listn fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (UserBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getLogin());
				System.out.println(bean.getPassword());
				System.out.println(bean.getDob());
				System.out.println(bean.getRoleId());
				System.out.println(bean.getUnSuccessfulLogin());
				System.out.println(bean.getGender());
				System.out.println(bean.getLastLogin());
				System.out.println(bean.getLock());
				System.out.println(bean.getMobileNo());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedDatetime());

			}
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testGetRoles() {
		try {
			UserBean bean = new UserBean();
			List list = new ArrayList();
			bean.setRoleId(2L);
			list = model.getRoles(bean);
			if (list.size() < 0) {
				System.out.println("test get roles fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (UserBean) it.next();

				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getLogin());
				System.out.println(bean.getPassword());
				System.out.println(bean.getDob());
				System.out.println(bean.getRoleId());
				System.out.println(bean.getUnSuccessfulLogin());
				System.out.println(bean.getGender());
				System.out.println(bean.getLastLogin());
				System.out.println(bean.getLock());
			}

		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
