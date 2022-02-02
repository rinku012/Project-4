package in.com.rays.proj4.util;

//import in.co.rays.proj4.exception.ApplicationException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

//import in.co.rays.proj_4.exception.ApplicationException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import in.com.rays.proj4.exception.ApplicationException;

//JDBC DataSource is a Data Connection Pool


public class JDBCDataSource {

	/**
	 * JDBC Database connection pool ( DCP )
	 */
	private static JDBCDataSource datasource;
	
	private JDBCDataSource(){
		
	}
	
	private ComboPooledDataSource cdps=null;
	
	/**
	 * Create instance of Connection Pool
	 *
	 * @return datasource
	 */
	public static JDBCDataSource getInstance(){
		
		
		System.out.println("addd 0 in JDBC DataSourse");
		
		if(datasource==null){
			ResourceBundle rb=ResourceBundle.getBundle("in.com.proj4_bundle.System");
			
			System.out.println("addd 8 in JDBC DataSourse");
			datasource=new JDBCDataSource();
			datasource.cdps = new ComboPooledDataSource();
			
			try {
				datasource.cdps.setDriverClass(rb.getString("driver"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("addd 1 in JDBC DataSourse");
			datasource.cdps.setJdbcUrl(rb.getString("url"));
			datasource.cdps.setUser(rb.getString("username"));
			datasource.cdps.setPassword(rb.getString("password"));
			datasource.cdps.setInitialPoolSize(new Integer((String)rb.getString("initialPoolSize")));
			datasource.cdps.setAcquireIncrement(new Integer((String)rb.getString("acquireIncrement")));
			datasource.cdps.setMaxPoolSize(new Integer((String)rb.getString("maxPoolSize")));
			datasource.cdps.setMaxIdleTime(DataUtility.getInt(rb.getString("timeout")));
			datasource.cdps.setMinPoolSize(new Integer((String)rb.getString("minPoolSize")));
			System.out.println("addd 2 in JDBC DataSourse");
	
			}return datasource;
	}
	
	/**
	 * Gets the connection from ComboPooledDataSource
	 *
	 * @return connection
	 */
	public static Connection getConnection() throws Exception{
		
		return getInstance().cdps.getConnection();
		
	}
	
	/**
	 * Closes a connection    
	 *
	 * @param connection
	 * @throws Exception
	 */
	public static void closeConnection(Connection connection){
		System.out.println("addd 3 in JDBC DataSourse");
		if(connection!=null){
			try {
				connection.close();
			} catch (Exception e) {
			}
		}
	}
	public static void trnRollback(Connection connection) throws ApplicationException{
		
		System.out.println("addd 4 in JDBC DataSourse");
		if(connection!=null){
			try {
				connection.rollback();
			} catch (SQLException e) {
				throw new ApplicationException(e.toString());
			}
		}
	}
}
