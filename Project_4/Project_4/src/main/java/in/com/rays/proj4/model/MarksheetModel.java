package in.com.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.com.rays.proj4.bean.MarksheetBean;
import in.com.rays.proj4.bean.StudentBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DatabaseException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.util.JDBCDataSource;

//JDBC Implements of marksheet model

public class MarksheetModel {

	public Logger log = Logger.getLogger(MarksheetModel.class);

	/**
	 * add new id
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
			System.out.println("Connection Succesfully Establish (pk method in marksheet model)");

			PreparedStatement stmt = conn.prepareStatement("select max(id) from st_marksheet");

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {
			// log.error(e);
			throw new DatabaseException("Exception in Marksheet getting PK");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model nextPK End");
		return pk + 1;

	}

	/**
	 * add new marksheet
	 * 
	 * @param bean
	 * @return pk
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public long add(MarksheetBean bean) throws ApplicationException, DuplicateRecordException {
		// log.debug("Model add Started");

		Connection conn = null;

		StudentModel sModel = new StudentModel();
		StudentBean studentbean = sModel.findByPK(bean.getStudentId());
		bean.setName(studentbean.getFirstName() + "" + studentbean.getLastName());

		MarksheetBean duplicateMarksheet = findByRollNo(bean.getRollNo());
		int pk = 0;

		if (duplicateMarksheet != null) {
			throw new DuplicateRecordException("Roll Number already exist");

		}
		try {
			System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa add method");
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false);
			PreparedStatement stmt = conn.prepareStatement("insert into st_marksheet values(?,?,?,?,?,?,?,?,?,?,?)");

			stmt.setInt(1, pk);
			stmt.setString(2, bean.getRollNo());
			stmt.setLong(3, bean.getStudentId());
			stmt.setString(4, bean.getName());
			stmt.setInt(5, bean.getPhysics());
			stmt.setInt(6, bean.getChemistry());
			stmt.setInt(7, bean.getMaths());
			stmt.setString(8, bean.getCreatedBy());
			stmt.setString(9, bean.getModifiedBy());
			stmt.setTimestamp(10, bean.getCreatedDatetime());
			stmt.setTimestamp(11, bean.getModifiedDatetime());

			stmt.executeUpdate();
			System.out.println(" add method");
			conn.commit();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error(e);
			try {
				conn.rollback();
			} catch (Exception ex) {

				throw new ApplicationException("add rollback exception" + ex.getMessage());
			}
			throw new ApplicationException("Exception in add marksheet");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model add end");
		return pk;
	}

	/**
	 * delete marksheet information
	 * 
	 * @param bean
	 * @throws ApplicationException
	 */
	public void delete(MarksheetBean bean) throws ApplicationException {
		// log.debug("Model delete Started");
		System.out.println("marksheet model Method delete Started");
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement stmt = conn.prepareStatement("delete from st_marksheet where id=?");
			stmt.setLong(1, bean.getId());
			System.out.println("Deleted Marksheet");
			stmt.executeUpdate();
			conn.commit();
			stmt.close();
		} catch (Exception e) {
			// log.error(e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				// log.error(ex);
				throw new ApplicationException("Delete rollback exception" + ex.getMessage());

			}
			throw new ApplicationException("Exception in delete marksheet");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Mdel delete end");
	}

	/**
	 * find information with the help of rollno
	 * 
	 * @param rollNo
	 * @return bean
	 * @throws ApplicationException
	 */
	public MarksheetBean findByRollNo(String rollNo) throws ApplicationException {
		// log.debug("Model findByRollNo Started");

		System.out.println("marksheet model Method findbyrollno Started");
		StringBuffer sql = new StringBuffer("select * from st_marksheet where roll_no=?");

		MarksheetBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql.toString());
			stmt.setString(1, rollNo);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				bean = new MarksheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setStudentId(rs.getLong(3));
				bean.setName(rs.getString(4));
				bean.setPhysics(rs.getInt(5));
				bean.setChemistry(rs.getInt(6));
				bean.setMaths(rs.getInt(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
			}
			rs.close();
		} catch (Exception e) {
			// log.error(e);
			throw new ApplicationException("Exception in getting marksheet by rollno");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model findByRollNo end");
		return bean;
	}

	/**
	 * find information with the help of pk
	 * 
	 * @param pk
	 * @return bean
	 * @throws ApplicationException
	 */
	public MarksheetBean findByPK(long pk) throws ApplicationException {
		// log.debug("Model findByPK Started");
		System.out.println("marksheet model Method findbypk Started");
		StringBuffer sql = new StringBuffer("select *from st_marksheet where id=?");

		MarksheetBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql.toString());
			stmt.setLong(1, pk);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				bean = new MarksheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setStudentId(rs.getLong(3));
				bean.setName(rs.getString(4));
				bean.setPhysics(rs.getInt(5));
				bean.setChemistry(rs.getInt(6));
				bean.setMaths(rs.getInt(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
			}
			rs.close();
		} catch (Exception e) {
			// log.error(e);
			throw new ApplicationException("Exception in getting marksheet by pk");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model findByPK end");
		return bean;

	}

	/**
	 * update marksheet information
	 * 
	 * @param bean
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public void update(MarksheetBean bean) throws ApplicationException, DuplicateRecordException {

		// log.debug("Model Update Started");
		System.out.println("marksheet model Method update Started");

		Connection conn = null;
		MarksheetBean beanExist = findByRollNo(bean.getRollNo());

		/*
		 * if (beanExist!=null&&beanExist.getId()!=bean.getId()){
		 * 
		 * throw new DuplicateRecordException("rollno is already exist"); }
		 */
		StudentModel sModel = new StudentModel();
		StudentBean studentbean = sModel.findByPK(bean.getStudentId());
		bean.setName(studentbean.getFirstName() + "" + studentbean.getLastName());

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement stmt = conn.prepareStatement(
					"update st_marksheet set roll_no=?,student_id=?,name=?,physics=?,chemistry=?,maths=?,created_by=?,modified_by=?,created_datetime=?,modified_datetime=? where id=?");

			stmt.setString(1, bean.getRollNo());
			stmt.setLong(2, bean.getStudentId());
			stmt.setString(3, bean.getName());

			stmt.setInt(4, bean.getPhysics());
			stmt.setInt(5, bean.getChemistry());
			stmt.setInt(6, bean.getMaths());
			stmt.setString(7, bean.getCreatedBy());
			stmt.setString(8, bean.getModifiedBy());
			stmt.setTimestamp(9, bean.getCreatedDatetime());
			stmt.setTimestamp(10, bean.getModifiedDatetime());
			stmt.setLong(11, bean.getId());

			stmt.executeUpdate();
			conn.commit();
			stmt.close();
		} catch (Exception e) {
			// log.error(e);
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("update rollback exception" + ex.getMessage());
			}

			throw new ApplicationException("Exception in updating marksheet");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model add end");

	}

	public List search(MarksheetBean bean) throws Exception {
		System.out.println("marksheet model Method search 0 Started");
		return search(bean, 0, 0);
	}

	/**
	 * search marksheet
	 * 
	 * @param marksheet
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */
	public List search(MarksheetBean marksheet, int pageNo, int pageSize) throws ApplicationException {
		Connection con = null;
		System.out.println("<<>>>>>>>>>>>>> Search(1) method (rollNo)=" + marksheet.getRollNo());
		StringBuffer sql = new StringBuffer("select * from st_marksheet where 1=1");
		if (marksheet != null) {
			if (marksheet.getId() > 0) {
				sql.append(" AND ID = " + marksheet.getId());
			}
			if ((marksheet.getRollNo() != null) && (marksheet.getRollNo().length() > 0)) {

				sql.append(" AND ROLL_NO like '" + marksheet.getRollNo() + "%'");
			}
			if (marksheet.getStudentId() > 0) {
				sql.append(" AND STUDENT_ID = " + marksheet.getStudentId());
			}
			if (marksheet.getName() != null && marksheet.getName().length() > 0) {
				sql.append(" AND NAME like '" + marksheet.getName() + "%'");
			}
			/*
			 * if (marksheet.getPhysics() > 0) { sql.append(" AND PHYSICS = " +
			 * marksheet.getPhysics()); } if (marksheet.getChemistry() > 0) {
			 * sql.append(" AND CHEMISTRY = " + marksheet.getChemistry()); } if
			 * (marksheet.getMaths() > 0) { sql.append(" AND MATHS = " +
			 * marksheet.getMaths()); }
			 */
		}
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + "," + pageSize);

			// sql.append(" limit " + pageNo + "," + pageSize);
		}
		List list = new ArrayList();
		try {

			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				MarksheetBean mbean = new MarksheetBean();
				mbean.setId(rs.getLong(1));
				mbean.setRollNo(rs.getString(2));
				mbean.setStudentId(rs.getLong(3));
				mbean.setName(rs.getString(4));
				mbean.setPhysics(rs.getInt(5));
				mbean.setChemistry(rs.getInt(6));
				mbean.setMaths(rs.getInt(7));
				list.add(mbean);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("Database Exception..", e);

			// throw new ApplicationException("Exception : Exception in search time table");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		// log.debug("Model search End");

		return list;

	}

	/*
	 * public List search(MarksheetBean bean) throws ApplicationException{ return
	 * search(bean,0,0); }
	 */

	/*
	 * public List search(MarksheetBean bean1,int pageNo,int pageSize) throws
	 * ApplicationException{ log.debug("Model search Started");
	 * 
	 * StringBuffer sql=new StringBuffer("select * from st_marksheet where 1=1");
	 * 
	 * MarksheetBean bean;
	 * 
	 * if(bean1!=null){ System.out.println("service"+bean1.getName());
	 * if(bean1.getId()>0){ sql.append(" AND id= "+bean1.getId());
	 * 
	 * } if(bean1.getRollNo()!=null&&bean1.getRollNo().length()>0){
	 * sql.append(" AND roll_no like '"+bean1.getRollNo()+"%'"); }
	 * if(bean1.getName()!=null&&bean1.getName().length()>0){
	 * sql.append(" AND name like '"+bean1.getName()+"%'"); }
	 * if(bean1.getPhysics()!=null &&bean1.getPhysics()>0){
	 * sql.append(" AND physics= "+bean1.getPhysics());
	 * 
	 * } if(bean1.getChemistry()!=null && bean1.getChemistry()>0){
	 * sql.append(" AND chemistry= "+bean1.getChemistry()); }
	 * if(bean1.getMaths()!=null&&bean1.getMaths()>0){
	 * sql.append(" AND maths= "+bean1.getMaths()); }
	 * 
	 * } if(pageSize>0){ pageNo=(pageNo-1)*pageSize;
	 * 
	 * sql.append(" Limit "+pageNo+","+pageSize); } ArrayList list=new ArrayList();
	 * Connection conn=null; try{ conn=JDBCDataSource.getConnection();
	 * PreparedStatement stmt=conn.prepareStatement(sql.toString()); ResultSet
	 * rs=stmt.executeQuery();
	 * 
	 * while(rs.next()){ bean=new MarksheetBean(); bean.setId(rs.getLong(1));
	 * bean.setRollNo(rs.getString(2)); bean.setStudentId(rs.getLong(3));
	 * bean.setName(rs.getString(4)); bean.setPhysics(rs.getInt(5));
	 * bean.setChemistry(rs.getInt(6)); bean.setMaths(rs.getInt(7));
	 * bean.setCreatedBy(rs.getString(8)); bean.setModifiedBy(rs.getString(9));
	 * bean.setCreatedDateTime(rs.getTimestamp(10));
	 * bean.setModifiedDateTime(rs.getTimestamp(11)); } rs.close(); }catch(Exception
	 * e){ log.error(e); e.printStackTrace(); throw new
	 * ApplicationException("Update rollback exception"+e.getMessage());
	 * 
	 * }finally{ JDBCDataSource.closeConnection(conn); }
	 * log.debug("Model search end"); return list;
	 * 
	 * 
	 * }
	 */
	public List list() throws ApplicationException {
		System.out.println("marksheet model Method List(0) Started");
		return list(0, 0);
	}

	/**
	 * get List of Marksheet with pagination
	 *
	 * @return list : List of Marksheets
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws DatabaseException
	 */

	public List list(int pageNo, int pageSize) throws ApplicationException {

		System.out.println("marksheet model Method List(1) Started");
		// log.debug("Model list Started");

		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_marksheet");
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql.toString());
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				MarksheetBean bean = new MarksheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setStudentId(rs.getLong(3));
				bean.setName(rs.getString(4));
				bean.setPhysics(rs.getInt(5));
				bean.setChemistry(rs.getInt(6));
				bean.setMaths(rs.getInt(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));

				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			// log.error(e);
			throw new ApplicationException("Exception in getting list of marksheet");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model list End");
		return list;
	}

	/**
	 * get merit list
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */
	public List getMeritList(int pageNo, int pageSize) throws ApplicationException {

		System.out.println("marksheet model Method  getMeritList Started");
		// log.debug("Model Meritlist Started");

		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer(
				"select id,roll_no,name,physics,chemistry,maths,(physics+chemistry+maths)as total from st_marksheet order by total desc");
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql.toString());
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				MarksheetBean bean = new MarksheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setName(rs.getString(3));
				bean.setPhysics(rs.getInt(4));
				bean.setChemistry(rs.getInt(5));
				bean.setMaths(rs.getInt(6));

				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error(e);
			throw new ApplicationException("Exception in getting Meritlist of marksheet");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model Meritlist End");
		return list;

	}

}
