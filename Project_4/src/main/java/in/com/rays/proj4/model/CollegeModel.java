package in.com.rays.proj4.model;

import java.sql.Connection;     
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.com.rays.proj4.bean.CollegeBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DatabaseException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.util.JDBCDataSource;

//JDBC implements of college model

public class CollegeModel {
	
private static Logger log=Logger.getLogger(CollegeModel.class);
	
	/**
	 * find pk
	 * @return pk      
	 * @throws DatabaseException
	 */
	public Integer nextPK() throws DatabaseException{
		//log.debug("Model nextPK Started");
		Connection conn=null;
		int pk=0;
		
		try{
			conn=JDBCDataSource.getConnection();
		
		PreparedStatement stmt=conn.prepareStatement("Select MAX(ID) from st_college");
		
		ResultSet rs=stmt.executeQuery();
		
		while(rs.next()){
			pk=rs.getInt(1);
		}
		rs.close();
	  }catch(Exception e){
		 // log.error("Database Exception",e);
		  throw new DatabaseException("Exception:Exception is getting PK");
		  
	  }finally{
		  JDBCDataSource.closeConnection(conn);
	  }
		
		//log.debug("Model nextPK End");
		return pk+1;
	}
	
	/**
	 * add new college
	 * @param bean
	 * @return
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public long add(CollegeBean bean) throws Exception{
		
		//log.debug("Model add Started");
		System.out.println("addddddddddddddddd");
		Connection conn=null;
		int pk=0;
		
		CollegeBean duplicateCollegeName=findByName(bean.getName());
		
		if(duplicateCollegeName!=null){
			throw new DuplicateRecordException("College Name Already Exists");
			
		}
		try{
			conn=JDBCDataSource.getConnection();
			
			System.out.println("addd 1");
			pk=nextPK();
			conn.setAutoCommit(false);
			PreparedStatement stmt=conn.prepareStatement("Insert into st_college values(?,?,?,?,?,?,?,?,?,?)");
			
			
			stmt.setInt(1, pk);
			stmt.setString(2,bean.getName());
			stmt.setString(3,bean.getAddress());
			stmt.setString(4,bean.getState());
			stmt.setString(5,bean.getCity());
			stmt.setString(6,bean.getPhonoNo());
			stmt.setString(7,bean.getCreatedBy());
			stmt.setString(8,bean.getModifiedBy());
			stmt.setTimestamp(9,bean.getCreatedDatetime());
			stmt.setTimestamp(10,bean.getModifiedDatetime());
			
			stmt.executeUpdate();
			System.out.println("test add seccesfull");
			conn.commit();
			stmt.close();
		
			
		}catch(Exception e){
			//log.error("Database Exception..",e);
			e.printStackTrace();
			try{
				conn.rollback();
			}catch(Exception ex){
			ex.printStackTrace();
			throw new ApplicationException("Exception:add rollback exception"+ex.getMessage());
			} 
			 throw new ApplicationException("Exception : Exception in add College");
			}finally{
				JDBCDataSource.closeConnection(conn);
			}
			//log.debug("Model add End");
			return pk;
		
		
	}
	
	/**
	 * delete college information
	 * @param b
	 * @throws DatabaseException
	 */
	public void delete(CollegeBean bean) throws ApplicationException{
		log.debug("Model Delete Started");
		Connection conn=null;
		try{
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement stmt=conn.prepareStatement("delete from st_college where id=?");
			stmt.setLong(1,bean.getId());
			stmt.executeUpdate();
			System.out.println("Delete secc");
			conn.commit();
			stmt.close();
			
		}catch(Exception e){
			log.error("Database Exception..",e);
			try{
				conn.rollback();
			}catch(Exception ex){
				throw new ApplicationException("Exception:Delete RollBack Exception"+ex.getMessage());
			
			} throw new ApplicationException("Exception:Exception in delete college");	
			}finally{ JDBCDataSource.closeConnection(conn);
		
			}log.debug("Model delete end");
		
		}
	
