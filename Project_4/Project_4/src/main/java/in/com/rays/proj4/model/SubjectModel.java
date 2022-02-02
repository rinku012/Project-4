package in.com.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.com.rays.proj4.bean.CourseBean;
import in.com.rays.proj4.bean.SubjectBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DatabaseException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.util.JDBCDataSource;

/**
 * @author Rinku
 *
 */
public class SubjectModel {

	private static Logger log = Logger.getLogger(SubjectModel.class);

	/**
	 * create id
	 * 
	 * @return
	 * @throws DatabaseException
	 */
	public Integer nextPK() throws DatabaseException {
		// log.debug("Model nextPK Started");
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement stmt = conn.prepareStatement("select max(id) from st_subject");

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {
			// log.error("Database Exception", e);
			throw new DatabaseException("Exception:Exception is getting PK");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		// log.debug("Model nextPK End");
		return pk + 1;
	}

	/**
	 * add subject
	 * 
	 * @param bean
	 * @return pk
	 * @throws DuplicateRecordException
	 * @throws ApplicationException
	 */
	public long add(SubjectBean bean) throws Exception {

		// log.debug("Model add Started");
		Connection conn = null;
		int pk = 0;

		CourseModel cmodel = new CourseModel();
		CourseBean cBean = cmodel.findByPK(bean.getCourseId());
		bean.setCourseName(cBean.getCourseName());

		SubjectBean duplicateSubjectName = findByName(bean.getSubjectName());

		if (duplicateSubjectName != null) {
			throw new DuplicateRecordException("Subject Name Already Exists");

		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false);
			PreparedStatement stmt = conn.prepareStatement("Insert into st_subject values(?,?,?,?,?,?,?,?,?)");

			stmt.setInt(1, pk);
			stmt.setString(2, bean.getSubjectName());
			stmt.setString(3, bean.getDescription());
			stmt.setString(4, bean.getCourseName());
			stmt.setLong(5, bean.getCourseId());
			stmt.setString(6, bean.getCreatedBy());
			stmt.setString(7, bean.getModifiedBy());
			stmt.setTimestamp(8, bean.getCreatedDatetime());
			stmt.setTimestamp(9, bean.getModifiedDatetime());

			stmt.executeUpdate();
			conn.commit();
			stmt.close();

		} catch (Exception e) {
			// log.error("Database Exception..",e);
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception:add rollback exception" + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add Subject");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model add End");
		return pk;

	}

