package in.com.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.com.rays.proj4.bean.CollegeBean;
import in.com.rays.proj4.bean.StudentBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DatabaseException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.util.JDBCDataSource;

//JDBC Implement of student model
public class StudentModel {

	private static Logger log = Logger.getLogger(StudentModel.class);

	/**
	 * create id
	 * 
	 * @return pk
	 * @throws DatabaseException
	 */
	public Integer nextPK() throws DatabaseException {
		// log.debug("Model nextPK Started");

		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement("select max(id) from st_student");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);

			}
			rs.close();
		} catch (Exception e) {
			// log.error("Database Exception..",e);
			throw new DatabaseException("Exception: Exception in getting PK");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model nextPK End");
		return pk + 1;
	}

	/**
	 * add student
	 * 
	 * @param bean
	 * @return pk
	 * @throws DuplicateRecordException
	 * @throws ApplicationException
	 */
	public long add(StudentBean bean) throws ApplicationException, DuplicateRecordException {
		// log.debug("Model add started");
		int pk = 0;

		CollegeModel cModel = new CollegeModel();
		System.out.println("djfkv;ksjg,,,,,," + bean.getCollegeId());
		CollegeBean collegeBean = cModel.findByPK(bean.getCollegeId());

		bean.setCollegeName(collegeBean.getName());
		System.out.println("college name :" + bean.getCollegeId());
		// StudentBean duplicateName=findByEmailId(bean.getEmail());

		// if (duplicateName!=null){
		// throw new DuplicateRecordException("Email already exist");
		// }
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			System.out.println(pk + " in ModelJDBC");
			conn.setAutoCommit(false);
			PreparedStatement stmt = conn.prepareStatement("insert into st_student values(?,?,?,?,?,?,?,?,?,?,?,?)");
			stmt.setInt(1, pk);
			stmt.setLong(2, bean.getCollegeId());
			stmt.setString(3, bean.getCollegeName());
			stmt.setString(4, bean.getFirstName());
			stmt.setString(5, bean.getLastName());
			System.out.println("11111111");
			stmt.setDate(6, new java.sql.Date(bean.getDob().getTime()));
			System.out.println("222222");
			stmt.setString(7, bean.getMobileNo());
			stmt.setString(8, bean.getEmail());
			stmt.setString(9, bean.getCreatedBy());
			stmt.setString(10, bean.getModifiedBy());
			stmt.setTimestamp(11, bean.getCreatedDatetime());
			stmt.setTimestamp(12, bean.getModifiedDatetime());
			stmt.executeUpdate();
			System.out.println("added");
			conn.commit();
			stmt.close();

		} catch (Exception e) {
			// log.error("Database Exception..",e);
			e.printStackTrace();
			try {
				conn.rollback();

			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception: add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception:Exception in add student");

		} finally {
			JDBCDataSource.closeConnection(conn);

		}
		// log.debug("Model add End");
		return pk;
	}

	/**
	 * delete student
	 * 
	 * @param bean
	 * @throws ApplicationException
	 */
	public void delete(StudentBean bean) throws ApplicationException {
		// log.debug("Model delete Started");

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement stmt = conn.prepareStatement("delete from st_student where ID=?");
			stmt.setLong(1, bean.getId());
			stmt.executeUpdate();
			conn.commit();
			stmt.close();

		} catch (Exception e) {
			// log.error("Database exception..",e);
			try {
				conn.rollback();

			} catch (Exception ex) {
				throw new ApplicationException("Exception: Delete rollback exception" + ex.getMessage());

			}
			throw new ApplicationException("Exception in delete Student");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model delete end");

	}

	/**
	 * find student with the help of emailId
	 * 
	 * @param email
	 * @return bean
	 * @throws ApplicationException
	 */
	public StudentBean findByEmailId(String email) throws ApplicationException {

		log.debug("Model findBy Email Started");
		StringBuffer sql = new StringBuffer("Select * from st_student where email=?");

		StudentBean bean = null;
		Connection conn = null;
		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql.toString());
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				bean = new StudentBean();
				bean.setId(rs.getLong(1));
				bean.setCollegeId(rs.getLong(2));
				bean.setCollegeName(rs.getString(3));
				bean.setFirstName(rs.getString(4));
				bean.setLastName(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setEmail(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));

			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception.. ", e);
			throw new ApplicationException("Exception: Exception in getting User by Email");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findBy Email End");
		return bean;

	}

	/**
	 * find student with the help of pk
	 * 
	 * @param pk
	 * @return bean
	 * @throws ApplicationException
	 */
	public StudentBean findByPK(long pk) throws ApplicationException {

		log.debug("Model findBy pk started");
		StringBuffer sql = new StringBuffer("Select * from st_student where id=?");

		StudentBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql.toString());
			stmt.setLong(1, pk);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				bean = new StudentBean();
				bean.setId(rs.getLong(1));
				bean.setCollegeId(rs.getLong(2));
				bean.setCollegeName(rs.getString(3));
				bean.setFirstName(rs.getString(4));
				bean.setLastName(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setEmail(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));

			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			e.printStackTrace();
			throw new ApplicationException("Exception:Exception in getting User by pk");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findBy Pk end");
		return bean;
	}

	/**
	 * update student
	 * 
	 * @param bean
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public void update(StudentBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model update Started");
		Connection conn = null;

		StudentBean beanExist = findByEmailId(bean.getEmail());

		if (beanExist != null && beanExist.getId() != bean.getId()) {
			throw new DuplicateRecordException("Email Id is already exist");

		}
		CollegeModel cModel = new CollegeModel();
		System.out.println("cmodel");
		CollegeBean collegeBean = cModel.findByPK(bean.getCollegeId());
		bean.setCollegeName(collegeBean.getName());

		System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvv" + collegeBean.getName());

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement stmt = conn.prepareStatement(
					"Update st_student set college_id=?,college_name=?,first_name=?,last_name=?,dob=?,mobile_no=?,email=?,created_by=?,modified_by=?,created_datetime=?,modified_datetime=? where id=?");
			stmt.setLong(1, bean.getCollegeId());
			stmt.setString(2, bean.getCollegeName());
			stmt.setString(3, bean.getFirstName());
			stmt.setString(4, bean.getLastName());
			stmt.setDate(5, new java.sql.Date(bean.getDob().getTime()));
			stmt.setString(6, bean.getMobileNo());
			stmt.setString(7, bean.getEmail());
			stmt.setString(8, bean.getCreatedBy());
			stmt.setString(9, bean.getModifiedBy());
			stmt.setTimestamp(10, bean.getCreatedDatetime());
			stmt.setTimestamp(11, bean.getModifiedDatetime());
			stmt.setLong(12, bean.getId());
			stmt.executeUpdate();
			conn.commit();
			stmt.close();

		} catch (Exception e) {
			log.error("DatabaseException..", e);
			try {
				conn.rollback();

			} catch (Exception ex) {
				throw new ApplicationException("Exception: delete rollback excption" + ex.getMessage());

			}
			throw new ApplicationException("Exception in update student");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model updarte end");

	}

	public List search(StudentBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	/**
	 * search student
	 * 
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */
	public List search(StudentBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model search Started");
		StringBuffer sql = new StringBuffer("select * from st_student where 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" And id= " + bean.getId());
			}
			if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
				sql.append(" And first_name like '" + bean.getFirstName() + "%'");

			}
			if (bean.getLastName() != null && bean.getLastName().length() > 0) {
				sql.append(" And last_name like '" + bean.getLastName() + "%'");
			}
			if (bean.getDob() != null && bean.getDob().getDate() > 0) {
				sql.append(" And Dob= " + bean.getDob());
			}
			if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
				sql.append(" And mobile_no like '" + bean.getMobileNo() + "%'");

			}
			if (bean.getEmail() != null && bean.getEmail().length() > 0) {
				sql.append(" And Email like '" + bean.getEmail() + "%'");
			}
			if (bean.getCollegeId() > 0) {
				sql.append(" AND COLLEGE_ID = " + bean.getCollegeId());
			}
			if (bean.getCollegeName() != null && bean.getCollegeName().length() > 0) {
				sql.append(" And College_name= '" + bean.getCollegeName() + "%'");

			}

		}
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" Limit " + pageNo + "," + pageSize);

		}
		ArrayList list = new ArrayList();
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql.toString());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				bean = new StudentBean();
				bean.setId(rs.getLong(1));
				bean.setCollegeId(rs.getLong(2));
				bean.setCollegeName(rs.getString(3));
				bean.setFirstName(rs.getString(4));

				bean.setLastName(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setEmail(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
				list.add(bean);
			}
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in search Student");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Serach End");
		return list;

	}

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * 
	 * list of student
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */
	public List list(int pageNo, int pageSize) throws ApplicationException {

		log.debug("Model list Started");

		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_student");

		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				StudentBean bean = new StudentBean();
				bean.setId(rs.getLong(1));
				bean.setCollegeId(rs.getLong(2));
				bean.setCollegeName(rs.getString(3));
				bean.setFirstName(rs.getString(4));
				bean.setLastName(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setEmail(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting list of Student");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model list End");
		return list;
	}

}
