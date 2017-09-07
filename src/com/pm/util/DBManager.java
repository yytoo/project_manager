package com.pm.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Properties;
 
import javax.sql.DataSource;
 
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

	public class DBManager {
	    private static final Log log = LogFactory.getLog(DBManager.class);
	    //private static final String configFile = "../../../dbcp.properties";
	 
	    private static DataSource dataSource;
	 
	    static {
	    	try {
		    		Properties p = new Properties();
		    		p.setProperty("driverClassName", "com.mysql.jdbc.Driver");
		    		p.setProperty("password", "");
		    		p.setProperty("url", "jdbc:mysql://localhost:3306/projectmgr?useUnicode=true&characterEncoding=UTF-8");
		    		
		    		p.setProperty("username", "root");
		    		p.setProperty("maxActive", "30");
		    		p.setProperty("maxIdle", "10");
		    		p.setProperty("maxWait", "1000");
		    		p.setProperty("removeAbandoned", "false");
		    		p.setProperty("removeAbandonedTimeout", "120");
		    		p.setProperty("testOnBorrow", "true");
		    		p.setProperty("logAbandoned", "true");
		    		dataSource = BasicDataSourceFactory.createDataSource(p);
		    		Connection conn = getConn();
			        DatabaseMetaData mdm = conn.getMetaData();
			        log.info("Connected to " + mdm.getDatabaseProductName() + " "+ mdm.getDatabaseProductVersion());
			        if (conn != null) {
			        	conn.close();
			        }		      
	    		} catch (Exception e){
	    			 log.error("��ʼ�����ӳ�ʧ�ܣ�" + e);
	    		}
	          
	    }
	 
	    private DBManager() {
	    }
	 
	    /**
	     * ��ȡ���ӣ������ǵùر�
	     * 
	     * @see {@link DBManager#closeConn(Connection)}
	     * @return
	     */
	    public static final Connection getConn() {
	        Connection conn = null;
	        try {
	            conn = dataSource.getConnection();
	            conn.setAutoCommit(false);
	        } catch (SQLException e) {
	            log.error("��ȡ���ݿ�����ʧ�ܣ�" + e);
	        }
	        return conn;
	    }
	 
	    /**
	     * �ر�����
	     * 
	     * @param conn
	     *            ��Ҫ�رյ�����
	     */
	    public static void closeConn(Connection conn) {
	        try {
	            if (conn != null && !conn.isClosed()) {
	                conn.setAutoCommit(true);
	                conn.close();
	            }
	        } catch (SQLException e) {
	            log.error("�ر����ݿ�����ʧ�ܣ�" + e);
	        }
	    }
	 
	}