	/**
	 * find the infromation with the help of college name
	 * @param name
	 * @return bean
	 * @throws ApplicationException
	 */
	public CollegeBean findByName(String name) throws ApplicationException{
		//log.debug("Model FindByName Started ");
		StringBuffer sql=new StringBuffer("Select * from st_college where name=?");
		
		CollegeBean bean=null;
		Connection conn=null;
		try{
			conn=JDBCDataSource.getConnection();
			PreparedStatement stmt=conn.prepareStatement(sql.toString());
			stmt.setString(1,name);
			
			ResultSet rs=stmt.executeQuery();
			
			while(rs.next()){
				bean=new CollegeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhonoNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
			}rs.close();
			
			
		}catch(Exception e){
			//log.error("Database Exception..",e);
			throw new ApplicationException("Exception:Exception in getting College by Name");
			
			
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		//log.debug("Model  findByName Ended");
		return bean;
	}
	
	/**
	 * find the information with the help of pk
	 * @param pk
	 * @return bean
	 * @throws ApplicationException
	 */
	public CollegeBean findByPK(long pk) throws ApplicationException{
		//log.debug("Model findByPK Started");
		
		StringBuffer sql=new StringBuffer("select * from st_college where id=?");
		
		CollegeBean bean=null;
		Connection conn=null;
		
		try{
			conn=JDBCDataSource.getConnection();
			PreparedStatement stmt=conn.prepareStatement(sql.toString());
			stmt.setLong(1,pk);
			
			ResultSet rs=stmt.executeQuery();
			
			while(rs.next()){
				bean=new CollegeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhonoNo(rs.getString(6));
//				bean.setCreatedBy(rs.getString(7));
//				bean.setModifiedBy(rs.getString(8));
//				bean.setCreatedDateTime(rs.getTimestamp(9));
//				bean.setModifiedDateTime(rs.getTimestamp(10));
			}rs.close();
			stmt.close();
	
		
		}catch(Exception e){
			//log.error("Database Exception..",e);
			throw new ApplicationException("Exception:Exception in getting College by pk");
			
			
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		//log.debug("Model  findByPK End");
		return bean;
		
				
	}
	
	/**
	 * update college detail
	 * @param bean
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public void update(CollegeBean bean) throws DuplicateRecordException, ApplicationException{
		
		log.debug("Model update started");
		
		Connection conn=null;
		
		CollegeBean beanExist=findByName(bean.getName());
		
		if(beanExist!=null&&beanExist.getId()!=bean.getId()){
			throw new DuplicateRecordException("College is already exist");
			 
		}
		try{
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			
			PreparedStatement stmt=conn.prepareStatement("Update st_college set name=?,address=?,state=?,city=?,phone_no=?,created_by=?,modified_by=?,created_datetime=?,modified_datetime=? where id=?");
			
			stmt.setString(1,bean.getName());
			stmt.setString(2,bean.getAddress());
			stmt.setString(3,bean.getState());
			stmt.setString(4,bean.getCity());
			stmt.setString(5,bean.getPhonoNo());
			stmt.setString(6,bean.getCreatedBy());
			stmt.setString(7,bean.getModifiedBy());
			stmt.setTimestamp(8,bean.getCreatedDatetime());
			stmt.setTimestamp(9,bean.getModifiedDatetime());
			stmt.setLong(10,bean.getId());
			
			stmt.executeUpdate();
			conn.commit();
			stmt.close();
		
			
		}catch(Exception e){
			log.error("Database Exception..",e);
		
			try{
				conn.rollback();
			}catch(Exception ex){
		ex.printStackTrace();
			throw new ApplicationException("Exception:delete rollback exception"+ex.getMessage());
			
			}
			throw new ApplicationException("Exception in updating college");
		}finally{
				JDBCDataSource.closeConnection(conn);
			}
			log.debug("Model update End");
			
			
		
		
		}

	/**
	 * search college information 
	 * @param cbean1
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */
		public List search(CollegeBean bean,int pageNo,int pageSize) throws ApplicationException{
			
			log.debug("Model Search Started");
			StringBuffer sql=new StringBuffer ("Select * from st_college where 1=1");
			
			if(bean!=null){
				if(bean.getId()>0){
					sql.append(" AND id= "+bean.getId());
					
				}
			if(bean.getName()!=null&&bean.getName().length()>0){
				sql.append(" AND NAME like '"+bean.getName()+"%'");
				}
				
			if(bean.getAddress()!=null&&bean.getAddress().length()>0){
				sql.append(" AND ADDRESS like '"+bean.getAddress()+"%'");
				}
			if(bean.getState()!=null&&bean.getState().length()>0){
				sql.append(" AND STATE like '"+bean.getState()+"%'");
			}
			if(bean.getCity()!=null&&bean.getCity().length()>0){
				sql.append(" AND CITY like '"+bean.getCity()+"%'");
			}
			if(bean.getPhonoNo()!=null&&bean.getPhonoNo().length()>0){
				sql.append(" AND PHONE_NO like '"+bean.getPhonoNo()+"%'");
			}
			
				}
			
			if(pageSize>0){
				pageNo=(pageNo-1)*pageSize;
				
				sql.append(" Limit "+pageNo+","+pageSize);
			}
			
			ArrayList list=new ArrayList();
			Connection conn=null;
			try{
				conn=JDBCDataSource.getConnection();
				
				PreparedStatement stmt=conn.prepareStatement(sql.toString());
				ResultSet rs=stmt.executeQuery();
				
				while(rs.next()){
				
				bean=new CollegeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhonoNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
				
				list.add(bean);
				
				}rs.close();
			}catch(Exception e){
				e.printStackTrace();
				log.error("DataBase Exception",e);
				throw new ApplicationException("Exception:Exception in search college");
				
			}finally{
				JDBCDataSource.closeConnection(conn);
			}
			log.debug("Model Search End");
			return list;
			
			
		}
	
		public List search(CollegeBean bean)throws ApplicationException{
			
			
			
			return search(bean, 0, 0);
			
		}
		
		public List list() throws ApplicationException{
			return list(0,0);
		}
	
		/**
		 * to show list of college
		 * @param pageNo
		 * @param pageSize
		 * @return list
		 * @throws ApplicationException
		 */
	public List list(int pageNo,int pageSize) throws ApplicationException{
		
		log.debug("Model list Started");
		
		ArrayList list=new ArrayList();
		StringBuffer sql=new StringBuffer("select * from st_college order by name");
		
		if(pageSize>0){
			pageNo=(pageNo-1) * pageSize;
			sql.append(" limit "+pageNo+","+pageSize);
			
		}
		Connection conn=null;
		
		try{
			conn=JDBCDataSource.getConnection();
			PreparedStatement stmt=conn.prepareStatement(sql.toString());
			
			ResultSet rs=stmt.executeQuery();
			while(rs.next()){
				CollegeBean bean=new CollegeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhonoNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
				
				list.add(bean);
				

			}rs.close();
		}catch(Exception e){
			e.printStackTrace();
			log.error("Database Exception..",e);
			throw new ApplicationException("Exception:Exception in getting list of user");
			
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model list end");
		return list;
		
	}  
	
	
	/*
	 * public List search(CollegeBean bean, int pageNo, int pageSize) throws
	 * ApplicationException { log.debug("Model search Started"); StringBuffer sql =
	 * new StringBuffer( "SELECT * FROM ST_COLLEGE WHERE 1=1"); if (bean != null) {
	 * if (bean.getId() > 0) { sql.append(" AND id = " + bean.getId()); } if
	 * (bean.getName() != null && bean.getName().length() > 0) {
	 * sql.append(" AND NAME like '" + bean.getName() + "%'"); } if
	 * (bean.getAddress() != null && bean.getAddress().length() > 0) {
	 * sql.append(" AND ADDRESS like '" + bean.getAddress() + "%'"); } if
	 * (bean.getState() != null && bean.getState().length() > 0) {
	 * sql.append(" AND STATE like '" + bean.getState() + "%'"); } if
	 * (bean.getCity() != null && bean.getCity().length() > 0) {
	 * sql.append(" AND CITY like '" + bean.getCity() + "%'"); } if
	 * (bean.getPhoneNo() != null && bean.getPhoneNo().length() > 0) {
	 * sql.append(" AND PHONE_NO = " + bean.getPhoneNo()); } } // if page size is
	 * greater than zero then apply pagination if (pageSize > 0) { // Calculate
	 * start record index pageNo = (pageNo - 1) * pageSize; sql.append(" Limit " +
	 * pageNo + ", " + pageSize); // sql.append(" limit " + pageNo + "," +
	 * pageSize); } ArrayList list = new ArrayList(); Connection conn = null; try {
	 * conn = JDBCDataSource.getConnection(); PreparedStatement pstmt =
	 * conn.prepareStatement(sql.toString()); ResultSet rs = pstmt.executeQuery();
	 * while (rs.next()) { bean = new CollegeBean(); bean.setId(rs.getLong(1));
	 * bean.setName(rs.getString(2)); bean.setAddress(rs.getString(3));
	 * bean.setState(rs.getString(4)); bean.setCity(rs.getString(5));
	 * bean.setPhoneNo(rs.getString(6)); bean.setCreatedBy(rs.getString(7));
	 * bean.setModifiedBy(rs.getString(8));
	 * bean.setCreatedDateTime(rs.getTimestamp(9));
	 * bean.setModifiedDateTime(rs.getTimestamp(10)); list.add(bean); } rs.close();
	 * } catch (Exception e) { log.error("Database Exception..", e); throw new
	 * ApplicationException( "Exception : Exception in search college"); } finally {
	 * JDBCDataSource.closeConnection(conn); } log.debug("Model search End"); return
	 * list; } public List search(CollegeBean bean) throws ApplicationException {
	 * return search(bean, 0, 0); } public List list() throws ApplicationException {
	 * return list(0, 0); }
	 * 
	 * public List list(int pageNo, int pageSize) throws ApplicationException {
	 * log.debug("Model list Started"); ArrayList list = new ArrayList();
	 * StringBuffer sql = new StringBuffer("select * from ST_COLLEGE"); // if page
	 * size is greater than zero then apply pagination if (pageSize > 0) { //
	 * Calculate start record index pageNo = (pageNo - 1) * pageSize;
	 * sql.append(" limit " + pageNo + "," + pageSize); } Connection conn = null;
	 * try { conn = JDBCDataSource.getConnection(); PreparedStatement pstmt =
	 * conn.prepareStatement(sql.toString()); ResultSet rs = pstmt.executeQuery();
	 * while (rs.next()) { CollegeBean bean = new CollegeBean();
	 * bean.setId(rs.getLong(1)); bean.setName(rs.getString(2));
	 * bean.setAddress(rs.getString(3)); bean.setState(rs.getString(4));
	 * bean.setCity(rs.getString(5)); bean.setPhoneNo(rs.getString(6));
	 * bean.setCreatedBy(rs.getString(7)); bean.setModifiedBy(rs.getString(8));
	 * bean.setCreatedDateTime(rs.getTimestamp(9));
	 * bean.setModifiedDateTime(rs.getTimestamp(10)); list.add(bean); } rs.close();
	 * } catch (Exception e) { log.error("Database Exception..", e); throw new
	 * ApplicationException( "Exception : Exception in getting list of users"); }
	 * finally { JDBCDataSource.closeConnection(conn); }
	 * log.debug("Model list End"); return list; }
	 */	

}
