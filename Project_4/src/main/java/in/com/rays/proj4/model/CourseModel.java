package in.com.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.com.rays.proj4.bean.CourseBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DatabaseException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.util.JDBCDataSource;

public class CourseModel {
	
	public static Logger log = Logger.getLogger(CourseModel.class);
	
	//create id
	//return pk
	//throws DatabaseException
	
	public Integer nextpk() throws DatabaseException{
	log.debug("Maodel nextpk Started");
		
		Connection conn = null;
		int pk=0;
		try {
			conn=JDBCDataSource.getConnection();
		    PreparedStatement stmt=conn.prepareStatement("Select max(id) from st_course");
			
		    ResultSet rs=stmt.executeQuery();
		    
		    while(rs.next()){
		    	pk=rs.getInt(1);
		}
	
		    stmt.close();
		    rs.close();
		}catch(Exception e){
			//log.error("Daatabase Exception..",e);
			throw new DatabaseException("Exception:Exception in getting pk");
			
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model pk end");
		return pk+1;
	}
	
	/**
	 * add new course
	 * 
	 * @param b
	 * @return pk
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public long add(CourseBean bean) throws Exception{
		
		log.debug("Model add Started");
		Connection conn=null;
		int pk=0;
		CourseBean beanExist=findByName(bean.getCourseName());
		
		if(beanExist!=null&&beanExist.getId()!=bean.getId()){
			throw new DuplicateRecordException("Course is already exist");
			
		}

		
		try{
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			pk=nextpk();
			PreparedStatement stmt=conn.prepareStatement("Insert into st_course values(?,?,?,?,?,?,?,?)");
		
		stmt.setInt(1, pk);
		stmt.setString(2,bean.getCourseName());
		
		stmt.setString(3,bean.getDescription());
		stmt.setString(4,bean.getDuration());
		stmt.setString(5,bean.getCreatedBy());
		stmt.setString(6, bean.getModifiedBy());
		stmt.setTimestamp(7,bean.getCreatedDatetime());
		stmt.setTimestamp(8,bean.getModifiedDatetime());
		
		stmt.executeUpdate();
		conn.commit();
		stmt.close();
		
	}catch(Exception e){
		log.error("Database Exception..",e);
		e.printStackTrace();
		try{
			conn.rollback();
		}catch(Exception ex){
			throw new ApplicationException("Exception: add rollback exception"+ex.getMessage());
		}
		throw new ApplicationException("Exception: Exception in add college");
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add end");
		return pk;
	}
	
	/**
	 * delete course information in table
	 * 
	 * @param b
	 * @throws ApplicationException
	 */
	public void delete(CourseBean bean) throws ApplicationException{
		log.debug("Model delete Started");
		Connection conn=null;
		
		try{
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement stmt=conn.prepareStatement("delete from st_course where id=?");
			stmt.setLong(1, bean.getId());
			stmt.executeUpdate();
			conn.commit();
			stmt.close();
		}catch(Exception e){
			log.error("DatabaseException ",e);
			try{
				conn.rollback();
			}catch(Exception ex){
				throw new ApplicationException("Exception:Delete rollback exception "+ex.getMessage());
				
			}throw new ApplicationException("Exception in delete course");
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model delete end");
	}
	
	/**
	 * find course by name
	 * 
	 * @param courseName
	 * @return bean
	 * @throws ApplicationException
	 */
	public CourseBean findByName(String name) throws ApplicationException{
		log.debug("Model find by name started");
		
		StringBuffer sql=new StringBuffer("select * from st_course where course_name=?");
		
		CourseBean bean=null;
		Connection conn=null;
		try{
			conn=JDBCDataSource.getConnection();
			PreparedStatement stmt=conn.prepareStatement(sql.toString());
			stmt.setString(1, name);
			ResultSet rs=stmt.executeQuery();
			
			while(rs.next()){
				bean=new CourseBean();
				
				bean.setId(rs.getLong(1));
				bean.setCourseName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setDuration(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
				
				
			}rs.close();
		}catch(Exception e){
			log.error("Database Exception..",e);
			throw new ApplicationException("Exception:Exception in getting Course by Name");
			
			
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model  findByName Ended");
		return bean;
	}
	
	/**
	 * find information by pk
	 * 
	 * @param pk
	 * @return bean
	 * @throws ApplicationException
	 */
	public CourseBean findByPK(long pk) throws ApplicationException{
		log.debug("Model find by pk started");
		
		StringBuffer sql=new StringBuffer("select * from st_course where id=?");
		CourseBean bean=null;
		Connection conn=null;
		
		try{
			conn=JDBCDataSource.getConnection();
			PreparedStatement stmt=conn.prepareStatement(sql.toString());
			stmt.setLong(1,pk);
			
			ResultSet rs=stmt.executeQuery();
			
			while(rs.next()){
				bean=new CourseBean();
				bean.setId(rs.getInt(1));
				bean.setCourseName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setDuration(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
				
				
			}rs.close();
		}catch(Exception e){
			log.error("Database Exception..",e);
			throw new ApplicationException("Exception:Exception in getting Course by pk");
			
			
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model  findByPK End");
		return bean;
		
		
	}
	
	/**
	 * update course information
	 * @param b
	 * @throws ApplicationException
	 */
public void update(CourseBean bean) throws DuplicateRecordException, ApplicationException{
		
		log.debug("Model update started");
		
		Connection conn=null;
		
		CourseBean beanExist=findByName(bean.getCourseName());
		
		if(beanExist!=null&&beanExist.getId()!=bean.getId()){
			throw new DuplicateRecordException("course  is already exist");
			
		}
		try{
			conn=JDBCDataSource.getConnection();
			
			
			PreparedStatement stmt=conn.prepareStatement("Update st_course set course_name=?,description=?,duration=?,created_by=?,modified_by=?,created_datetime=?,modified_datetime=? where id=?");
			conn.setAutoCommit(false);
			
			stmt.setString(1,bean.getCourseName());
			
			stmt.setString(2,bean.getDescription());
			stmt.setString(3,bean.getDuration());
			stmt.setString(4,bean.getCreatedBy());
			stmt.setString(5, bean.getModifiedBy());
			stmt.setTimestamp(6,bean.getCreatedDatetime());
			stmt.setTimestamp(7,bean.getModifiedDatetime());

			stmt.setLong(8,bean.getId());
			
			stmt.executeUpdate();
			conn.commit();
			stmt.close();
		
			
		}catch(Exception e){
			//log.error("Database Exception..",e);
		
			/*
			 * try{ conn.rollback(); }catch(Exception ex){ ex.printStackTrace(); throw new
			 * ApplicationException("Exception:delete rollback exception"+ex.getMessage());
			 * 
			 * } throw new ApplicationException("Exception in updating college"); }finally{
			 * JDBCDataSource.closeConnection(conn); }
			 */
		}
			JDBCDataSource.closeConnection(conn);
			log.debug("Model update End");
			
		
		}

/**
 * search list of course detail
 * 
 * @param cbean1
 * @param pageNo
 * @param pageSize
 * @return list
 * @throws ApplicationException
 */
public List search(CourseBean bean, int pageNo, int pageSize)throws ApplicationException {
		
			log.debug("Model search Started");
		
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE WHERE 1=1");
		System.out.println(bean.getDuration()+",,,,,,,,,,,,,,,,............"+bean.getId());
		/*if (bean != null) {
		if (bean.getId() > 0) {
		sql.append(" AND id = " + bean.getId());
		}*/
		if(bean!=null){
			if(bean.getId()>0){
		sql.append(" AND id= "+bean.getId());
				
		}
		
		if (bean.getCourseName() != null && bean.getCourseName().length() > 0) {
		sql.append(" AND COURSE_NAME like '" + bean.getCourseName() + "%'");
		}
		if (bean.getDescription() != null && bean.getDescription().length() > 0) {
		sql.append(" AND Description like '" + bean.getDescription() + "%'");
		}
		if (bean.getDuration() != null && bean.getDuration().length() > 0) {
			System.out.println(bean.getDuration()+",,,,,,,,,,,,,,,,............");
//			sql.append(" AND Duration like '" + bean.getDuration() + "%'");
			sql.append(" AND Duration like '"+bean.getDuration()+ "%'");
		}
		}
		
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
		// Calculate start record index
		pageNo = (pageNo - 1) * pageSize;
		
		
		sql.append(" Limit " + pageNo + "," + pageSize);
		// sql.append(" limit " + pageNo + "," + pageSize);
		}
		
		ArrayList<CourseBean> list = new ArrayList<CourseBean>();
		Connection conn = null;
		try {
		conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
		bean = new CourseBean();
		bean.setId(rs.getInt(1));
		bean.setCourseName(rs.getString(2));
		bean.setDescription(rs.getString(3));
		bean.setDuration(rs.getString(4));
		bean.setCreatedBy(rs.getString(5));
		bean.setModifiedBy(rs.getString(6));
		bean.setCreatedDatetime(rs.getTimestamp(7));
		bean.setModifiedDatetime(rs.getTimestamp(8));
		list.add(bean);
		}
		rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		log.error("Database Exception..", e);
		throw new ApplicationException("Exception : Exception in search course");
		} finally {
		JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("Model search End");
		return list;
		}
		
		public List search(CourseBean bean) throws ApplicationException {
		return search(bean, 0, 0);
		}
		
		public List list() throws ApplicationException {
		return list(0, 0);
		}
		
		/**
		 * to show course list
		 * 
		 * @param pageNo
		 * @param pageSize
		 * @return list
		 * @throws ApplicationException
		 */
		public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_course");
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
		CourseBean bean = new CourseBean();
		bean.setId(rs.getInt(1));
		bean.setCourseName(rs.getString(2));
		bean.setDescription(rs.getString(3));
		bean.setDuration(rs.getString(4));
		bean.setCreatedBy(rs.getString(5));
		bean.setModifiedBy(rs.getString(6));
		bean.setCreatedDatetime(rs.getTimestamp(7));
		bean.setModifiedDatetime(rs.getTimestamp(8));
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
