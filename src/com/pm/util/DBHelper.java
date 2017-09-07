package com.pm.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBHelper {
	private static final String driver="com.mysql.jdbc.Driver";
	private static final String url="jdbc:mysql://127.0.0.1:3306/projectmgr?useUnicode=true&characterEncoding=UTF-8";
	private static final String user="root";
	private static final String password="";	
	private static Connection conn = null;
	
	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws Exception{
		if(conn == null){
			
				conn=DriverManager.getConnection(url, user, password);
			
			return conn;
		}else{return conn;}
	}
}
