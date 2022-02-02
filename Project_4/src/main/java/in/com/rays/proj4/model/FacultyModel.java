package in.com.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import org.apache.log4j.Logger;

import in.com.rays.proj4.bean.CollegeBean;
import in.com.rays.proj4.bean.CourseBean;
import in.com.rays.proj4.bean.FacultyBean;
import in.com.rays.proj4.bean.SubjectBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DatabaseException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.util.JDBCDataSource;

// TODO: Auto-generated Javadoc
/**
 * JDBC Implementation of FacultyModel.
 *
 * @author Rinku
 * @version 1.0 Copyright (c) SunilOS
 */

public class FacultyModel {

	/** The log. */
	public static Logger log = Logger.getLogger(FacultyModel.class);

	/**
	 * Find next PK of Faculty.
	 *
	 * @return the integer
	 * @throws ApplicationException the application exception
	 */

	public Integer nextPk() throws ApplicationException {
		log.debug("Faculty Model nextPK method Started");
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(id) FROM ST_FACULTY");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {
			log.error("DataBase Exception ..", e);
			throw new ApplicationException("Exception in Getting the PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		    log.debug("Faculty Model nextPK method End");
		return pk + 1;
	}

	/**
	 * Add a Faculty.
	 *
	 * @param bean the bean
	 * @return the long
	 * @throws ApplicationException     the application exception
	 * @throws DuplicateRecordException the duplicate record exception
	 */

	public long add(FacultyBean bean) throws ApplicationException, DuplicateRecordException {
		// log.debug("Model add Started");

		Connection conn = null;
		// System.out.println("gender :- "+bean.getGender());
		int pk = 0;
		CollegeModel collegeModel = new CollegeModel();
		CollegeBean collegeBean = collegeModel.findByPK(bean.getCollegeId());

		bean.setCollegeName(collegeBean.getName());
		System.out.println("college name          +++++++"+ collegeBean.getName());   

		CourseModel courseModel = new CourseModel();
		CourseBean courseBean = courseModel.findByPK(bean.getCourseId());

		bean.setCourseName(courseBean.getCourseName());

		SubjectModel subjectModel = new SubjectModel();
		SubjectBean subjectbean = subjectModel.findByPK(bean.getSubjectId());

		bean.setSubjectName(subjectbean.getSubjectName());

		FacultyBean beanExist = findByEmail(bean.getEmailId());
		if (beanExist != null) {
			throw new DuplicateRecordException("Email already exists");
		}
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPk();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn
					.prepareStatement("INSERT INTO st_faculty VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setLong(2, bean.getCollegeId());
			pstmt.setLong(3, bean.getSubjectId());
			pstmt.setLong(4, bean.getCourseId());
			pstmt.setString(5, bean.getFirstName());
			pstmt.setString(6, bean.getLastName());
			pstmt.setString(7, bean.getGender());
			pstmt.setDate(8, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(9, bean.getEmailId());
			pstmt.setString(10, bean.getMobileNo());
			pstmt.setString(11, bean.getCourseName());
			pstmt.setString(12, bean.getCollegeName());
			pstmt.setString(13, bean.getSubjectName());
			pstmt.setString(14, bean.getCreatedBy());
			pstmt.setString(15, bean.getModifiedBy());
			pstmt.setTimestamp(16, bean.getCreatedDatetime());
			pstmt.setTimestamp(17, bean.getModifiedDatetime());

			pstmt.executeUpdate();
			conn.commit(); // End transaction
			System.out.println("faculty add close");
			pstmt.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			e.printStackTrace();
			// throw new ApplicationException("Exception : Exception in add Faculty");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		return pk;
	}

	/**
	 * Delete a Faculty.
	 *
	 * @param bean the bean
	 * @throws ApplicationException the application exception
	 */

	public void delete(FacultyBean bean) throws ApplicationException {
		// log.debug("Faculty Model Delete method End");
		Connection conn = null;
		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_FACULTY WHERE ID=?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			log.error("DATABASE EXCEPTION ", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception in Faculty Model rollback" + ex.getMessage());
			}
			throw new ApplicationException("Exception in Faculty Model Delete Method");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Faculty Model delete method End");
	}

	/**
	 * Update a Faculty.
	 *
	 * @param bean the bean
	 * @throws ApplicationException     the application exception
	 * @throws DuplicateRecordException the duplicate record exception
	 */

	public void update(FacultyBean bean) throws ApplicationException, DuplicateRecordException {
		//log.debug("Model update Started");
		Connection conn = null;
		CollegeModel collegeModel = new CollegeModel();
		CollegeBean collegeBean = collegeModel.findByPK(bean.getCollegeId());
		bean.setCollegeName(collegeBean.getName());

		CourseModel courseModel = new CourseModel();
		CourseBean courseBean = courseModel.findByPK(bean.getCourseId());
		bean.setCourseName(courseBean.getCourseName());

		SubjectModel subjectModel = new SubjectModel();
		SubjectBean subjectBean = subjectModel.findByPK(bean.getSubjectId());
		bean.setSubjectName(subjectBean.getSubjectName());

		FacultyBean beanExist = findByEmail(bean.getEmailId());
		// Check if updated EmailId already exist
		if (beanExist != null && !(beanExist.getId() == bean.getId())) {
			throw new DuplicateRecordException("EmailId is already exist");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_FACULTY SET COLLEGE_ID=?,SUBJECT_ID=?,COURSE_ID=?, FIRST_NAME=?, LAST_NAME=?, GENDER=?, DOB=?,  EMAIL_ID=?, MOBILE_NO=? , COURSE_NAME=?,  COLLEGE_NAME=?, SUBJECT_NAME=?, CREATED_BY=? , MODIFIED_BY=? , CREATED_DATETIME=? , MODIFIED_DATETIME=? WHERE ID=? ");

			pstmt.setLong(1, bean.getCollegeId());
			pstmt.setLong(2, bean.getSubjectId());
			pstmt.setLong(3, bean.getCourseId());
			pstmt.setString(4, bean.getFirstName());
			pstmt.setString(5, bean.getLastName());
			pstmt.setString(6, bean.getGender());
			pstmt.setDate(7, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(8, bean.getEmailId());
			pstmt.setString(9, bean.getMobileNo());
			pstmt.setString(10, bean.getCourseName());
			pstmt.setString(11, bean.getCollegeName());
			pstmt.setString(12, bean.getSubjectName());
			pstmt.setString(13, bean.getCreatedBy());
			pstmt.setString(14, bean.getModifiedBy());
			pstmt.setTimestamp(15, bean.getCreatedDatetime());
			pstmt.setTimestamp(16, bean.getModifiedDatetime());
			pstmt.setLong(17, bean.getId());

			pstmt.executeUpdate();

			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			//log.error("DATABASE EXCEPTION ...", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception in rollback faculty model .." + ex.getMessage());
			}
			throw new ApplicationException("Exception in faculty model Update Method..");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		//log.debug("Faculty Model update method End");
	}

	/**
	 * Find User by Faculty name.
	 *
	 * @param EmailId : get parameter
	 * @return bean
	 * @throws ApplicationException the application exception
	 */

	public FacultyBean findByEmail(String EmailId) throws ApplicationException {

		System.out.println("faculty add find by name");
		//log.debug("Faculty Model findByName method Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE EMAIL_ID=?");
		Connection conn = null;
		FacultyBean bean = null;

		System.out.println(" faculty  find by name 1");
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			System.out.println("prepared");
			pstmt.setString(1, EmailId);
			System.out.println("resultset" + EmailId);
			ResultSet rs = pstmt.executeQuery();
			System.out.println(" faculty  find by name 1 while");
			while (rs.next()) {
				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setCollegeId(rs.getLong(2));
				bean.setSubjectId(rs.getLong(3));
				bean.setCourseId(rs.getLong(4));
				bean.setFirstName(rs.getString(5));
				bean.setLastName(rs.getString(6));
				bean.setGender(rs.getString(7));
				bean.setDob(rs.getDate(8));
				bean.setEmailId(rs.getString(9));
				bean.setMobileNo(rs.getString(10));
				bean.setCourseName(rs.getString(11));
				bean.setCollegeName(rs.getString(12));
				bean.setSubjectName(rs.getString(13));
				bean.setCreatedBy(rs.getString(14));
				bean.setModifiedBy(rs.getString(15));
				bean.setCreatedDatetime(rs.getTimestamp(16));
				bean.setModifiedDatetime(rs.getTimestamp(17));
				System.out.println(" faculty  find by name 3");
			}
			rs.close();
		} catch (Exception e) {
			//log.error("database exception ...", e);
			throw new ApplicationException("Exception : Exception in faculty model in findbyName method");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		System.out.println(" faculty  find by name 4");
		//log.debug("Faculty Model findbyName method End");
		return bean;
	}

	/**
	 * Find User by Faculty PK.
	 *
	 * @param pk : get parameter
	 * @return bean
	 * @throws ApplicationException the application exception
	 */

	public FacultyBean findByPk(long pk) throws ApplicationException {
		//log.debug("Faculty Model findByPK method Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE ID=?");
		Connection conn = null;
		FacultyBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setCollegeId(rs.getLong(2));
				bean.setSubjectId(rs.getLong(3));
				bean.setCourseId(rs.getLong(4));
				bean.setFirstName(rs.getString(5));
				bean.setLastName(rs.getString(6));
				bean.setGender(rs.getString(7));
				bean.setDob(rs.getDate(8));
				bean.setEmailId(rs.getString(9));
				bean.setMobileNo(rs.getString(10));
				bean.setCourseName(rs.getString(11));
				bean.setCollegeName(rs.getString(12));
				bean.setSubjectName(rs.getString(13));
				bean.setCreatedBy(rs.getString(14));
				bean.setModifiedBy(rs.getString(15));
				bean.setCreatedDatetime(rs.getTimestamp(16));
				bean.setModifiedDatetime(rs.getTimestamp(17));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			//log.error("database exception ...", e);
			throw new ApplicationException("Exception : Exception in findByPK in faculty model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		//log.debug("Faculty Model FindByPK method end");
		return bean;
	}

	/**
	 * Search Faculty.
	 *
	 * @param bean : Search Parameters
	 * @return the list
	 * @throws ApplicationException the application exception
	 */

	public List search(FacultyBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	/**
	 * Search Faculty with pagination.
	 *
	 * @param bean     : Search Parameters
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @return list : List of Users
	 * @throws ApplicationException the application exception
	 */

	public List search(FacultyBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Faculty Model search  method Started");
		System.out.println("faculty model");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE true");
		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getCollegeId() > 0) {
				sql.append(" AND college_Id = " + bean.getCollegeId());
			}
			if (bean.getFirstName() != null && bean.getFirstName().trim().length() > 0) {
				sql.append(" AND FIRST_NAME like '" + bean.getFirstName() + "%'");
			}
			if (bean.getLastName() != null && bean.getLastName().trim().length() > 0) {
				sql.append(" AND LAST_NAME like '" + bean.getLastName() + "%'");
			}

			if (bean.getEmailId() != null && bean.getEmailId().length() > 0) {
				sql.append(" AND Email_Id like '" + bean.getEmailId() + "%'");
			}

			if (bean.getGender() != null && bean.getGender().length() > 0) {
				sql.append(" AND Gender like '" + bean.getGender() + "%'");
			}

			if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
				sql.append(" AND Mobile_No like '" + bean.getMobileNo() + "%'");
			}

			if (bean.getCollegeName() != null && bean.getCollegeName().length() > 0) {
				sql.append(" AND college_Name like '" + bean.getCollegeName() + "%'");
			}
			if (bean.getCourseId() > 0) {
				sql.append(" AND course_Id = " + bean.getCourseId());
			}
			if (bean.getCourseName() != null && bean.getCourseName().length() > 0) {
				sql.append(" AND course_Name like '" + bean.getCourseName() + "%'");
			}
			if (bean.getSubjectId() > 0) {
				sql.append(" AND Subject_Id = " + bean.getSubjectId());
			}
			if (bean.getSubjectName() != null && bean.getSubjectName().length() > 0) {
				sql.append(" AND subject_Name like '" + bean.getSubjectName() + "%'");
			}
		}

		// if page no is greater then zero then apply pagination
		System.out.println("model page ........" + pageNo + " " + pageSize);
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " , " + pageSize);
		}
		System.out.println("final sql  " + sql);
		Connection conn = null;
		ArrayList list = new ArrayList();
		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {

				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setCollegeId(rs.getLong(2));
				bean.setSubjectId(rs.getLong(3));
				bean.setCourseId(rs.getLong(4));
				bean.setFirstName(rs.getString(5));
				bean.setLastName(rs.getString(6));
				bean.setGender(rs.getString(7));
				bean.setDob(rs.getDate(8));
				bean.setEmailId(rs.getString(9));
				bean.setMobileNo(rs.getString(10));
				bean.setCourseName(rs.getString(11));
				bean.setCollegeName(rs.getString(12));
				bean.setSubjectName(rs.getString(13));
				bean.setCreatedBy(rs.getString(14));
				bean.setModifiedBy(rs.getString(15));
				bean.setCreatedDatetime(rs.getTimestamp(16));
				bean.setModifiedDatetime(rs.getTimestamp(17));
				System.out.println("out whiile");
				list.add(bean);
				System.out.println("list size ----------->" + list.size());
			}
			rs.close();

		} catch (Exception e) {
			log.error("database Exception .. ", e);
			e.printStackTrace();
			// throw new ApplicationException("Exception : Exception in Search method of
			// Faculty Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Faculty Model search  method End");
		// System.out.println("retuen >>>>>>>>>>>>>>>"+list.size());
		return list;

	}

	/**
	 * Get List of Faculty.
	 *
	 * @return list : List of Faculty
	 * @throws ApplicationException the application exception
	 */

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * Get List of Faculty with pagination.
	 *
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @return list : List of Faculty
	 * @throws ApplicationException the application exception
	 */

	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Faculty Model List method Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY");
		Connection conn = null;
		ArrayList list = new ArrayList();

		// if page is greater than zero then apply pagination
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " , " + pageSize);
		}
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				FacultyBean bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setCollegeId(rs.getLong(2));
				bean.setSubjectId(rs.getLong(3));
				bean.setCourseId(rs.getLong(4));

				bean.setFirstName(rs.getString(5));
				bean.setLastName(rs.getString(6));
				bean.setGender(rs.getString(7));
				bean.setDob(rs.getDate(8));
				bean.setEmailId(rs.getString(9));
				bean.setMobileNo(rs.getString(10));
				bean.setCourseName(rs.getString(11));
				bean.setCollegeName(rs.getString(12));
				bean.setSubjectName(rs.getString(13));
				bean.setCreatedBy(rs.getString(14));
				bean.setModifiedBy(rs.getString(15));
				bean.setCreatedDatetime(rs.getTimestamp(16));
				bean.setModifiedDatetime(rs.getTimestamp(17));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception ......", e);
			throw new ApplicationException("Exception in list method of FacultyModel");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Faculty Model List method End");
		return list;
	}
}