	/**
	 * delete subject
	 * 
	 * @param bean
	 * @throws ApplicationException
	 */
	public void delete(SubjectBean bean) throws ApplicationException {
		// log.debug("Model Delete Started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement stmt = conn.prepareStatement("delete from st_subject where id=?");
			stmt.setLong(1, bean.getId());
			stmt.executeUpdate();
			conn.commit();
			stmt.close();

		} catch (Exception e) {
			// log.error("Database Exception..",e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception:Delete RollBack Exception" + ex.getMessage());

			}
			throw new ApplicationException("Exception:Exception in delete Subject");
		} finally {
			JDBCDataSource.closeConnection(conn);

		}
		// log.debug("Model delete end");

	}

	/**
	 * find subject by name
	 * 
	 * @param subjectname
	 * @return bean
	 * @throws ApplicationException
	 */
	public SubjectBean findByName(String subjectName) throws ApplicationException {
		// log.debug("Model FindByName Started ");
		StringBuffer sql = new StringBuffer("Select * from st_subject where subject_name=?");

		SubjectBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql.toString());
			stmt.setString(1, subjectName);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				bean = new SubjectBean();

				bean.setId(rs.getLong(1));
				bean.setSubjectName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCourseName(rs.getString(4));
				bean.setCourseId(rs.getLong(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}
			rs.close();

		} catch (Exception e) {
			// log.error("Database Exception..", e);
			throw new ApplicationException("Exception:Exception in getting Subject by Name");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model findByName Ended");
		return bean;
	}

	/**
	 * find subject by pk
	 * 
	 * @param pk
	 * @return bean
	 * @throws ApplicationException
	 */

	public SubjectBean findByPK(long pk) throws ApplicationException {
		// log.debug("Model findByPK Started");

		StringBuffer sql = new StringBuffer("select * from st_subject where id=?");

		SubjectBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql.toString());
			stmt.setLong(1, pk);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				bean = new SubjectBean();
				bean.setId(rs.getLong(1));
				bean.setSubjectName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCourseName(rs.getString(4));
				bean.setCourseId(rs.getLong(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}
			rs.close();

		} catch (Exception e) {
			// log.error("Database Exception..", e);
			throw new ApplicationException("Exception:Exception in getting Subject by pk");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model findByPK End");
		return bean;

	}

	/**
	 * update subject
	 * 
	 * @param bean
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */

	public void update(SubjectBean bean) throws DuplicateRecordException, ApplicationException {

		// log.debug("Model update started");

		Connection conn = null;

		CourseModel cmodel = new CourseModel();
		CourseBean cBean = cmodel.findByPK(bean.getCourseId());
		bean.setCourseName(cBean.getCourseName());

		SubjectBean subjectbean = findByName(bean.getCourseName());

		if (subjectbean != null && subjectbean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Subject is already exist");

		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement stmt = conn.prepareStatement(
					"Update st_subject set subject_name=?,description=?,course_name=?,course_id=?,created_by=?,modified_by=?,created_datetime=?,modified_datetime=? where id=?");

			stmt.setString(1, bean.getSubjectName());
			stmt.setString(2, bean.getDescription());
			stmt.setString(3, bean.getCourseName());
			stmt.setLong(4, bean.getCourseId());
			stmt.setString(5, bean.getCreatedBy());
			stmt.setString(6, bean.getModifiedBy());
			stmt.setTimestamp(7, bean.getCreatedDatetime());
			stmt.setTimestamp(8, bean.getModifiedDatetime());
			stmt.setLong(9, bean.getId());

			stmt.executeUpdate();
			conn.commit();
			stmt.close();

		} catch (Exception e) {
			// log.error("Database Exception..",e);

			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception:delete rollback exception" + ex.getMessage());

			}
			throw new ApplicationException("Exception in updating Subject");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model update End");

	}

	/**
	 * search subject
	 * 
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */
	public List search(SubjectBean bean, int pageNo, int pageSize) throws ApplicationException {
		System.out.println("SubjectModel search()................");
		// log.debug("Model search Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT WHERE 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			
			  if (bean.getSubjectName() != null && bean.getSubjectName().length() > 0) {
			  sql.append(" AND SUBJECT_NAME like '" + bean.getSubjectName() + "%'");
			  System.out.println("SubjectModel ki subject search sql:" +sql); }
			 
			if (bean.getDescription() != null && bean.getDescription().length() > 0) {
				sql.append(" AND Description like '" + bean.getDescription() + "%'");
			}
			if (bean.getCourseName() != null && bean.getCourseName().length() > 0) {
				sql.append(" AND COURSE_NAME like '" + bean.getCourseName() + "%'");
				System.out.println("SubjectModel ki course search sql:" +sql);
			}
			if (bean.getCourseId() > 0) {
				sql.append(" AND COURSE_ID = " + bean.getCourseId());
			}

		}

		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
		}

		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new SubjectBean();
				bean.setId(rs.getLong(1));
				bean.setSubjectName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCourseName(rs.getString(4));
				bean.setCourseId(rs.getLong(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in search course");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model search End");
		return list;
	}

	public List search(SubjectBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * list of subject
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */
	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_subject");
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				SubjectBean bean = new SubjectBean();
				bean.setId(rs.getLong(1));
				bean.setSubjectName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCourseName(rs.getString(4));
				bean.setCourseId(rs.getLong(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting list of users");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model list End");
		return list;

	}
}
